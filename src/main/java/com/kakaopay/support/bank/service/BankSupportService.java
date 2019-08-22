package com.kakaopay.support.bank.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kakaopay.support.bank.constant.MessageConstant;
import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.entity.RegionCode;
import com.kakaopay.support.bank.exception.SupportException;
import com.kakaopay.support.bank.model.BankSupportCSV;
import com.kakaopay.support.bank.model.UpdateBankSupport;
import com.kakaopay.support.bank.model.search.Format;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.model.search.Sort;
import com.kakaopay.support.bank.repository.BankSupportRepository;
import com.kakaopay.support.bank.service.format.CustomFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankSupportService {
    private static final String ENCORDING_TYPE = "MS949";

    private BankSupportRepository bankSupportRepository;

    public BankSupportService(BankSupportRepository bankSupportRepository) {
        this.bankSupportRepository = bankSupportRepository;
    }

    public String saveUploadData(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), ENCORDING_TYPE)) {
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = mapper.schemaFor(BankSupportCSV.class).withSkipFirstDataRow(true);
            MappingIterator<BankSupportCSV> objects = mapper.readerFor(BankSupportCSV.class).with(bootstrapSchema).readValues(reader);

            List<BankSupportCSV> list = objects.readAll();
            for (int i= 0; i < list.size() ;i++) {
                BankSupportCSV row = list.get(i);
                    String code = String.format("REG%04d", row.getId());
                    RegionCode regionCode = RegionCode.builder()
                            .code(code)
                            .name(row.getRegion())
                            .build();

                    BankSupport bankSupport = BankSupport.builder()
                            .id(row.getId())
                            .regionCode(regionCode)
                            .target(row.getTarget())
                            .usage(row.getUsage())
                            .limit(row.getLimit())
                            .rate(row.getRate())
                            .institute(row.getInstitute())
                            .mgmt(row.getMgmt())
                            .reception(row.getReception())
                            .regDate(LocalDateTime.now())
                            .build();

                    bankSupportRepository.save(bankSupport);
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패 했습니다. : %s", e);
            throw new SupportException( MessageConstant.FAIL.name());
        }
        return MessageConstant.SUCCESS.name();
    }

    public String updateBankSupport(String resionCode, UpdateBankSupport updateBankSupport) {
        BankSupport bankSupport = bankSupportRepository.findByRegionCodeCode(resionCode);
        bankSupport.setTarget(updateBankSupport.getTarget());
        bankSupport.setUsage(updateBankSupport.getUsage());
        bankSupport.setLimit(updateBankSupport.getLimit());
        bankSupport.setRate(updateBankSupport.getRate());
        bankSupport.setInstitute(updateBankSupport.getInstitute());
        bankSupport.setMgmt(updateBankSupport.getMgmt());
        bankSupport.setReception(updateBankSupport.getReception());
        bankSupport.setUpdateDate(LocalDateTime.now());
        bankSupportRepository.save(bankSupport);
        return MessageConstant.SUCCESS.name();
    }

    public List<CustomFormat> getBankSupportListAll() {
        RequestBankSupport requestBankSupport = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .format(Format.BASIC)
                .build();

        return getBankSupportList(requestBankSupport);
    }

    public List<CustomFormat> getBankSupportList(RequestBankSupport requestBankSupport) {
        List<BankSupport> list = search(requestBankSupport);

        Sort sort = requestBankSupport.getSort();
        if (sort != null) {
            sort.createSort().excute(list);
        }

        Format format = requestBankSupport.getFormat();
        return list.stream()
                .map(s -> format.createFormat().generateFormat(s))
                .limit(requestBankSupport.getCount())
                .collect(Collectors.toList());
    }

    public CustomFormat getBankSupport(RequestBankSupport requestBankSupport) {
        requestBankSupport.setCount(Integer.MAX_VALUE);
        requestBankSupport.setFormat(Format.BASIC);
        return getBankSupportList(requestBankSupport)
                .stream().findFirst().orElseThrow(() -> new SupportException(MessageConstant.NO_RESULT.name()));
    }

    private List<BankSupport> search(RequestBankSupport requestBankSupport) {
        List<BankSupport> responceBankSupportList = bankSupportRepository.search(requestBankSupport);
        if (CollectionUtils.isEmpty(responceBankSupportList)) {
            return Collections.emptyList();
        }
        return responceBankSupportList;
    }
}

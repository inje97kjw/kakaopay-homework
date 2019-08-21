package com.kakaopay.support.bank.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kakaopay.support.bank.constant.MessageConstant;
import com.kakaopay.support.bank.exception.SupportException;
import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.entity.RegionCode;
import com.kakaopay.support.bank.model.BankSupportCSV;
import com.kakaopay.support.bank.model.UpdateBankSupport;
import com.kakaopay.support.bank.model.search.FilterCondition;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.model.search.ResponceBankSupport;
import com.kakaopay.support.bank.model.search.Sort;
import com.kakaopay.support.bank.repository.BankSupportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankSupportService {
    private static final String ENCORDING_TYPE = "MS949";
    private static final Pattern REGEX_USAGE_DRIVE = Pattern.compile("(운전)");
    private static final Pattern REGEX_USAGE_FACILITIES = Pattern.compile("(시설)");
    private static final Pattern REGEX_LIMIT_MILLION = Pattern.compile("(\\d+[백만원])");
    private static final Pattern REGEX_LIMIT_BILLION = Pattern.compile("(\\d+[억])");
    private static final Pattern REGEX_RATE = Pattern.compile("(\\d+[%])");

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

    public List<ResponceBankSupport> getBankSupportListAll() {
        RequestBankSupport requestBankSupport = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .sort(Sort.DEFAULT)
                .build();

        return getBankSupportList(requestBankSupport);
    }

    public List<ResponceBankSupport> getBankSupportList(RequestBankSupport requestBankSupport) {
        List<BankSupport> bankSupportList = search(requestBankSupport);

        bankSupportList = requestBankSupport.getSort()
                .customSort(bankSupportList, requestBankSupport.adjustLimitCount(bankSupportList.size()));

        return bankSupportList.stream()
                .map(s -> convertResponceBankSupport(s))
                .collect(Collectors.toList());
    }

    public ResponceBankSupport getBankSupport(RequestBankSupport requestBankSupport) {
        BankSupport support = bankSupportRepository.search(requestBankSupport)
                .stream().findFirst().orElseThrow(() -> new SupportException(MessageConstant.NO_RESULT.name()));
        return convertResponceBankSupport(support);
    }

    public List<ResponceBankSupport> getArticleAanalysisRecommend(String article) {
        RequestBankSupport requestBankSupport = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .sort(Sort.DEFAULT)
                .build();

        List<BankSupport> bankSupportList = search(requestBankSupport);

        FilterCondition filterCondition = getArticleFilterCondition(article);
        bankSupportList = filterCondition.excutefilter(bankSupportList);

        return bankSupportList.stream()
                .map(s -> convertResponceBankSupport(s))
                .collect(Collectors.toList());
    }

    private FilterCondition getArticleFilterCondition(String article) {
        String articleResion = "충남대천"; //기사에서 지역을 추출할수 있다고 가정하여 위치도 임의로 데이터에 넣어둠
        String articleUsageDriver = matchingStr(REGEX_USAGE_DRIVE, article);
        String articleUsageFacilities = matchingStr(REGEX_USAGE_FACILITIES, article);
        String articleLimit = matchingStr(REGEX_LIMIT_BILLION, article);
        if (articleLimit.isEmpty()) {
            articleLimit = matchingStr(REGEX_LIMIT_MILLION, article);
        }
        String articleRate = matchingStr(REGEX_RATE, article);

        return  FilterCondition.builder()
                .region(articleResion)
                .usageDriver(articleUsageDriver)
                .usageFacilities(articleUsageFacilities)
                .limit(articleLimit)
                .region(articleRate)
                .build();
    }

    private ResponceBankSupport convertResponceBankSupport(BankSupport bankSupport) {
     return ResponceBankSupport.builder()
             .region(bankSupport.getRegionCode().getName())
             .target(bankSupport.getTarget())
             .usage(bankSupport.getUsage())
             .limit(bankSupport.getLimit())
             .rate(bankSupport.getRate())
             .institute(bankSupport.getInstitute())
             .mgmt(bankSupport.getMgmt())
             .reception(bankSupport.getReception())
             .build();
    }

    private List<BankSupport> search(RequestBankSupport requestBankSupport) {
        List<BankSupport> responceBankSupportList = bankSupportRepository.search(requestBankSupport);
        if (CollectionUtils.isEmpty(responceBankSupportList)) {
            return Collections.emptyList();
        }
        return responceBankSupportList;
    }

    private String matchingStr(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}

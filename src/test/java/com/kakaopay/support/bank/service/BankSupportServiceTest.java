package com.kakaopay.support.bank.service;

import com.kakaopay.support.bank.constant.MessageConstant;
import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.entity.RegionCode;
import com.kakaopay.support.bank.exception.SupportException;
import com.kakaopay.support.bank.model.search.ResponceBankSupport;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.model.search.Sort;
import com.kakaopay.support.bank.repository.BankSupportRepository;
import com.kakaopay.support.bank.repository.RegionCodeRepository;
import com.kakaopay.support.bank.service.BankSupportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankSupportService.class)
public class BankSupportServiceTest {
    @Autowired
    private BankSupportService service;

    @MockBean
    private BankSupportRepository bankSupportRepository;

    @Test
    public void uploadDataTest_SUCCESS() throws Exception {
        //give
        MockMultipartFile multipartFile = getMockMultipartFile();
        //when

        //then
        String result = service.saveUploadData(multipartFile);
        assertThat(result).isEqualTo(MessageConstant.SUCCESS.name());
    }

    @Test(expected = SupportException.class)
    public void uploadDataTest_FAIL() throws Exception {
        //give
        MockMultipartFile multipartFile = getMockMultipartFile();
        //when
        when(bankSupportRepository.save(any(BankSupport.class))).thenThrow(SupportException.class);
        //then
        service.saveUploadData(multipartFile);
    }

    @Test
    public void getSupportListTest_COUNT_ALL() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .sort(Sort.DEFAULT)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(5);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<ResponceBankSupport> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.size()).isEqualTo(5);
    }

    @Test
    public void getSupportListTest_COUNT_3() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(3)
                .sort(Sort.DEFAULT)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(5);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<ResponceBankSupport> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.size()).isEqualTo(3);
    }

    @Test
    public void getSupportListTest_BLANK_RESULT() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .sort(Sort.DEFAULT)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(0);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<ResponceBankSupport> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.size()).isEqualTo(0);
    }

    private MockMultipartFile getMockMultipartFile() throws IOException {
        String fileName = "init_data.csv";
        InputStream file = new ClassPathResource(fileName).getInputStream();
        return new MockMultipartFile(fileName, file);
    }

    public List<BankSupport> createSupportFixture(int count) {
        List<BankSupport> bankSupportList = new ArrayList<>();
        for (int i = 0;i < count;i++) {
            RegionCode regionCode = RegionCode.builder()
                    .name("강릉시" + i)
                    .code("REG000" + i)
                    .build();

            BankSupport bankSupport = BankSupport.builder()
                    .regionCode(regionCode)
                    .target("강릉소재 중소기업")
                    .usage("운전")
                    .limit(String.format("%s억원 이내", i+1))
                    .rate(String.format("%s5%%~%s%%", i+1, i+3))
                    .institute("강릉시" + i)
                    .mgmt("강릉지점" + i)
                    .reception("강릉시 소재 영업점" + i)
                    .build();
            bankSupportList.add(bankSupport);
        }

        return bankSupportList;

    }
}

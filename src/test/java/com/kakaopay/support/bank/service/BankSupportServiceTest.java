package com.kakaopay.support.bank.service;

import com.kakaopay.support.bank.constant.MessageConstant;
import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.entity.RegionCode;
import com.kakaopay.support.bank.exception.SupportException;
import com.kakaopay.support.bank.model.UpdateBankSupport;
import com.kakaopay.support.bank.model.search.Format;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.model.search.Sort;
import com.kakaopay.support.bank.repository.BankSupportRepository;
import com.kakaopay.support.bank.service.format.BasicFormat;
import com.kakaopay.support.bank.service.format.CustomFormat;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankSupportService.class)
public class BankSupportServiceTest {
    @Autowired
    private BankSupportService service;

    @MockBean
    private BankSupportRepository bankSupportRepository;

    @Test
    public void uploadDataTest_성공() throws Exception {
        //give
        MockMultipartFile multipartFile = getMockMultipartFile();
        //when

        //then
        String result = service.saveUploadData(multipartFile);
        assertThat(result).isEqualTo(MessageConstant.SUCCESS.name());
    }

    @Test(expected = SupportException.class)
    public void uploadDataTest_실패() throws Exception {
        //give
        MockMultipartFile multipartFile = getMockMultipartFile();
        //when
        when(bankSupportRepository.save(any(BankSupport.class))).thenThrow(SupportException.class);
        //then
        service.saveUploadData(multipartFile);
    }

    @Test
    public void getSupportListTest_모두조회() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .format(Format.BASIC)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(5);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<CustomFormat> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.size()).isEqualTo(5);
    }

    @Test
    public void getSupportListTest_부분조회() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(3)
                .format(Format.BASIC)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(5);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<CustomFormat> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.size()).isEqualTo(3);
    }

    @Test
    public void getSupportListTest_결고없음() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .format(Format.BASIC)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(0);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<CustomFormat> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.size()).isEqualTo(0);
    }

    @Test
    public void getBankSupportListAll() {
        //give
        List<BankSupport> bankSupportFixtureList = createSupportFixture(10);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<CustomFormat> bankSupportList = service.getBankSupportListAll();
        assertThat(bankSupportList.size()).isEqualTo(10);
    }

    @Test
    public void getBankSupport() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .region("강릉시1")
                .format(Format.BASIC)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(10);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        CustomFormat bankSupport = service.getBankSupport(request);
        assertThat(((BasicFormat)bankSupport).getRegion()).isEqualTo("강릉시0");
    }

    @Test(expected = SupportException.class)
    public void getBankSupport_조회결과없음() {
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .region("강릉시1")
                .format(Format.BASIC)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(0);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        service.getBankSupport(request);
    }

    @Test
    public void getSupportListTest_LIMIT_정렬() {
        //지원한도가 많은 순으로 정렬
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .format(Format.BASIC)
                .sort(Sort.LIMIT)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(10);
        Collections.shuffle(bankSupportFixtureList);
        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<CustomFormat> bankSupportList = service.getBankSupportList(request);
        assertThat(((BasicFormat)bankSupportList.get(0)).getLimit()).isEqualTo("10억원 이내");
    }

    @Test
    public void getSupportListTest_RATE_정렬() {
        //이차보전평균이 작은순 정렬
        //give
        RequestBankSupport request = RequestBankSupport.builder()
                .count(Integer.MAX_VALUE)
                .format(Format.BASIC)
                .sort(Sort.RATE)
                .build();

        List<BankSupport> bankSupportFixtureList = createSupportFixture(10);
        Collections.shuffle(bankSupportFixtureList);

        //when
        when(bankSupportRepository.search(any(RequestBankSupport.class))).thenReturn(bankSupportFixtureList);

        //then
        List<CustomFormat> bankSupportList = service.getBankSupportList(request);
        assertThat(((BasicFormat)bankSupportList.get(0)).getRate()).isEqualTo("1%~3%");
    }

    @Test
    public void updateBankSupport_성공() {
        //give
        BankSupport bankSupportFixture = createSupportFixture(1).get(0);
        UpdateBankSupport updateBankSupport = UpdateBankSupport.builder()
                .institute("판교시")
                .limit("10억원 이내")
                .mgmt("판교지점")
                .rate("10%~12%")
                .reception("판교시 소재 영업점")
                .target("판교소재 중소기업")
                .build();
        //when
        when(bankSupportRepository.findByRegionCodeCode(anyString())).thenReturn(bankSupportFixture);

        //then
        String result = service.updateBankSupport("",updateBankSupport);
        assertThat(result).isEqualTo("SUCCESS");
        assertThat(bankSupportFixture.getInstitute()).isEqualTo(updateBankSupport.getInstitute());
        assertThat(bankSupportFixture.getLimit()).isEqualTo(updateBankSupport.getLimit());
        assertThat(bankSupportFixture.getMgmt()).isEqualTo(updateBankSupport.getMgmt());
        assertThat(bankSupportFixture.getRate()).isEqualTo(updateBankSupport.getRate());
        assertThat(bankSupportFixture.getReception()).isEqualTo(updateBankSupport.getReception());
        assertThat(bankSupportFixture.getTarget()).isEqualTo(updateBankSupport.getTarget());
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
                    .rate(String.format("%s%%~%s%%", i+1, i+3))
                    .institute("강릉시" + i)
                    .mgmt("강릉지점" + i)
                    .reception("강릉시 소재 영업점" + i)
                    .build();
            bankSupportList.add(bankSupport);
        }

        return bankSupportList;

    }
}

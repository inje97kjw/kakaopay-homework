package com.kakaopay.support.bank.service;

import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.entity.RegionCode;
import com.kakaopay.support.bank.model.search.ResponceBankSupport;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
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

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankSupportService.class)
public class BankSupportServiceTest {
    @Autowired
    private BankSupportService service;

    @MockBean
    private RegionCodeRepository regionCodeRepository;

    @MockBean
    private BankSupportRepository bankSupportRepository;

    @Test
    public void uploadData() throws Exception {
        //give
        String fileName = "init_data.csv";
        InputStream file = new ClassPathResource(fileName).getInputStream();
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, file);
        //when
        String result = service.saveUploadData(multipartFile);
        //then
        assertThat(result).isEqualTo("success");
    }

    @Test
    public void getSupportListTest() {
        //give
        RequestBankSupport request = RequestBankSupport.builder().build();

        RegionCode regionCode = RegionCode.builder()
                .name("강릉시")
                .code("REG0001")
                .build();

        BankSupport bankSupport = BankSupport.builder()
                .regionCode(regionCode)
                .target("강릉소재 중소기업")
                .usage("운전")
                .limit("8억원 이내")
                .rate("2.5%~3.0$%")
                .institute("강릉시")
                .mgmt("강릉지점")
                .reception("강릉시 소재 영업점")
                .build();

        //when
        when(bankSupportRepository.findAll()).thenReturn(Arrays.asList(bankSupport));

        //then

        List<ResponceBankSupport> bankSupportList = service.getBankSupportList(request);
        assertThat(bankSupportList.get(0)).isEqualTo(bankSupport);
    }
}

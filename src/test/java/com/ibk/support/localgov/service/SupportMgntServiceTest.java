package com.ibk.support.localgov.service;

import com.ibk.support.localgov.entity.SupportInfo;
import com.ibk.support.localgov.repository.LocalGovRepository;
import com.ibk.support.localgov.repository.SupportInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupportMgntService.class)
public class SupportMgntServiceTest {
    @Autowired
    private SupportMgntService service;

    @MockBean
    private LocalGovRepository localGovRepository;

    @MockBean
    private SupportInfoRepository supportInfoRepository;

    @Test
    public void uploadData() throws Exception {
        //give
        String fileName = "init_data.csv";
        InputStream file = new ClassPathResource(fileName).getInputStream();
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, file);
        //when
        service.saveUploadData(multipartFile);
        //then
    }

    @Test
    public void getSupportListTest() {
        //give
        SupportInfo supportInfo = new SupportInfo();
        supportInfo.setRegionCode("reg453");
        supportInfo.setTarget("강릉소재 중소기업");
        supportInfo.setReason("운전");
        supportInfo.setLimit("8억원 이내");
        supportInfo.setReward("2.5%~3.0$%");
        supportInfo.setRecommend("강릉시");
        supportInfo.setManager("강릉지점");
        supportInfo.setDealer("강릉시 소재 영업점");
        supportInfo.setLat(0.0);
        supportInfo.setLon(0.0);

        //when
        when(supportInfoRepository.findAll()).thenReturn(Arrays.asList(supportInfo));

        //then
        List<SupportInfo> supportInfoList = service.getSupportList();
        assertThat(supportInfoList.get(0)).isEqualTo(supportInfo);
    }
}

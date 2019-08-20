package com.ibk.support.localgov.service;

import com.ibk.support.localgov.entity.Region;
import com.ibk.support.localgov.entity.SupportInfo;
import com.ibk.support.localgov.model.search.SearchRequest;
import com.ibk.support.localgov.model.search.SearchSupportInfo;
import com.ibk.support.localgov.repository.RegionRepository;
import com.ibk.support.localgov.repository.SupportInfoRepository;
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
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupportMgntService.class)
public class SupportMgntServiceTest {
    @Autowired
    private SupportMgntService service;

    @MockBean
    private RegionRepository regionRepository;

    @MockBean
    private SupportInfoRepository supportInfoRepository;

    @Test
    public void uploadData() throws Exception {
        //give
        String fileName = "init_data.csv";
        InputStream file = new ClassPathResource(fileName).getInputStream();
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, file);
        //when
        String result = service.saveUploadData(multipartFile);
        //then
        assertThat(result).isEqualTo("업로드에 성공 했습니다.");
    }

    @Test
    public void getSupportListTest() {
        //give
        SearchRequest request = new SearchRequest();

        Region region = new Region();
        region.setRegion("강릉시");
        region.setRegionCode("REG0001");

        SupportInfo supportInfo = new SupportInfo();
        supportInfo.setRegion(region);
        supportInfo.setTarget("강릉소재 중소기업");
        supportInfo.setUsage("운전");
        supportInfo.setLimit("8억원 이내");
        supportInfo.setRate("2.5%~3.0$%");
        supportInfo.setInstitute("강릉시");
        supportInfo.setMgmt("강릉지점");
        supportInfo.setReception("강릉시 소재 영업점");
        supportInfo.setRegDate(new Date());
        supportInfo.setUpdateDate(new Date());
        //when
        when(supportInfoRepository.findAll()).thenReturn(Arrays.asList(supportInfo));

        //then

        List<SearchSupportInfo> supportInfoList = service.getSupportInfoList(request);
        assertThat(supportInfoList.get(0)).isEqualTo(supportInfo);
    }
}

package com.ibk.support.localgov.service;

import com.ibk.support.localgov.entity.Region;
import com.ibk.support.localgov.entity.SupportInfo;
import com.ibk.support.localgov.model.search.SearchRequest;
import com.ibk.support.localgov.model.search.SearchSupportInfo;
import com.ibk.support.localgov.model.UpdateSupportInfo;
import com.ibk.support.localgov.repository.SupportInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SupportMgntService {
    @Autowired
    private SupportInfoRepository supportInfoRepository;

    public String saveUploadData(MultipartFile file) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), "MS949");
            BufferedReader br = new BufferedReader(inputStreamReader);
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                //1,강릉시,강릉시 소재 중소기업으로서 강릉시장이 추천한 자,운전,추천금액 이내,3%,강릉시,강릉지점,강릉시 소재 영업점
                String[] values = line.split(",");

                if (values.length < 10) {
                    String regionCode = String.format("REG%04d", Integer.parseInt(values[0]));
                    Region region = new Region();
                    region.setRegionCode(regionCode);
                    region.setRegion(values[1]);

                    SupportInfo supportInfo = new SupportInfo();
                    supportInfo.setId(values[0]);
                    supportInfo.setRegion(region);
                    supportInfo.setTarget(values[2]);
                    supportInfo.setUsage(values[3]);
                    supportInfo.setLimit(values[4]);
                    supportInfo.setRate(values[5]);
                    supportInfo.setInstitute(values[6]);
                    supportInfo.setMgmt(values[7]);
                    supportInfo.setReception(values[8]);
                    supportInfo.setRegDate(new Date());
                    supportInfo.setUpdateDate(new Date());

                    supportInfoRepository.save(supportInfo);
                }
            }
        } catch (Exception e) {
            log.info("데이터 저장에 실패 했습니다. : %s", e);
            return "fail";
        }
        return "success";
    }

    public String updateSupportInfo(String resionCode, UpdateSupportInfo updateSupportInfo) {
        SupportInfo supportInfo = supportInfoRepository.findByRegionRegionCode(resionCode);
        supportInfo.setTarget(updateSupportInfo.getTarget());
        supportInfo.setUsage(updateSupportInfo.getUsage());
        supportInfo.setLimit(updateSupportInfo.getLimit());
        supportInfo.setRate(updateSupportInfo.getRate());
        supportInfo.setInstitute(updateSupportInfo.getInstitute());
        supportInfo.setMgmt(updateSupportInfo.getMgmt());
        supportInfo.setReception(updateSupportInfo.getReception());
        supportInfo.setUpdateDate(new Date());
        supportInfoRepository.save(supportInfo);
        return "success";
    }

    public List<SearchSupportInfo> getSupportInfoList(SearchRequest searchRequest) {
        List<SearchSupportInfo> searchSupportInfoList = supportInfoRepository.search();
        if (CollectionUtils.isEmpty(searchSupportInfoList)) {
            return Collections.EMPTY_LIST;
        }
        if (searchSupportInfoList.size() < searchRequest.getCount()) {
            searchRequest.setCount(searchSupportInfoList.size());
        }

        return searchRequest.getSort().getResultType(searchSupportInfoList, searchRequest.getCount());
    }

    public SearchSupportInfo getSupportInfo(SearchRequest searchRequest) {
        return supportInfoRepository.findOne(searchRequest);
    }

}

package com.ibk.support.localgov.service;

import com.ibk.support.localgov.entity.LocalGovCode;
import com.ibk.support.localgov.entity.SupportInfo;
import com.ibk.support.localgov.model.SearchRequest;
import com.ibk.support.localgov.repository.SupportInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
                String regionCode = String.format("REG%04d", Integer.parseInt(values[0]));
                LocalGovCode localGovCode = new LocalGovCode();
                localGovCode.setLocalGovCode(regionCode);
                localGovCode.setLocalGovName(values[1]);

                SupportInfo supportInfo = new SupportInfo();
                supportInfo.setId(values[0]);
                supportInfo.setLocalGovCode(localGovCode);
                supportInfo.setLocalGovName(values[1]);
                supportInfo.setTarget(values[2]);
                supportInfo.setReason(values[3]);
                supportInfo.setLimit(values[4]);
                supportInfo.setReward(values[5]);
                supportInfo.setRecommend(values[6]);
                supportInfo.setManager(values[7]);
                supportInfo.setDealer(values[8]);
                supportInfo.setRegDate(new Date());
                supportInfo.setUpdateDate(new Date());

                supportInfoRepository.save(supportInfo);
            }
        } catch (Exception e) {
            log.info("데이터 저장에 실패 했습니다. : %s", e);
            return "fail";
        }
        return "success";
    }

    public List<SupportInfo> getSupportList(SearchRequest searchRequest) {
        return supportInfoRepository.findAll();
    }

}

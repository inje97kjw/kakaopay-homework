package com.ibk.support.localgov.service;

import com.ibk.support.localgov.entity.SupportInfo;
import com.ibk.support.localgov.repository.SupportInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class SupportMgntService {
    @Autowired
    private SupportInfoRepository supportInfoRepository;

    public String saveUploadData(MultipartFile file) throws Exception {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

            }
        } catch (IOException ioe) {
            throw new Exception("업로드에 실패 했습니다.");
        }
        return "업로드에 성공 했습니다.";
    }

    public List<SupportInfo> getSupportList() {
        return supportInfoRepository.findAll();
    }

}

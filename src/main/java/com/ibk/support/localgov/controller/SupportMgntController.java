package com.ibk.support.localgov.controller;

import com.ibk.support.localgov.entity.SupportInfo;
import com.ibk.support.localgov.service.SupportMgntService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/support")
public class SupportMgntController {

    @Autowired
    private SupportMgntService supportMgntService;

    @PostMapping("/uploadData")
    @ResponseBody
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {
        String result = "";
        try {
            result = supportMgntService.saveUploadData(file);
        } catch (Exception e) {
            new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<SupportInfo> getSupports() {
        return supportMgntService.getSupportList();
    }
}

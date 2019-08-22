package com.kakaopay.support.bank.controller;

import com.kakaopay.support.bank.model.ApiResult;
import com.kakaopay.support.bank.model.UpdateBankSupport;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.service.BankSupportService;
import com.kakaopay.support.bank.service.format.CustomFormat;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank-support")
public class BankSupportController {

    private BankSupportService bankSupportService;

    public BankSupportController(BankSupportService  bankSupportService) {
        this.bankSupportService = bankSupportService;
    }

    @ApiOperation(value = "데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API", produces = "multipart/form-data")
    @PostMapping("/upload-support-data")
    public ApiResult uploadData(@ApiParam(name = "file", value = "지자체 지원 정보 csv", required = true) @RequestParam("file") MultipartFile file) {
        String result = bankSupportService.saveUploadData(file);
        return ApiResult.builder()
                .status(HttpStatus.OK.value())
                .message(result)
                .build();
    }

    @ApiOperation(value = "지원하는 지자체 목록 검색 API")
    @GetMapping("/list")
    public List<CustomFormat> getSupportInfoList() {
        return bankSupportService.getBankSupportListAll();
    }

    @ApiOperation(value = "지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API")
    @GetMapping("/support")
    public CustomFormat getSupportInfo(@ApiParam(name = "requestBankSupport", value = "지자체명 검색정보", required = true) @RequestBody(required = true) RequestBankSupport requestBankSupport) {
        return bankSupportService.getBankSupport(requestBankSupport);
    }

    @ApiOperation(value = "지원하는 지자체 정보 수정 기능 API")
    @PutMapping("/support/{regionCode}")
    public ApiResult updateSupportInfo(@PathVariable String regionCode, @RequestBody(required=true) UpdateBankSupport updateBankSupport) {
        String result = bankSupportService.updateBankSupport(regionCode, updateBankSupport);
        return ApiResult.builder()
                .status(HttpStatus.OK.value())
                .message(result)
                .build();
    }
}


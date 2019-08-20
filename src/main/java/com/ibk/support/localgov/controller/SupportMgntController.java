package com.ibk.support.localgov.controller;

import com.ibk.support.localgov.model.*;
import com.ibk.support.localgov.model.search.SearchRequest;
import com.ibk.support.localgov.model.search.SearchSupportInfo;
import com.ibk.support.localgov.model.search.Sort;
import com.ibk.support.localgov.service.SupportMgntService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/support")
public class SupportMgntController {

    @Autowired
    private SupportMgntService supportMgntService;

    @PostMapping("/uploadData")
    @ResponseBody
    public ApiResult uploadData(@RequestParam("file") MultipartFile file) {
        String result = supportMgntService.saveUploadData(file);
        if (result.equals("success")) {
            return new ApiResult(HttpStatus.OK.value(), result);
        }
        return new ApiResult(HttpStatus.BAD_REQUEST.value(), result);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<SearchSupportInfo> getSupportInfoList(@RequestBody(required=false) SearchRequest searchRequest) {
        if (searchRequest == null) {
            searchRequest = new SearchRequest();
        }
        return supportMgntService.getSupportInfoList(searchRequest);
    }

    @GetMapping("/info")
    @ResponseBody
    public SearchSupportInfo getSupportInfo(@RequestBody SearchRequest searchRequest) {
        return supportMgntService.getSupportInfo(searchRequest);
    }

    @PutMapping("/info/{regionCode}")
    @ResponseBody
    public String updateSupportInfo(@PathVariable String regionCode, @RequestBody UpdateSupportInfo updateSupportInfo) {
        return supportMgntService.updateSupportInfo(regionCode, updateSupportInfo);
    }

    @GetMapping("/list/maxLimitRegionList")
    @ResponseBody
    public List<String> getMaxLimitList(@RequestBody(required=false) SearchRequest searchRequest) {
        if (searchRequest == null) {
            searchRequest = new SearchRequest();
        }
        searchRequest.setSort(Sort.LIMIT_DESC);

        List<SearchSupportInfo> searchSupportInfoList = supportMgntService.getSupportInfoList(searchRequest);
        return searchSupportInfoList.stream()
                .map(s -> s.getRegion())
                .collect(Collectors.toList());
    }

    @GetMapping("/list/minRateRegion")
    @ResponseBody
    public List<String> getMinRateList(@RequestBody(required=false) SearchRequest searchRequest) {
        if (searchRequest == null) {
            searchRequest = new SearchRequest();
        }
        searchRequest.setCount(1);
        searchRequest.setSort(Sort.RATE_ASC);

        List<SearchSupportInfo> searchSupportInfoList = supportMgntService.getSupportInfoList(searchRequest);
        return searchSupportInfoList.stream()
                .map(s -> s.getRegion())
                .collect(Collectors.toList());
    }
}

package com.kakaopay.support.bank.controller;

import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.service.BankSupportService;
import com.kakaopay.support.bank.service.format.BasicFormat;
import com.kakaopay.support.bank.service.format.CustomFormat;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(BankSupportController.class)
public class BankSupportControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BankSupportService bankSupportService;

    private List<CustomFormat> fixtureList = null;
    @Before
    public void init () {
        fixtureList = createBasicFormatFixture(10);
    }

    @Test
    public void 지자체지원_목록조회_OK() throws Exception {
        //given
        given(bankSupportService.getBankSupportListAll()).willReturn(fixtureList);

        //when
        final MockHttpServletResponse response = mvc.perform(get("/bank-support/list"))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotBlank();
    }

    @Test
    public void 지자체지원_목록조회_없음_OK() throws Exception {
        //given
        given(bankSupportService.getBankSupportListAll()).willReturn(null);

        //when
        final MockHttpServletResponse response = mvc.perform(get("/bank-support/list"))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void 해당지자체지원_목록_ERROR() throws Exception {
        //given
        given(bankSupportService.getBankSupport(any(RequestBankSupport.class))).willReturn(fixtureList.get(0));

        //when
        final MockHttpServletResponse response = mvc.perform(get("/bank-support/support"))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void 해당지자체지원_목록_OK() throws Exception {
        //given
        given(bankSupportService.getBankSupport(any(RequestBankSupport.class))).willReturn(fixtureList.get(0));

        //when
        JSONObject json = new JSONObject();
        json.put("region", "REG0001");

        final MockHttpServletResponse response = mvc.perform(get("/bank-support/support")
                .contentType("application/json")
                .content(json.toString()))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotBlank();
    }

    @Test
    public void 해당지자체_수정_ERROR() throws Exception {
        //given
        given(bankSupportService.getBankSupport(any(RequestBankSupport.class))).willReturn(fixtureList.get(0));

        //when
        final MockHttpServletResponse response = mvc.perform(put("/bank-support/support/REG00001")
                .contentType("application/json"))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void 해당지자체_수정_OK() throws Exception {
        //given
        given(bankSupportService.getBankSupport(any(RequestBankSupport.class))).willReturn(fixtureList.get(0));

        //when
        JSONObject json = new JSONObject();
        json.put("target", "강릉시 중소기업");
        json.put("limit", "5억원");

        final MockHttpServletResponse response = mvc.perform(put("/bank-support/support/REG00001")
                .contentType("application/json")
                .content(json.toString()))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void 지자체지원정보_파일업로드_OK() throws Exception {
        //given
        given(bankSupportService.saveUploadData(any(MultipartFile.class))).willReturn("파일업로드성공");

        //when
        MockMultipartFile file = new MockMultipartFile("file", "bank-support.cvs", null, "bank-support".getBytes());

        final MockHttpServletResponse response = mvc.perform(multipart("/bank-support/upload-support-data").file(file))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"status\":200,\"message\":\"파일업로드성공\"}");
    }

    public List<CustomFormat> createBasicFormatFixture(int count) {
        List<CustomFormat> responceList = new ArrayList<>();
        for (int i = 0;i < count;i++) {
            BasicFormat format = BasicFormat.builder()
                    .region("강릉시" + i)
                    .target("강릉소재 중소기업")
                    .usage("운전")
                    .limit(String.format("%s억원 이내", i+1))
                    .rate(String.format("%s%%~%s%%", i+1, i+3))
                    .institute("강릉시" + i)
                    .mgmt("강릉지점" + i)
                    .reception("강릉시 소재 영업점" + i)
                    .build();
            responceList.add(format);
        }

        return responceList;

    }
}


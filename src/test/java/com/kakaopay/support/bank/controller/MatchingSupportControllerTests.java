package com.kakaopay.support.bank.controller;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(MatchingSupportController.class)
public class MatchingSupportControllerTests {
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
    public void 지원한도컬럼_내림차순_정렬_목록_OK() throws Exception {
        //given
        given(bankSupportService.getBankSupportListAll()).willReturn(fixtureList);

        //when
        JSONObject json = new JSONObject();
        json.put("count", "1");

        final MockHttpServletResponse response = mvc.perform(get("/matching-support/max-limit-support")
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
    public void 이차보전컬럼_가장작은기관_OK() throws Exception {
        //given
        given(bankSupportService.getBankSupportListAll()).willReturn(fixtureList);

        //when
        final MockHttpServletResponse response = mvc.perform(get("/matching-support/min-rate-support"))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotBlank();
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


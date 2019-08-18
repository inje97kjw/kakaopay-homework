package com.ibk.support.localgov.controller;

import com.ibk.support.localgov.service.SupportMgntService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(SupportMgntController.class)
public class SupportMgntControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SupportMgntService supportMgntService;

    @Test
    public void list() throws Exception {
        //given

        //when
        final MockHttpServletResponse response = mvc.perform(get("/support/list"))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("hello");
    }
/*

    //when
    final ResultActions actions = mvc.perform(get("/books/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());
    //then
        actions
                .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1L))
            .andExpect(jsonPath("title").value("title"))
            .andExpect(jsonPath("price").value(1000D))*/
}


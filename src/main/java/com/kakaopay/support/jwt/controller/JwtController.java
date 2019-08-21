package com.kakaopay.support.jwt.controller;

import com.kakaopay.support.bank.model.ApiResult;
import com.kakaopay.support.jwt.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/token")
public class JwtController {

    @PostMapping(value = "/signin")
    public ApiResult signin(@RequestBody(required = true) User user, HttpServletResponse response) {

        return null;
    }
}

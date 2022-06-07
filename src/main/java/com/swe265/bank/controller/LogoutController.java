package com.swe265.bank.controller;

import com.swe265.bank.service.LogoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Can Wang
 * @contact canw7@uci.edu
 * @date 5/9/22
 */
@RestController
public class LogoutController {

    @Resource
    private LogoutService logoutService;

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpRequest, @RequestParam("username") String username) {
            HttpSession session = httpRequest.getSession();
            session.removeAttribute(username);
        return logoutService.logout();
    }
}




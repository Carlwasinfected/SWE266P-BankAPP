package com.swe265.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Can Wang
 * @contact canw7@uci.edu
 * @date 5/9/22
 */
@Service
public class LogoutService {
    public ModelAndView logout() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}

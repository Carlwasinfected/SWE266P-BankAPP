package com.swe265.bank.controller;

import com.swe265.bank.service.LoginRegService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Account Operation Controller
 * @author Huang Yuxin, Can Wang
 * @date 2022/5/8
 */
@Controller
@RequestMapping("/account")
public class LoginRegController {

    @Resource
    private LoginRegService loginRegService;

    @RequestMapping("/register")
    public String index() {
        return "signup";
    }

    /**
     * Scenario: valid registration
     * Given the username, password, and initial balance are all valid
     *  When I register with the above valid input
     *  Then an account under the username should be opened with the initial balance
     */
    @GetMapping("/validRegistration")
    @ResponseBody
    public ModelAndView validRegistration(@RequestParam("username") String username,
                                          @RequestParam("password") String password,
                                          @RequestParam("initialBalance") Double initialBalance){
        boolean registerUser = loginRegService.registerUser(username, password, initialBalance);
        ModelAndView mv = new ModelAndView();

        if(registerUser){
            mv.addObject("username", username);
            mv.addObject("balance", initialBalance);
            mv.setViewName("account");
        }else{
            mv.addObject("message", "Register error! Please check your input and try again!");
            mv.setViewName("signup");
        }
        return mv;
    }

    /**
     *
     * Given the username or password are nonexistent or incorrect
     *  When I log in to the bank with the above invalid input
     *  Then the login should fail
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        return loginRegService.loginUser(username, password);
    }



}

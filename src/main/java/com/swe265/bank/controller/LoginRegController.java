package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.service.LoginRegService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Account Operation Controller
 * @author Huang Yuxin
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
    public String validRegistration(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("initialBalance") Double initialBalance){
        boolean registerUser = loginRegService.registerUser(username, password, initialBalance);
        if(registerUser){
            return "Register Success";
        }
        return "Register Success";
    }

    /**
     *
     * Given the username or password are nonexistent or incorrect
     *  When I log in to the bank with the above invalid input
     *  Then the login should fail
     */
    @GetMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        String msg =  loginRegService.loginUser(username, password);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("message", msg);

        return "OK";
    }

}

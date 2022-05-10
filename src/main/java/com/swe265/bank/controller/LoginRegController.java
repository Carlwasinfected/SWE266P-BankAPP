package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.service.LoginRegService;
import com.swe265.bank.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Account Operation Controller
 * @author Huang Yuxin, Can Wang
 * @date 2022/5/8
 */
@Controller
@RequestMapping("/account")
public class LoginRegController {

    @Resource
    private AccountRepository accountRepository;

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
                                          @RequestParam("initialBalance") Double initialBalance,
                                          HttpServletRequest httpRequest,
                                          HttpServletResponse httpResponse){
        Utils.setSessionUserName(httpRequest, httpResponse, username);
        Account acc = accountRepository.findByName(username);
        ModelAndView mv = new ModelAndView();
        if (acc != null) {
            System.out.println("Already has the user "+ username);
            mv.setViewName("signup");
        } else {
            String id = loginRegService.registerUser(username, password, initialBalance);


            if (id != null) {
                mv.addObject("username", username);
                mv.addObject("balance", initialBalance);
                mv.addObject("id", id);
                mv.setViewName("account");
            } else {
                mv.addObject("message", "Register error! Please check your input and try again!");
                mv.setViewName("signup");
            }
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
                        @RequestParam("password") String password,
                              HttpServletRequest httpRequest,
                              HttpServletResponse httpResponse) {
        String sessionUsername = (String) httpRequest.getSession().getAttribute("username");
        if( sessionUsername != null){
            //System.out.println("Already logged in as " + sessionUsername);
            return loginRegService.loginUserWithSession(sessionUsername);
        }
        return loginRegService.loginUser(username, password);
    }



}

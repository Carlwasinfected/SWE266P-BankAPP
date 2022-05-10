package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.service.LoginRegService;
import com.swe265.bank.service.TransactionService;
import com.swe265.bank.utils.AmountValidUtil;
import com.swe265.bank.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

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
@Slf4j
public class LoginRegController {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private LoginRegService loginRegService;

    @Resource
    TransactionService transactionService;

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
            String message = username + ", The name has already been registered";
            System.out.println();
            mv.addObject("message", message);
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




    private String getUsername(HttpServletRequest httpRequest) {
        String username = (String) httpRequest.getSession().getAttribute("username");
        if (username == null) {
            System.out.println("Invalid username");
            return null;
        }
        return username;
    }

    @PostMapping(value = "/transaction")
    public ModelAndView transaction(@RequestParam("id") String id,
                                @RequestParam("amount") String amount,
                                @RequestParam(name = "withdraw", required = false) String withdraw,
                                @RequestParam(name = "deposit", required = false) String deposit) throws Exception {
        Account account = accountRepository.findById(id);
        ModelAndView model = new ModelAndView();
        if (account == null) {
            model.addObject("message", "Invalid User Info");
            model.setViewName("error");
            return model;
        }
        boolean flag = AmountValidUtil.numericInputsCheck(amount);
        if (!flag) {
            model.addObject("message", "Invalid Amount Number");
            model.setViewName("error");
            return model;
        }
        double money = Double.parseDouble(amount);
        if (StringUtils.hasLength(withdraw)) {
            account = transactionService.withdraw(money, account);
        } else {
            account = transactionService.deposit(money, account);
        }
        model.addObject("balance", account.getBalance());
        model.addObject("username", account.getName());
        model.addObject("id", account.getId());
        model.setViewName("account");

        return model;
    }

    @GetMapping("/getInfo")
    public ModelAndView getInfoById(@RequestParam("id") String id){
        ModelAndView model = new ModelAndView();
        Account account = loginRegService.getInfo(id);
        model.addObject("balance", account.getBalance());
        model.addObject("username", account.getName());
        model.addObject("id", account.getId());
        model.setViewName("account");
        return model;
    }



}

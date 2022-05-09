package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Account Operation Controller
 * @author Huang Yuxin
 * @date 2022/5/8
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountRepository accountRepository;


    /**
     * Scenario: valid registration
     * Given the username, password, and initial balance are all valid
     *  When I register with the above valid input
     *  Then an account under the username should be opened with the initial balance
     */
    @GetMapping("/validRegistration/{username}/{password}/{initialBalance}")
    @ResponseBody
    public String validRegistration(@PathVariable("username") String username,
                                    @PathVariable("password") String password,
                                    @PathVariable("initialBalance") Double initialBalance){
        Account account = new Account();
        account.setBalance(initialBalance);
        account.setName(username);
        account.setPassword(password);
        accountRepository.save(account);
        return "Register Success";
    }

}

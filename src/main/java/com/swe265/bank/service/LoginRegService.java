package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.utils.StringValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Huang Yuxin, Can Wang
 * @date 2022/5/8
 */
@Service
@Slf4j
public class LoginRegService {

    @Resource
    private AccountRepository accountRepository;

    public String registerUser(String username, String password, Double initialBalance){
        String userId = UUID.randomUUID().toString();
        accountRepository.saveAccount(userId, initialBalance, username, password);
        return userId;
    }

    public Account getInfo(String id){
        return accountRepository.findById(id);
    }

    /**
     *
     * @param username
     * @param password
     * @return String type: operation result message
     */
    public ModelAndView loginUser(String username, String password) {
        Account account;
        ModelAndView mv = new ModelAndView();
        if (!StringValidatorUtil.isNameOrPasswordValid(username) ||
                !StringValidatorUtil.isNameOrPasswordValid(password)) {
            mv.setViewName("error");
            mv.addObject("message", "Invalid Input!");
        }
        Optional<Account> accountOptional = Optional.ofNullable(accountRepository.findAccountByNameAndPassword(username, password));
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            mv.setViewName("account");
            mv.addObject("id", account.getId());
            mv.addObject("username", account.getName());
            mv.addObject("balance", account.getBalance());
        } else {
            // username and password does not match
            mv.setViewName("error");
            mv.addObject("message", "Your input does not match any user.");
        }

        return mv;
    }

    public ModelAndView loginUserWithSession(String username) {
        Account account;
        ModelAndView mv = new ModelAndView();

        Account acc = accountRepository.findByName(username);
        //System.out.println("all+ " +accountRepository.findAll());
        //System.out.println("loginUserWithSession" + acc.getName());
        if (acc != null) {

            mv.setViewName("account");
            mv.addObject("id", acc.getId());
            mv.addObject("username", acc.getName());
            mv.addObject("balance", acc.getBalance());
        } else {
            // username and password does not match
            System.out.println("No result");
            mv.setViewName("error");
            mv.addObject("message", "Your input does not match any user.");
        }

        return mv;
    }

}

package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.utils.StringValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Huang Yuxin, Can Wang
 * @date 2022/5/8
 */
@Service
public class LoginRegService {

    @Resource
    private AccountRepository accountRepository;

    public boolean registerUser(String username, String password, Double initialBalance){
        Account account = new Account();
        account.setBalance(initialBalance);
        account.setName(username);
        account.setPassword(password);
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        Optional<Account> accountOptional = Optional.ofNullable(accountRepository.findByNameAndPassword(username, password));
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            mv.setViewName("account");
            mv.addObject("id", account.getId());
        } else {
            // username and password does not match
            mv.setViewName("error");
            mv.addObject("message", "Your input does not match any user.");
        }

        return mv;
    }

}

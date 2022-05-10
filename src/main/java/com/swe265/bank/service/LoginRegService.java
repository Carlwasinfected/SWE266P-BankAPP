package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.utils.StringValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Huang Yuxin
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
    public String loginUser(String username, String password) {
        Account account;
        if (!StringValidatorUtil.isNameOrPasswordValid(username) ||
                !StringValidatorUtil.isNameOrPasswordValid(password)) return "Invalid Input!";
        Optional<Account> accountOptional = Optional.ofNullable(accountRepository.findByNameAndPassword(username, password));
        if (accountOptional.isPresent()) {
            account = accountOptional.get();

            return "Login OK";
        } else {
            // username and password does not match
            return "Your input cannot match any user in DB.";
        }

    }

}

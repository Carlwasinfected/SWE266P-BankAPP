package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

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
}

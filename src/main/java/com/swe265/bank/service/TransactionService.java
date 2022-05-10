package com.swe265.bank.service;

import com.swe265.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Can Wang
 * @contact canw7@uci.edu
 * @date 5/9/22
 */
@Service
public class TransactionService {

    @Resource
    AccountRepository accountRepository;

    /**
     *
     * @param amount
     * @return true, false
     */
    public boolean deposit(String amount) {
        return true;
    }

    /**
     *
     * @param amount
     * @return 0: invalid input 1: amount exceeds balance 2: ok
     */
    public int withdraw(String amount) {
        return 0;
    }

}

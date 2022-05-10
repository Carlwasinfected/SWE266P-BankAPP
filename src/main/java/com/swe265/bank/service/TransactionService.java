package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Can Wang
 * @contact canw7@uci.edu
 * @date 5/9/22
 */
@Service
public class TransactionService {

    @Resource
    AccountRepository accountRepository;

    public Account withdraw(double money, Account account) {
        BigDecimal bd1 = new BigDecimal(account.getBalance());
        BigDecimal bd2 = new BigDecimal(money);
        double balance = bd1.subtract(bd2).doubleValue();
        accountRepository.updateBalanceById(balance, account.getId());
        return accountRepository.findById(account.getId());
    }

    public Account deposit(Double money, Account account) throws Exception {
        double sum = Double.sum(account.getBalance(), money);
        accountRepository.updateBalanceById(sum, account.getId());
        return accountRepository.findById(account.getId());
    }

}

package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.utils.AmountValidUtil;
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
    public Account deposit(String amount, String id) {
        Account account = accountRepository.findById(id);
        if(account == null){
            return null;
        }
        AmountValidUtil validAmount = new AmountValidUtil();
        validAmount.AmountValidUtil(amount);
        if (validAmount.getAmount() != null) {
            Double depositAmount = validAmount.getAmount();
            double balance = account.getBalance() + depositAmount;
            accountRepository.updateById(balance, id);
        }
        return accountRepository.findById(id);

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

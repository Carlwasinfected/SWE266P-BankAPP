package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.utils.AmountValidUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ruokun Xu, Jing Gu
 * @date 2022/5/10
 */
@RestController
public class TransactionController {
    AccountRepository accountRepository;
    Account account;

    private String getUsername(HttpServletRequest httpRequest) {
        String username = (String) httpRequest.getSession().getAttribute("username");
        if (username == null) {
            System.out.println("Invalid username");
            return null;
        }
        return username;
    }
    @PostMapping(value = "/account")
    public ModelAndView deposit(@RequestParam("id") long id,
                                @RequestParam("amount") String deposit) {
        Account acc = accountRepository.findById(id);
        ModelAndView model = new ModelAndView();
        if (acc == null) {
            model.addObject("message", "Invalid Username");
            model.setViewName("error");
            return model;
        }
        AmountValidUtil validAmount = new AmountValidUtil();
        validAmount.AmountValidUtil(deposit);
        if (validAmount.getAmount() != null) {
            Double depositAmount = validAmount.getAmount();
            account.setBalance(account.getBalance() + depositAmount);
            accountRepository.save(account);
        }
        model.addObject("id", id);
        model.addObject("balance", account.getBalance());
        model.setViewName("account");
        return model;
    }

}

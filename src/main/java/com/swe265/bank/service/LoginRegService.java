package com.swe265.bank.service;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.utils.AmountValidUtil;
import com.swe265.bank.utils.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.swe265.bank.utils.Encrypt;
import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
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

    public String registerUser(String username, String password, String initialBalance) throws NoSuchAlgorithmException {
        String userId = UUID.randomUUID().toString();

        String cipher_pwd = Encrypt.getCipher(password);
        accountRepository.saveAccount(userId, Double.parseDouble(initialBalance), username, cipher_pwd);
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
    public ModelAndView loginUser(String username, String password) throws NoSuchAlgorithmException {
        Account account;
        ModelAndView mv = new ModelAndView();
        if (!AmountValidUtil.namePasswordCheck(username) ||
                !AmountValidUtil.namePasswordCheck(password)) {
            mv.setViewName("error");
            mv.addObject("message", "Invalid Input!");
            return mv;
        }

        String cipher_pwd = Encrypt.getCipher(password);
        Optional<Account> accountOptional = Optional.ofNullable(accountRepository.findAccountByNameAndPassword(username, cipher_pwd));
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            mv.setViewName("account");
            mv.addObject("id", account.getId());
            mv.addObject("username", account.getName());
            mv.addObject("balance", account.getBalance());
        } else {
            // username and password does not match
            mv.setViewName("error");
            mv.addObject("message", "Username or Password error! Please check again!");
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

    public String getPasswordHint(String username){
        Account account = accountRepository.getPasswordHint(username);
        if(account == null){
            return "No username exists!";
        }
        String password = account.getPassword();
        String formatString = "Username '" + username + "' has password: %.2s%s";
        return String.format(
                formatString,
                password,
                String.format("%0" + (password.length() - 2) + "d", 0).replace("0", "*"));

    }
}

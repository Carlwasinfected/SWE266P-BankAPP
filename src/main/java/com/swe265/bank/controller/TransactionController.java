package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.service.TransactionService;
import com.swe265.bank.utils.AmountValidUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author Ruokun Xu, Jing Gu
 * @date 2022/5/10
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Resource
    AccountRepository accountRepository;

    @Resource
    TransactionService transactionService;

    private String getUsername(HttpServletRequest httpRequest) {
        String username = (String) httpRequest.getSession().getAttribute("username");
        if (username == null) {
            System.out.println("Invalid username");
            return null;
        }
        return username;
    }

    @PostMapping(value = "/account")
    public ModelAndView deposit(@RequestParam("id") String id,
                                @RequestParam("amount") String amount,
                                @RequestParam(name = "withdraw", required = false) String withdraw,
                                @RequestParam(name = "deposit", required = false) String deposit) throws Exception {
        Account account = accountRepository.findById(id);
        ModelAndView model = new ModelAndView();
        if (account == null) {
            model.addObject("message", "Invalid User Info");
            model.setViewName("error");
            return model;
        }
        boolean flag = AmountValidUtil.checkAmount(amount);
        if (!flag) {
            model.addObject("message", "Invalid Amount Number");
            model.setViewName("error");
            return model;
        }
        double money = Double.parseDouble(amount);
        if (StringUtils.hasLength(withdraw)) {
            account = transactionService.withdraw(money, account);
        } else {
            account = transactionService.deposit(money, account);
        }
        model.addObject("balance", account.getBalance());
        model.addObject("name", account.getName());
        model.addObject("id", account.getId());
        model.setViewName("account");
        return model;
    }



//    @PostMapping(value = "/account")
//    public ModelAndView withdraw(@RequestParam("amount") String amount,
//                                 HttpServletRequest httpRequest,
//                                 HttpServletResponse httpResponse) {
//        String sessionUsername = (String) httpRequest.getSession().getAttribute("username");
//        Account acc = accountRepository.findByName(sessionUsername);
//
//        ModelAndView model = new ModelAndView();
//        if (acc == null) {
//            model.addObject("message", "Invalid Username");
//            model.setViewName("error");
//            return model;
//        }
//        AmountValidUtil validAmount = new AmountValidUtil();
//        validAmount.AmountValidUtil(amount);
//
//        if (validAmount.getAmount() != null) {
//            Double depositAmount = validAmount.getAmount();
//            if (acc.getBalance() < depositAmount) {
//                model.addObject("message", "Invalid amount");
//                model.setViewName("error");
//                return model;
//            }
//            account.setBalance(acc.getBalance() - depositAmount);
//            accountRepository.save(account);
//        }
//        model.addObject("id", acc.getId());
//        model.addObject("balance", account.getBalance());
//        model.setViewName("account");
//        return model;
//    }
}

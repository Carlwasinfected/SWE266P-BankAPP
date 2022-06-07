package com.swe265.bank.controller;

import com.swe265.bank.entity.Account;
import com.swe265.bank.entity.UserVO;
import com.swe265.bank.repository.AccountRepository;
import com.swe265.bank.service.LoginRegService;
import com.swe265.bank.service.TransactionService;
import com.swe265.bank.utils.AmountValidUtil;
import com.swe265.bank.utils.Encrypt;
import com.swe265.bank.utils.UUIDUtil;
import com.swe265.bank.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;

import static com.swe265.bank.utils.AmountValidUtil.namePasswordCheck;

/**
 * Account Operation Controller
 *
 * @author Huang Yuxin, Can Wang
 * @date 2022/5/8
 */

@Controller
@RequestMapping("/account")
@Slf4j
public class LoginRegController {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private LoginRegService loginRegService;

    @Resource
    TransactionService transactionService;

    @RequestMapping("/register")
    public String index() {
        return "signup";
    }

    /**
     * Scenario: valid registration
     * Given the username, password, and initial balance are all valid
     * When I register with the above valid input
     * Then an account under the username should be opened with the initial balance
     */
    @GetMapping("/validRegistration")
    @ResponseBody
    public ModelAndView validRegistration(@RequestParam("username") String username,
                                          @RequestParam("password") String password,
                                          @RequestParam("initialBalance") String initialBalance,
                                          HttpServletRequest httpRequest,
                                          HttpServletResponse httpResponse) throws NoSuchAlgorithmException {
        ModelAndView mv = new ModelAndView();
        // check valid input parameter
        if (!namePasswordCheck(username) ||
                !namePasswordCheck(password) ||
                !AmountValidUtil.numericInputsCheck(initialBalance)) {
            String message = "the name or password or initialBalance invalid_input";
            mv.addObject("message", message);
            mv.setViewName("signup");
            return mv;
        }

        // check username has been register or not
        Account acc = accountRepository.findByName(username);
        if (acc != null) {
            String message = username + ", The name has already been registered";
            mv.addObject("message", message);
            mv.setViewName("signup");
            return mv;
        }
        // register user
        String id = loginRegService.registerUser(username, password, initialBalance);

        if (id != null) {
            mv.addObject("username", username);
            mv.addObject("balance", initialBalance);
            mv.addObject("id", id);
            mv.setViewName("account");
            Utils.setSessionUserName_R(httpRequest, httpResponse, username, Encrypt.getCipher(password));
        } else {
            mv.addObject("message", "Register error! Please check your input and try again!");
            mv.setViewName("signup");
        }
        return mv;
    }

    /**
     * Given the username or password are nonexistent or incorrect
     * When I log in to the bank with the above invalid input
     * Then the login should fail
     */
    @PostMapping("/login")
    public ModelAndView login(UserVO userVO,
                              HttpServletRequest httpRequest,
                              HttpServletResponse httpResponse) throws NoSuchAlgorithmException {
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        // check username and password
        String sessionUsername = (String) httpRequest.getSession().getAttribute("username");
        // check session user equal current username
        if (sessionUsername != null
                && username.equals(sessionUsername)) {
            return loginRegService.loginUserWithSession(sessionUsername);
        }
        ModelAndView modelAndView = loginRegService.loginUser(username, password);
        Utils.setSessionUserName(httpRequest, httpResponse, username, Encrypt.getCipher(password));
        return modelAndView;
    }

    @PostMapping(value = "/transaction")
    public ModelAndView transaction(@RequestParam("id") String id,
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
        boolean flag = AmountValidUtil.numericInputsCheck(amount);
        if (!flag) {
            model.addObject("message", "Invalid Amount Number");
            model.setViewName("error");
            return model;
        }

        double money = Double.parseDouble(amount);

        // check withdraw operation has enough balance to avoid negative balance
        int compare = AmountValidUtil.doubleCompare(money, account.getBalance());
        if (StringUtils.hasLength(withdraw) && compare >= 1) {
            model.addObject("message", "Invalid Amount Number, balance is not enough, Operation fail");
            model.addObject("username", account.getName());
            model.addObject("balance", account.getBalance());
            model.addObject("id", account.getId());
            model.setViewName("account");
            return model;
        }
        // operation divide
        if (StringUtils.hasLength(withdraw)) {
            account = transactionService.withdraw(money, account);
        } else {
            account = transactionService.deposit(money, account);
        }
        model.addObject("balance", account.getBalance());
        model.addObject("username", account.getName());
        model.addObject("id", account.getId());
        model.addObject("message", "Operation Success! Please click refresh to check your balance");
        model.setViewName("account");

        return model;
    }

    @GetMapping("/getInfo")
    public ModelAndView getInfoById(@RequestParam("id") String id) {
        ModelAndView model = new ModelAndView();
        boolean validUUID = UUIDUtil.isValidUUID(id);
        if (!validUUID) {
            model.setViewName("error");
            model.addObject("message", "Username or Password error! Please check again!");
            return model;
        }
        Account account = loginRegService.getInfo(id);
        model.addObject("balance", account.getBalance());
        model.addObject("username", account.getName());
        model.addObject("id", account.getId());
        model.setViewName("account");
        return model;
    }

    @RequestMapping("/hint")
    public String getPasswordHint() {
        return "hint";
    }

    @GetMapping("/passwordHint")
    public ModelAndView getPasswordHint(@RequestParam("username") String username) {
        ModelAndView model = new ModelAndView();
        if (!namePasswordCheck(username)) {
            model.addObject("message", "Invalid Username");
        } else {
            String passwordHint = loginRegService.getPasswordHint(username);
            model.addObject("message", passwordHint);
        }
        model.setViewName("password");
        return model;
    }
}



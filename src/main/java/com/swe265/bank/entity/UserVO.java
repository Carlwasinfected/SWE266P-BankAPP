package com.swe265.bank.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;

/**
 * @author Huang Yuxin
 * @date 2022/6/6
 */
@Data
@ConditionalOnEnabledResourceChain
public class UserVO {
    /**
     * username
     */
    private String username;

    /**
     * password of user
     */
    private String password;

    /**
     * register money when initial user
     */
    String initialBalance;
}

package com.swe265.bank.entity;


import lombok.Data;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;

/**
 *  This is an entity to record user info
 */
@Data
@ConditionalOnEnabledResourceChain
public class Account {
    /**
     * user id
     */
//    @Id
//    @Column(name = "id")
    private String id;

    /**
     * username
     */
//    @Column(name = "name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'username'")
    private String name;

    /**
     * password of user
     */
//    @Column(name = "password", columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'password'")
    private String password;

    /**
     * money in account
     */
//    @Column(name = "balance")
    private double balance;

}

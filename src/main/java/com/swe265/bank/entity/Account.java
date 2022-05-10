package com.swe265.bank.entity;


import lombok.Data;

import javax.persistence.*;

/**
 *  This is an entity to record user info
 */
@Data
@Entity
@Table(name = "account")
public class Account {
    /**
     * user id
     */
    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * username
     */
    @Column(name = "name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'username'")
    private String name;

    /**
     * password of user
     */
    @Column(name = "password", columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'password'")
    private String password;

    /**
     * money in account
     */
    @Column(name = "balance", scale = 2)
    private double balance;

}

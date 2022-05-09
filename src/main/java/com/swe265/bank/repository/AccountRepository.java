package com.swe265.bank.repository;

import com.swe265.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Huang Yuxin
 * @date 2022/5/8
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByName(String name);
  Account findByNameAndPassword(String name, String password);
}

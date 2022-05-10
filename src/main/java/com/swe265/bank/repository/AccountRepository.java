package com.swe265.bank.repository;

import com.swe265.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Huang Yuxin
 * @date 2022/5/8
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByName(String name);
  Account findByNameAndPassword(String name, String password);


  @Transactional
  @Query(value = "insert into bank (userId, username, password, amount) to bank values (?1, ?2, ?3, ?4)", nativeQuery = true)
  account saveAccount(String userId, String username, String password, double amount);
}

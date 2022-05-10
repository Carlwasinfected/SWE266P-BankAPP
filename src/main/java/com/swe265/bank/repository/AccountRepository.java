package com.swe265.bank.repository;

import com.swe265.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Query(value = "select * from account where name=(?1) and password=(?2)", nativeQuery = true)
  Account findAccountByNameAndPassword(String name, String password);  // used by `signin` feature


  @Modifying
  @Transactional
  @Query(value = "insert into account(id, balance, name, password) values (?1, ?2, ?3, ?4)", nativeQuery = true)
  void saveAccount(String userId, double balance, String username, String password);

  @Modifying
  @Transactional
  @Query(value = "update account set balance = ?1 where id = ?2", nativeQuery = true)
  void updateBalanceById(Double balance, String userId);

  @Modifying
  @Transactional
  @Query(value = "select * from account where id = ?1", nativeQuery = true)
  Account findById(String userId);
}

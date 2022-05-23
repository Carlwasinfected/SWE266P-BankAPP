package com.swe265.bank.repository;

import com.swe265.bank.entity.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @author Huang Yuxin
 * @date 2022/5/8
 */

@Component
public class AccountRepository {
    @Value("${spring.datasource.url}")
    String databaseUrl;

    @Value("${spring.datasource.driver-class-name}")
    String drive;

    @Value("${spring.datasource.username}")
    String password;

    @Value("${spring.datasource.password}")
    String username;


    public Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(drive);
        String url = databaseUrl;
        Connection con = DriverManager.getConnection(url, password, username);
        return con;
    }


    public Account findByName(String username) {
        Account account = null;
        Connection connect = null;
        try {
            connect = createConnection();
            String sql = "SELECT * FROM account WHERE name = '" + username + "'";
            Statement statement = connect.createStatement();
            statement = connect.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery(sql);
            if (result.first()) {
                account = new Account();
                account.setPassword(result.getString("password"));
                account.setId(result.getString("id"));
                account.setName(result.getString("name"));
                account.setBalance(Double.parseDouble(result.getString("balance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return account;
    }

    public Account findAccountByNameAndPassword(String name, String password) {
        Account account = null;
        Connection connect = null;
        try {
            connect = createConnection();
            String sql = "SELECT * FROM account WHERE name = '" + name + "' and password = '" + password + "'";
            Statement statement = connect.createStatement();
            statement = connect.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery(sql);
            if (result.first()) {
                account = new Account();
                account.setPassword(result.getString("password"));
                account.setId(result.getString("id"));
                account.setName(result.getString("name"));
                account.setBalance(Double.parseDouble(result.getString("balance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return account;
    }


    public void saveAccount(String userId, double balance, String username, String password) {
        Connection connect = null;
        try {
            connect = createConnection();
            String sql = "insert into account(id, balance, name, password) values" +
                    " ( '" + userId + "', +" + balance + " , '" + username + "' , '" + password + "' )";
            Statement statement = connect.createStatement();
            statement = connect.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateBalanceById(Double balance, String userId) {
        Connection connect = null;
        try {
            connect = createConnection();
            String sql = "update account set balance = '" + balance + "' where id = '" + userId + "'";
            Statement statement = connect.createStatement();
            statement = connect.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Account findById(String userId){
        Account account = null;
        Connection connect = null;
        try {
            connect = createConnection();
            String sql = "SELECT * FROM account WHERE id = '" + userId + "'";
            Statement statement = connect.createStatement();
            statement = connect.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery(sql);
            if (result.first()) {
                account = new Account();
                account.setPassword(result.getString("password"));
                account.setId(result.getString("id"));
                account.setName(result.getString("name"));
                account.setBalance(Double.parseDouble(result.getString("balance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return account;
    }

    public Account getPasswordHint(String username) {
        Account account = null;
        Connection connect = null;
        try {
            connect = createConnection();
            String sql = "SELECT * FROM account WHERE name = '" + username + "'";
            Statement statement = connect.createStatement();
            statement = connect.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery(sql);
            if (result.first()) {
                account = new Account();
                account.setPassword(result.getString("password"));
                account.setId(result.getString("id"));
                account.setName(result.getString("name"));
                account.setBalance(Double.parseDouble(result.getString("balance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return account;
    }


}

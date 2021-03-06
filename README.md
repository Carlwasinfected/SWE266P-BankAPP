# SWE266P-BankAPP
> Team Member: [Can Wang](mailto:canw7@uci.edu), Ruokun Xu, Yuxin Huang, Jing Gu

## A. How to set-up

The user can set up the web application and its local MySQL environment by following the instructions [here](https://github.com/Carlwasinfected/SWE266P-BankAPP/blob/master/README.md).

- Download the source code from our GitHub repository [here](https://github.com/Carlwasinfected/SWE266P-BankAPP.git). Type the Git command below.
    
    `git clone https://github.com/Carlwasinfected/SWE266P-BankAPP.git`
    
- Open your local MySQL connection, and create a new schema named `bank`. Remember to set the charset as UTF-8. The following example is using `MySQLWorkbench`.
    
    ![*Fig 1, Create schema of MySqlWorkBench*](https://github.com/Carlwasinfected/hximgs/blob/main/data/mysqlworkbench.png)
    
    *Fig 1, Create schema of MySqlWorkBench*
    
- Open the project via IntelliJ IDEA, Goto `src/main/resources/application.yml`.
    - Set your own MySQL username and password in `username` and `password` field.
    - Before the next step, please make sure your connection to MySQL is available!
- Go to the root path of the project and execute the SQL script named `account.sql`. It will create an account table, and initialize a user, whose username is `admin`; password is `admin` and balance is `100.00`.
- Go to `src/main/java/com/swe265/bank/BankApplication.java`, and start the project locally.
- Go to [`http://127.0.0.1:8081/bank`](http://127.0.0.1:8081/bank), and start your exploration!

If you successfully run the application locally, the login page should pop up and it should look like the image below.

![*Fig 2. The login page*](https://github.com/Carlwasinfected/hximgs/blob/main/data/loginpage.png)

*Fig 2. The login page*

## B. How to explore features

- Registration: Click "Sign up" on the website
- Login: There is no default account, you should create an admin account by yourself
- Forget Password: You could click the password hint if you forget your password
- Check Balance: Once you log in, your account balance will display on the website automatically.
After the transaction, you could use the Button "Refresh Balance" to refresh your balance.
- Withdraw and Deposit: After you log in, you could input an amount that you want to deposit or withdraw. After pressing the button "Withdraw" or "Deposit", Your operation will work successfully.

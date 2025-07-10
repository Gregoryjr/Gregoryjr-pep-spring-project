package com.example.service;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
     public AccountRepository accountRepository;
@Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


public Optional<Account> getAccountById(Integer id){
    return accountRepository.findById(id);

}
public Optional<Account> getAccountByUser(String user){
    return accountRepository.findByUsername(user);
}

      public Account persistAccount(Account account){
        return accountRepository.save(account);
    }

    public Optional<Account> login(String user, String pass){
       return accountRepository.findByUsernameAndPassword(user, pass);


    }





}

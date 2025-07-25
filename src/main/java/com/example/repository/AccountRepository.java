package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

 Optional<Account> findByUsername(String username);
 Optional<Account> findByUsernameAndPassword(String username, String password);

}

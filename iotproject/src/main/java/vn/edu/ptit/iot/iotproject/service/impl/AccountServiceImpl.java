package vn.edu.ptit.iot.iotproject.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vn.edu.ptit.iot.iotproject.entity.Account;
import vn.edu.ptit.iot.iotproject.payload.AccountLogin;
import vn.edu.ptit.iot.iotproject.repository.AccountRepository;
import vn.edu.ptit.iot.iotproject.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccount() {
        try {
            return accountRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return Collections.emptyList(); // Return an appropriate value or handle it differently based on your use case
        }
    }

    @Override
    public AccountLogin accountLogin(String code, String password) {
        try {
            Account account = accountRepository.findByCodeAndPassword(code, password);
            boolean isAdmin = false;
            if (account != null) {
                if (account.getStudent() == null) {
                    isAdmin = true;
                }
                return new AccountLogin(account.getName(), account.getCode(), isAdmin, true);
            } else {
                return new AccountLogin(null, null, isAdmin, false);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return new AccountLogin(null, null, false, false); // Return an appropriate value or handle it differently based on your use case
        }
    }

    @Override
    public Account getStudentByRfid(String rfid) {
        try {
            return accountRepository.findStudentByRfid(rfid);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return null; // Return an appropriate value or handle it differently based on your use case
        }
    }
}


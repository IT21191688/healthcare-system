package com.cus.healthcare.system.service.impl;


import com.cus.healthcare.system.entity.UserRole;
import com.cus.healthcare.system.repo.UserRoleRepo;
import com.cus.healthcare.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public void initializeRoles() {
        if (userRoleRepo.count()==0){
            UserRole admin = new UserRole(1,"ADMIN","admin",null);
            UserRole doc = new UserRole(2,"USER","user",null);
            userRoleRepo.saveAll(List.of(admin,doc));
        }

    }
}

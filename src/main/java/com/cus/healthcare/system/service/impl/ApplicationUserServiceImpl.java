package com.cus.healthcare.system.service.impl;

import com.cus.healthcare.system.auth.ApplicationUser;
import com.cus.healthcare.system.entity.User;
import com.cus.healthcare.system.entity.UserRoleHasUser;
import com.cus.healthcare.system.repo.UserRepo;
import com.cus.healthcare.system.repo.UserRoleHasUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cus.healthcare.system.security.ApplicationUserRole.ADMIN;
import static com.cus.healthcare.system.security.ApplicationUserRole.DOCTOR;


@Service
public class ApplicationUserServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;

    public ApplicationUserServiceImpl(UserRepo userRepo, UserRoleHasUserRepo userRoleHasUserRepo) {
        this.userRepo = userRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User selectedUser = userRepo.findByUsername(username);
        if (selectedUser==null){
            throw new UsernameNotFoundException(String.format("username %s not found",username));
        }

        //change
        List<UserRoleHasUser> userRoles = userRoleHasUserRepo.findByUserId(selectedUser.getId());
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for (UserRoleHasUser userRole:userRoles){
            if (userRole.getUserRole().getRoleName().equals("ADMIN")){
                grantedAuthorities.addAll(ADMIN.getSimpleGrantedAuthorities());
            }
            if (userRole.getUserRole().getRoleName().equals("USER")){
                grantedAuthorities.addAll(DOCTOR.getSimpleGrantedAuthorities());
            }
        }

        return new ApplicationUser(
                grantedAuthorities,
                selectedUser.getPassword(),
                selectedUser.getUsername(),
                selectedUser.isAccountNonExpired(),
                selectedUser.isAccountNonLocked(),
                selectedUser.isCredentialsNonExpired(),
                selectedUser.isEnabled()
        );

    }
}
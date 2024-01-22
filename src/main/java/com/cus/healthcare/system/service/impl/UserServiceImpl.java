package com.cus.healthcare.system.service.impl;


import com.cus.healthcare.system.dto.request.RequestUserDto;
import com.cus.healthcare.system.entity.User;
import com.cus.healthcare.system.entity.UserRole;
import com.cus.healthcare.system.entity.UserRoleHasUser;
import com.cus.healthcare.system.exception.EntryNotFoundException;
import com.cus.healthcare.system.jwt.JwtConfig;
import com.cus.healthcare.system.repo.UserRepo;
import com.cus.healthcare.system.repo.UserRoleHasUserRepo;
import com.cus.healthcare.system.repo.UserRoleRepo;
import com.cus.healthcare.system.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserRoleRepo userRoleRepo, UserRoleHasUserRepo userRoleHasUserRepo,
                           PasswordEncoder passwordEncoder, JwtConfig jwtConfig, SecretKey secretKey) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public String signup(RequestUserDto userDto) {
        UserRole userRole;

        if (userDto.getRoleId() == 1) {
            userRole = userRoleRepo.findUserRoleByName("ADMIN");
        } else {
            userRole = userRoleRepo.findUserRoleByName("USER");
        }

        if (userRole == null) {
            throw new RuntimeException("User role not found");
        }

        // Generate a random 10-digit user ID
        String userId = generateRandomUserId();

        User user = new User(
                userId,
                userDto.getFullName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRoleId(),
                true,
                true,
                true,
                true,
                null
        );

        UserRoleHasUser userData = new UserRoleHasUser(user, userRole);
        userRepo.save(user);
        userRoleHasUserRepo.save(userData);

        return userId;
    }

    private String generateRandomUserId() {
        Random random = new Random();
        // Generate a random integer with 10 digits
        int userId = 100_000_000 + random.nextInt(900_000_000);
        return String.valueOf(userId);
    }

    @Override
    public boolean verifyUser(String type, String token) {

        String realToken =
                token.replace(jwtConfig.getTokenPrefix(),"");
        System.out.println(type);
        System.out.println(token);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(realToken);
        String username = claimsJws.getBody().getSubject();
        User selectedUser = userRepo.findByUsername(username);
        if(null==selectedUser){
            throw new EntryNotFoundException("Username not found!");
        }

        for(UserRoleHasUser roleUser:selectedUser.getUserRoleHasUsers()){
            if (roleUser.getUserRole().getRoleName().equals(type)){
                return true;
            }
        }
        return false;
    }
}

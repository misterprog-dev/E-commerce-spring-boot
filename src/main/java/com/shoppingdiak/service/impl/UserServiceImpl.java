package com.shoppingdiak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingdiak.model.User;
import com.shoppingdiak.repository.PasswordResetTokenRepository;
import com.shoppingdiak.repository.RoleRepository;
import com.shoppingdiak.repository.UserRepository;
import com.shoppingdiak.security.PasswordResetToken;
import com.shoppingdiak.security.UserRole;
import com.shoppingdiak.service.UserService;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PasswordResetToken getPasswordTokenForUser(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenUser(User user, String token) {
        final PasswordResetToken mytoken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(mytoken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception{
        User localUser = userRepository.findByUsername(user.getUsername());
        if(localUser != null){
            user.setId(localUser.getId());
            userRepository.updateEmail(user.getId(),localUser.getEmail());
        }else{
            for(UserRole ur : userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRole().addAll(userRoles);
            localUser = userRepository.save(user);
        }
        return localUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}

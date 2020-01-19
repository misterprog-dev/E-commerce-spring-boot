package com.shoppingdiak.service;

import com.shoppingdiak.model.User;
import com.shoppingdiak.security.PasswordResetToken;
import com.shoppingdiak.security.UserRole;

import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordTokenForUser(final String token);
    
    void createPasswordResetTokenUser(final User user, final String token);
    
    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}

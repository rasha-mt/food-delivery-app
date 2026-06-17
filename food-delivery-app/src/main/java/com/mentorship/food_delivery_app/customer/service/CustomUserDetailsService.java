package com.mentorship.food_delivery_app.customer.service;

import com.mentorship.food_delivery_app.common.secuirty.UserPrincipal;
import com.mentorship.food_delivery_app.user.model.User;
import com.mentorship.food_delivery_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByUserEmail(email)
                .orElseThrow();

        return UserPrincipal.create(user);
    }


    public UserPrincipal loadUserById(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return UserPrincipal.create(user);
    }
}
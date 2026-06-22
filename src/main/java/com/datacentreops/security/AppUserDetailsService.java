package com.datacentreops.security;

import com.datacentreops.iam.entity.User;
import com.datacentreops.iam.entity.UserStatus;
import com.datacentreops.iam.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No user with email: " + email));

        // ✅ ENUM-based status check
        boolean enabled =
                user.getStatus() != UserStatus.SUSPENDED &&
                        user.getStatus() != UserStatus.INACTIVE;

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword() == null ? "" : user.getPassword())
                .authorities(
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
                .disabled(!enabled)
                .build();
    }
}

package com.dailycodework.dreamshops.security.user;

import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class ShopUserDatailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = Optional.ofNullable(userRepository.findByEmail(email))
               .orElseThrow(() -> new UsernameNotFoundException("User not found!" + email));
        return ShopUserDetails.buildUserDetails(user);
    }
}

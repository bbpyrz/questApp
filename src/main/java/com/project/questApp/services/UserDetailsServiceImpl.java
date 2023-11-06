package com.project.questApp.services;

import com.project.questApp.entities.User;
import com.project.questApp.repos.UserRepository;
import com.project.questApp.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username);

        return JwtUserDetails.create(user);
    }
    public UserDetails loadById(Long id){
        User user=userRepository.findById(id).get();

        return JwtUserDetails.create(user);
    }
}

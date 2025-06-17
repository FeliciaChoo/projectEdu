package com.example.projectEdu.service;

import com.example.projectEdu.repository.FunderRepository;
import com.example.projectEdu.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImp implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired private FunderRepository funderRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return studentRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .or(() -> funderRepository.findByEmail(email).map(CustomUserDetails::new))
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));
    }
}
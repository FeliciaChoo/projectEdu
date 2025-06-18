package com.example.projectEdu.service;

import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Long id;

    private String name;
    private final String email;
    private final String password;

    private String profileUrl;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String name, String email, String password, String profileUrl, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
        this.authorities = List.of(new SimpleGrantedAuthority(role));
    }

    public CustomUserDetails(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.password = student.getPassword();
        this.profileUrl = student.getProfileUrl();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }

    public CustomUserDetails(Funder funder) {
        this.id = funder.getId();
        this.name = funder.getName();
        this.email = funder.getEmail();
        this.password = funder.getPassword();
        this.profileUrl = funder.getProfileUrl();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_FUNDER"));
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    public Long getId(){ return id; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getProfileUrl(){return profileUrl; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}


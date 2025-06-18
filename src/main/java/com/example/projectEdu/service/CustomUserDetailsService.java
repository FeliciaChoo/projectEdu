package com.example.projectEdu.service;
import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.repository.FunderRepository;
import com.example.projectEdu.repository.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final FunderRepository funderRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public CustomUserDetailsService(StudentRepository studentRepo, FunderRepository funderRepo) {
        this.studentRepository = studentRepo;
        this.funderRepository = funderRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email.equalsIgnoreCase("admin@example.com")) {
            return new CustomUserDetails(
                    0L,
                    "Admin",
                    "admin@example.com",
                    passwordEncoder.encode("admin123"),
                    "/images/default-pfp.png",
                    "ROLE_ADMIN"
            );
        }

        Student student = studentRepository.findByEmail(email).orElse(null);
        if (student != null) {
            return new CustomUserDetails(student);
        }

        Funder funder = funderRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetails(funder);
    }

}

package com.example.projectEdu.service;
import com.example.projectEdu.model.Funder;
import com.example.projectEdu.model.Student;
import com.example.projectEdu.repository.FunderRepository;
import com.example.projectEdu.repository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepo;
    private final FunderRepository funderRepo;

    public CustomUserDetailsService(StudentRepository studentRepo, FunderRepository funderRepo) {
        this.studentRepo = studentRepo;
        this.funderRepo = funderRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(email).orElse(null);
        if (student != null) {
            return new CustomUserDetails(student);
        }

        Funder funder = funderRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetails(funder);
    }

}

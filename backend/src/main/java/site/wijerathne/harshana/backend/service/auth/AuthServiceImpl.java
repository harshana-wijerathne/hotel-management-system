package site.wijerathne.harshana.backend.service.auth;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.wijerathne.harshana.backend.dto.SignUpRequest;
import site.wijerathne.harshana.backend.dto.UserDto;
import site.wijerathne.harshana.backend.entity.User;
import site.wijerathne.harshana.backend.enums.UserRole;
import site.wijerathne.harshana.backend.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created Successfully");
        }else{
            System.out.println("Admin account already exists");
        }
    }


    public UserDto createUser(SignUpRequest signUpRequest){
        if(userRepository.findFirstByEmail(signUpRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User Already Present With email" + signUpRequest.getEmail());
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

}

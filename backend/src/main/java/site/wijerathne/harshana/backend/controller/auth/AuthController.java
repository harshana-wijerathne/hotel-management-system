package site.wijerathne.harshana.backend.controller.auth;


import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.wijerathne.harshana.backend.dto.AuthenticationRequest;
import site.wijerathne.harshana.backend.dto.AuthenticationResponse;
import site.wijerathne.harshana.backend.dto.SignUpRequest;
import site.wijerathne.harshana.backend.dto.UserDto;
import site.wijerathne.harshana.backend.entity.User;
import site.wijerathne.harshana.backend.repository.UserRepository;
import site.wijerathne.harshana.backend.service.auth.AuthService;
import site.wijerathne.harshana.backend.service.jwt.UserService;
import site.wijerathne.harshana.backend.util.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;


    @PostMapping("/signup")
    private ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            UserDto createdUser = authService.createUser(signUpRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>("User already Exist", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e) {
            return new ResponseEntity<>("User Not created, try again later", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid email or password");
        }

        final UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        System.out.println(userDetails);
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }

        return authenticationResponse;
    }




}

package site.wijerathne.harshana.backend.controller.auth;


import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.wijerathne.harshana.backend.dto.SignUpRequest;
import site.wijerathne.harshana.backend.dto.UserDto;
import site.wijerathne.harshana.backend.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


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




}

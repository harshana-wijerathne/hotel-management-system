package site.wijerathne.harshana.backend.service.auth;

import site.wijerathne.harshana.backend.dto.SignUpRequest;
import site.wijerathne.harshana.backend.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignUpRequest signUpRequest);
}

package site.wijerathne.harshana.backend.dto;

import lombok.Data;
import site.wijerathne.harshana.backend.enums.UserRole;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}

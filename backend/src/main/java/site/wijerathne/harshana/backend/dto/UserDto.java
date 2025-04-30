package site.wijerathne.harshana.backend.dto;

import lombok.Data;
import site.wijerathne.harshana.backend.enums.UserRole;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}

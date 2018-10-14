package it.alecsferra.letspartylogin.core.model.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResult {

    private String username;

    private String token;

    private Long expireDate;

}
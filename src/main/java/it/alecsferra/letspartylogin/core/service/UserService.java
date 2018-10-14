package it.alecsferra.letspartylogin.core.service;

import it.alecsferra.letspartylogin.core.model.dto.input.LoginUser;
import it.alecsferra.letspartylogin.core.model.dto.output.LoginResult;
import it.alecsferra.letspartylogin.core.model.entity.User;

public interface UserService {

    boolean saveUser(User user);

    User findByUsername(String username);

    LoginResult generateToken(LoginUser loginUser);

}
package it.alecsferra.letspartylogin.core.controller;

import it.alecsferra.letspartylogin.core.Utils;
import it.alecsferra.letspartylogin.core.model.dto.input.LoginUser;
import it.alecsferra.letspartylogin.core.model.dto.input.SignUpUser;
import it.alecsferra.letspartylogin.core.model.dto.output.LoginResult;
import it.alecsferra.letspartylogin.core.model.dto.output.SimpleResult;
import it.alecsferra.letspartylogin.core.model.dto.output.UserInfo;
import it.alecsferra.letspartylogin.core.model.entity.User;
import it.alecsferra.letspartylogin.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public SimpleResult register(@RequestBody @Validated SignUpUser userDto, BindingResult bindingResult) {
        SimpleResult result = new SimpleResult();
        if (bindingResult.hasErrors()){
            return result;
        }
        User user = modelMapper.map(userDto, User.class);
        boolean success = userService.saveUser(user);
        if (!success)
            result.setMessage("Username already exists.");
        result.setSuccess(success);
        return result;
    }

    @PostMapping("/login")
    public LoginResult login(@RequestBody @Validated LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            LoginResult result = new LoginResult();
            result.setUsername(loginUser.getUsername());
            return result;
        }
        return userService.generateToken(loginUser);
    }

    @RequestMapping("/my/me")
    public UserInfo me(){
        String username = Utils.getCurrentUsername();
        User user = userService.findByUsername(username);
        return modelMapper.map(user, UserInfo.class);
    }
}
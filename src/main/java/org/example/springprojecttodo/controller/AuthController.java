package org.example.springprojecttodo.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.springprojecttodo.bean.LogInRequestBean;
import org.example.springprojecttodo.bean.SignUpRequestBean;
import org.example.springprojecttodo.service.ClientServiceJpa;
import org.example.springprojecttodo.shared.MyExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientServiceJpa clientService;

    @Autowired
    public AuthController(final ClientServiceJpa clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/sign-up")
    public UUID signUp(
            @Valid @RequestBody final SignUpRequestBean signUpRequestBean,
            final BindingResult bindingResult
    ) {
        MyExceptionUtils.checkValidationResult(bindingResult);
        return clientService.createUser(signUpRequestBean);
    }

    @PostMapping("/log-in")
    public boolean logIn(
            @Valid @RequestBody final LogInRequestBean logInRequestBean,
            final BindingResult bindingResult,
            final HttpServletResponse response
    ) {
        MyExceptionUtils.checkValidationResult(bindingResult);
        if (clientService.checkLoginAccess(logInRequestBean)) {
            response.addCookie(clientService.createCookie(logInRequestBean.email()));
            return true;
        }

        return false;
    }

    @PostMapping("/log-out")
    public boolean logOut(final HttpServletResponse response) {
        response.addCookie(clientService.clearCookie());
        // TODO 25.06.2024
        throw new UnsupportedOperationException("Not implemented yet");
    }

}

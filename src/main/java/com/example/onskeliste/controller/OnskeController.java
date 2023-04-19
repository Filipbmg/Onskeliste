package com.example.onskeliste.controller;

import com.example.onskeliste.model.User;
import com.example.onskeliste.repository.OnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


import jakarta.servlet.http.HttpSession;

@Controller
public class OnskeController {
    private OnskeRepository user;
    public OnskeController(OnskeRepository user){
        this.user = user;
    }

    @GetMapping("/")
    public String front() {
        return "login";}

    @PostMapping("/login")
    public String login(WebRequest request, HttpSession session) {
        User userLogin = new User();
        userLogin.setUsername(request.getParameter("username"));
        userLogin.setPassword(request.getParameter("password"));
        if (user.verifyLoginInfo(userLogin)) {
            session.setAttribute("username", userLogin.getUsername());
            return "testsuccess";
        } else {
            return "login";
        }
    }

    @GetMapping("/nybruger")
    public String nybruger(){
        return "nybruger";
    }

    @PostMapping("/signup")
    public String createNewUser(WebRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User newUser = new User();

        if (username == null || password == null) {
            System.out.println("For kort brugernavn eller kodeord");
            return"fejlioprettelse";
        } else if (username.length() < 3 || password.length() < 3){
            System.out.println("For kort brugernavn eller kodeord");
            return"fejlioprettelse";
        }else {
            newUser.setUsername(username);
            newUser.setPassword(password);
            if (!user.checkIfDup(newUser)) {
                System.out.println("brugernavnet er taget");
                return"fejlioprettelse";
            } else {
                user.addUser(newUser);
                return"login";
            }
        }
    }
}

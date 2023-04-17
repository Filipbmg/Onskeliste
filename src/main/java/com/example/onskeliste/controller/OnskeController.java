package com.example.onskeliste.controller;

import com.example.onskeliste.model.User;
import com.example.onskeliste.repository.OnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OnskeController {
    private OnskeRepository user;
    public OnskeController(OnskeRepository user){
        this.user = user;
    }


    @GetMapping("/")
    public String front(){
        return "front";
    }

    @GetMapping("/nybruger")
    public String nybruger(){
        return "nybruger";
    }

    @PostMapping("/nybruger")
    public String createNewUser(WebRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User newUser = new User();

        if (username == null || password == null) {
            System.out.println("For kort brugernavn eller kodeord");
            return"nybruger";
        } else if (username.length() < 1 || password.length() < 1){
            System.out.println("For kort brugernavn eller kodeord");
            return"nybruger";
        }else {
            newUser.setUsername(username);
            newUser.setPassword(password);
            user.addUser(newUser);
            return"front";
        }

    }
}

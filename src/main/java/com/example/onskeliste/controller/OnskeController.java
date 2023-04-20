package com.example.onskeliste.controller;

import com.example.onskeliste.model.*;
import com.example.onskeliste.repository.OnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
@Controller
public class OnskeController {
    private OnskeRepository user;
    public OnskeController(OnskeRepository user){
        this.user = user;
    }

    @GetMapping("/")
    public String front() {
        return "login";}

    @GetMapping("/login")
        public String login(){
        return "login";
    }


    @PostMapping("/login")
    public String login(WebRequest request, HttpSession session) {
        User userLogin = new User();
        userLogin.setUsername(request.getParameter("username"));
        userLogin.setPassword(request.getParameter("password"));
        if (user.verifyLoginInfo(userLogin)) {
            session.setAttribute("username", userLogin.getUsername());
            session.setAttribute("wishlist", user.getWishlist(userLogin));
            return "redirect:/onskeliste";
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
                return"redirect:/";
            }
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return"redirect:/";
    }

    @GetMapping("/onskeliste")
    public String onskeliste(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return"onskeliste";
        }
        return "redirect:/login";
    }

    @PostMapping("/onskeliste/fjern")
    public String removeWish(@RequestParam("byeLink") String byeLink, HttpSession session) {
        ArrayList<String> wishlist = (ArrayList<String>) session.getAttribute("wishlist");
        wishlist.remove(byeLink);
        session.setAttribute("wishlist", wishlist);
        user.removeWishFromDB(byeLink, session);
        return"redirect:/onskeliste";
    }

    @PostMapping("/onskeliste/tilfoj")
    public String addWish(WebRequest request, HttpSession session) {
        if (request != null) {
            if (request.getParameter("addLink").length() != 0) {
                user.addWishtoDB(request.getParameter("addLink"), session);
                ArrayList<String> wishlist = (ArrayList<String>) session.getAttribute("wishlist");
                wishlist.add(request.getParameter("addLink"));
                session.setAttribute("wishlist", wishlist);
            }
        }
        return"redirect:/onskeliste";

    }

    @GetMapping("/visit/{username}")
    public String shareList(@PathVariable("username") String username, Model userToVisit) {
        User visit = new User();
        visit.setUsername(username);
        if (!user.checkIfDup(visit)) {
            userToVisit.addAttribute("wishlist", user.getWishlist(visit));
            userToVisit.addAttribute("username", username);
            return "del";
        }
        return "redirect:/nybruger";

    }

}

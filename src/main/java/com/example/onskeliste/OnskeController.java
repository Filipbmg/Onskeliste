package com.example.onskeliste;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OnskeController {
    @GetMapping("/")
    public String front(){
        return "front";
    }
}

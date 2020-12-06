package com.depromeet.team5.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index.html")
    public String get() {
        return "index";
    }
}

package in.sp.itransition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ThemeController {

    @PostMapping("/setTheme")
    public String setTheme(@RequestParam("theme") String theme, HttpServletRequest request, HttpServletResponse response) {
        Cookie themeCookie = new Cookie("theme", theme);
        themeCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        themeCookie.setPath("/");
        response.addCookie(themeCookie);
        return "redirect:" + request.getHeader("Referer");
    }
}


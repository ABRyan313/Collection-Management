package in.sp.itransition.controller;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LocaleController {

	@GetMapping("/setLanguage")
    public String setLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        // Use Locale.forLanguageTag to avoid the deprecated constructor
        Locale locale = Locale.forLanguageTag(lang);
        LocaleContextHolder.setLocale(locale);

        // Also set the locale in the session
        HttpSession session = request.getSession();
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        // Redirect to the previous page or home page
        return "redirect:" + request.getHeader("Referer");
    }
}

package in.sp.itransition.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Define a LocaleResolver to store and retrieve the locale from a cookie
    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));  // Default to English
        localeResolver.setCookieMaxAge(60 * 60 * 24 * 30); // Cookie lifespan: 30 days
        localeResolver.setCookiePath("/");  // Set cookie path to root
        return localeResolver;
    }

    // Define a LocaleChangeInterceptor to intercept and manage locale changes
    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");  // Parameter name in the request to switch languages
        return localeChangeInterceptor;
    }

    // Register the LocaleChangeInterceptor in the application's interceptor registry
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    // Define the MessageSource bean to resolve messages from the resource bundle files
    @Bean
     ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");  // Base name for message bundle files (messages.properties)
        messageSource.setDefaultEncoding("UTF-8");  // Ensure proper encoding for non-ASCII characters
        return messageSource;
    }
}

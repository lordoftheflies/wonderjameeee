package hu.cherubits.wonderjam;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
    SwaggerConfiguration.class,
    AuthConfig.class,
    MailConfig.class,
    CmsConfiguration.class,
    FcmConfiguration.class,
    MailingServiceConfig.class,
    NetworkServiceConfiguration.class,
    SecurityConfiguration.class
})
@RestController
@RequestMapping
//@EnableWebMvc
public class Application extends SpringBootServletInitializer {

    @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_FORM_URLENCODED_VALUE
    },
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public Principal user(Principal user) {
        return user;
    }

//    @CrossOrigin(origins = "*", allowedHeaders = {"Access-Control-Allow-Origin"}, exposedHeaders = {"Access-Control-Allow-Origin"})
    @ResponseBody
    @RequestMapping(path = "/token", method = RequestMethod.GET,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", (session.getId() == null) ? "" : session.getId());
    }
    
    @ResponseBody
    @RequestMapping(path = "/user", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity user(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(auth.getPrincipal(), HttpStatus.OK);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

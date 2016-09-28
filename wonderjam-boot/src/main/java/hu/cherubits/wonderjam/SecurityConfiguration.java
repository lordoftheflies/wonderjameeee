/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.service.JpaUserDetailsService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = DatabaseConfiguration.class)
@EnableWebSecurity(debug = false)
@EnableRedisHttpSession
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(SecurityConfiguration.class.getName());

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private TokenBasedRememberMeServices rememberMeServices;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

//    @Override
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(1).and().sessionFixation().migrateSession().and()
                .antMatcher("/**")
                .httpBasic()
                .authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                    String requestedBy = request.getHeader(X_REQUESTED_BY_HEADER);
                    LOG.log(Level.INFO, "X-Requested-By: {0}", requestedBy);
                    if (requestedBy == null || requestedBy.isEmpty()) {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
//                        httpResponse.addHeader(AUTHENTICATION_HEADER, "Basic realm");
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    } else {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
//                        httpResponse.addHeader(AUTHENTICATION_HEADER, "Application driven");
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    }
                }).and()
                .authorizeRequests().antMatchers(
                        INDEX_FILE,
                        BOWER_COMPONENTS_DIRECTORY,
                        POLYMER_WIDGET_DIRECTORY,
                        IMAGES_DIRECTORY,
                        MANIFEST_FILE,
                        LOGIN_URL,
                        LOGON_URL,
                        LOGON_PROCESSING_URL,
                        //                        TOKEN_URL,
                        BASE_URL,
                        SERVICE_WORKER,
                        WEBJARS,
                        SWAGGER_CONFIGURATION,
                        SWAGGER_RESOURCES,
                        SWAGGER_UI,
                        SWAGGER_API).permitAll()
                .anyRequest().authenticated().and()
                .logout().permitAll().logoutSuccessUrl(BASE_URL).logoutUrl(LOGOUT_URL).deleteCookies(REMEMBER_ME_TOKEN, XXSRFTOKEN2)
//                .and().formLogin().loginPage("http://localhost:8080/#/login-view").loginProcessingUrl("http://localhost:8080/backend/login").usernameParameter("userName").passwordParameter("password").defaultSuccessUrl("http://localhost:8080/")
                .and().rememberMe().key(REMEMBER_ME_KEY).rememberMeServices(rememberMeServices)
                //        .and()
                //                .rememberMeParameter(REMEMBER_ME_TOKEN).rememberMeServices(rememberMeServices).tokenValiditySeconds(3600)

                //                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                .and().csrf().disable();
//                .and().csrf().csrfTokenRepository(BackendCookieCsrfTokenRepository.withHttpOnlyFalse());
//                .and()
//                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(XXSRFTOKEN);
        return repository;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSION");
        serializer.setCookiePath("/");
//        serializer.setUseHttpOnlyCookie(false);
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

//    PersistentTokenRepository rememberMeTokenRepository() {
//        return new ;
//    }
//    @Bean
//    public JedisConnectionFactory connectionFactory() {
//        return new JedisConnectionFactory();
//    }
//
//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/")
                        .allowedOrigins("*")
                        .allowedMethods("POST, GET, OPTIONS, DELETE ,PUT")
                        .allowedHeaders(AUTHORIZATION_HEADER, CONTENT_TYPE_HEADER, X_REQUESTED_WITH_HEADER, ACCEPT_HEADER, ORIGIN_HEADER,
                                AC_REQUEST_METHOD_HEADER, AC_REQUEST_HEADERS_HEADER, AC_ALLOW_ORIGIN_HEADER, AC_ALLOW_CREDENTIALS_HEADER, XXSRFTOKEN, XXSRFTOKEN2)
                        .exposedHeaders(AUTHORIZATION_HEADER, CONTENT_TYPE_HEADER, X_REQUESTED_WITH_HEADER, ACCEPT_HEADER, ORIGIN_HEADER,
                                AC_REQUEST_METHOD_HEADER, AC_REQUEST_HEADERS_HEADER, AC_ALLOW_ORIGIN_HEADER, XXSRFTOKEN, XXSRFTOKEN2)
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

//     @Bean()
//    public AuthenticationProvider rememberMeAuthenticationProvider() {
//        return new RememberMeAuthenticationProvider("KEY");
//    }
    @Bean
    TokenBasedRememberMeServices rememberMeServices() {
        BackendTokenBasedRememberMeServices tokenBasedRememberMeServices = new BackendTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
        tokenBasedRememberMeServices.setCookieName(REMEMBER_ME_TOKEN);
        tokenBasedRememberMeServices.setParameter(REMEMBER_ME_TOKEN);
        tokenBasedRememberMeServices.setTokenValiditySeconds(3600);
        tokenBasedRememberMeServices.setAlwaysRemember(true);
//        tokenBasedRememberMeServices.set;
        return tokenBasedRememberMeServices;
    }

    private static final String REMEMBER_ME_KEY = "KEY";
    private static final String REMEMBER_ME_TOKEN = "REMEMBERME";

    private static final String WEBJARS = "/webjars/**";
    private static final String SWAGGER_UI = "/swagger-ui.html";
    private static final String SWAGGER_RESOURCES = "/swagger-resources";
    private static final String SWAGGER_CONFIGURATION = "/configuration/**";
    private static final String SWAGGER_API = "/v2/**";
    private static final String BASE_URL = "/";
    private static final String BACKEND_URL = "/backend";
    private static final String LOGON_PROCESSING_URL = "/authentication/signon";
    private static final String LOGON_URL = "/addressbook/*/profile";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private static final String TOKEN_URL = "/token";
    private static final String MANIFEST_FILE = "/manifest.json";
    private static final String INDEX_FILE = "/index.html";
    private static final String IMAGES_DIRECTORY = "/images/**";
    private static final String POLYMER_WIDGET_DIRECTORY = "/src/**";
    private static final String BOWER_COMPONENTS_DIRECTORY = "/bower_components/**";

    private static final String SERVICE_WORKER = "/service-worker.js";

    private static final String AC_REQUEST_HEADERS_HEADER = "Access-Control-Request-Headers";
    private static final String AC_REQUEST_METHOD_HEADER = "Access-Control-Request-Method";
    private static final String AC_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private static final String AC_ALLOW_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";
    private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";
    private static final String X_REQUESTED_BY_HEADER = "X-Requested-By";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String ACCEPT_HEADER = "accept";
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String AUTHENTICATION_HEADER = "WWW-Authenticate";
    private static final String XXSRFTOKEN = "X-XSRF-TOKEN";
    private static final String XXSRFTOKEN2 = "XSRF-TOKEN";

}

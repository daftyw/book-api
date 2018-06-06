package rawin.springbootbooksample;

import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rawin.springbootbooksample.mongo.model.User;
import rawin.springbootbooksample.mongo.repo.UserRepository;
import rawin.springbootbooksample.security.MyTokenAuthenticationFilter;
import rawin.springbootbooksample.security.MyUserDetailsService;
import rawin.springbootbooksample.security.TokenAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { 
    
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/books", "/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .csrf().disable()                
                .cors().disable();                

        http.addFilterBefore(myFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider());
    }

    @Bean
    public TokenAuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(userRepository);
    }

    @Bean
    public MyTokenAuthenticationFilter myFilter() {
        return new MyTokenAuthenticationFilter();
    } 

    @Bean
    public AuthenticationEntryPoint unAuthenEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder(){
        
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return Objects.equal(User.encryptPassword(rawPassword.toString()), encodedPassword);
            }
        
            @Override
            public String encode(CharSequence rawPassword) {
                return User.encryptPassword(rawPassword.toString());
            }
        };
    }
    
}
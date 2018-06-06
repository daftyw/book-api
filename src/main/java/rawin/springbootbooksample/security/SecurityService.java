package rawin.springbootbooksample.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rawin.springbootbooksample.mongo.model.User;
import rawin.springbootbooksample.mongo.repo.UserRepository;

@Service
public class SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);
    
    private UserRepository userRepository;
    
    /**
     * @param userRepository the userRepository to set
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Find active user
     * 
     * @return Optional<User> - may be null if not logged in
     */
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if( auth == null ) {
            throw new IllegalStateException("cannot find authentication in session");
        }

        Optional<String> token = (Optional<String>) auth.getPrincipal();

        if(!token.isPresent()) {
            throw new IllegalStateException("cannot find token in session");
        }

        User user = userRepository.findByLoginToken(token.get());
        
        if(user == null) {
            throw new IllegalStateException("cannot find user from session");
        }
        
        return user;
    } 

}
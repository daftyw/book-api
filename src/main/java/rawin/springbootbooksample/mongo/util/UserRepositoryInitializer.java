package rawin.springbootbooksample.mongo.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import rawin.springbootbooksample.mongo.model.User;
import rawin.springbootbooksample.mongo.repo.UserRepository;

@Component
public class UserRepositoryInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryInitializer.class);

    private UserRepository userRepository;
    
    /**
     * @param userRepository the userRepository to set
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
        assert userRepository != null;
        
        logger.info("check if USER data is there");
        User user = userRepository.findByUsername("rawin");
        
        if(user != null) {
            return;
        }

        logger.info("inserting default user...");
        
        User toBeSaveUser = User.createWithEncryptedPassword("rawin", "password");
        toBeSaveUser.setDob(new Date());
        
        logger.debug("User data : {}", toBeSaveUser);
        userRepository.save(toBeSaveUser);
        
        logger.info("insert complete.");
	}

}
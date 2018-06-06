package rawin.springbootbooksample.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rawin.springbootbooksample.mongo.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username) ;
    User findByLoginToken(String token);
}
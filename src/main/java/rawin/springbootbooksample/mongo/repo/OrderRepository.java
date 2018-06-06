
package rawin.springbootbooksample.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rawin.springbootbooksample.mongo.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByBookIdAndUserId(String bookId, String userId);
}
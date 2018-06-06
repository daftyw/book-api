package rawin.springbootbooksample.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import rawin.springbootbooksample.mongo.model.Book;

public interface BookRepository extends MongoRepository<Book, Integer> {

    public Book findById(Integer bookId) ;
}
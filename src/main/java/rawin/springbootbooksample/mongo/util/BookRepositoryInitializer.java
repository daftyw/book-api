package rawin.springbootbooksample.mongo.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import rawin.springbootbooksample.mongo.model.Book;
import rawin.springbootbooksample.mongo.repo.BookRepository;
import rawin.springbootbooksample.remote.BookConnector;

@Component
public class BookRepositoryInitializer implements ApplicationRunner {
    private BookConnector bookConnector;
    private BookRepository bookRepo;

    /**
     * @param bookConnector the bookConnector to set
     */
    @Autowired
    public void setBookConnector(BookConnector bookConnector) {
        this.bookConnector = bookConnector;
    }

    /**
     * @param bookRepo the bookRepo to set
     */
    @Autowired
    public void setBookRepo(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long bookCount = bookRepo.count();

        if(bookCount == 0) {
            // initial
            List<Book> bookList = bookConnector.getBookList();
            if(bookList != null) {
                bookRepo.save(bookList);
            } 
        }
    }
}
package rawin.springbootbooksample.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import rawin.springbootbooksample.mongo.model.Book;
import rawin.springbootbooksample.mongo.repo.BookRepository;
import rawin.springbootbooksample.mongo.util.BookNameAndRecommendedComparator;
import rawin.springbootbooksample.remote.BookConnector;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
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

    @GetMapping("/books")
    public ResponseEntity<List<Book>> bookList() {
        List<Book> recommendedList = null;
        try {
            recommendedList = bookConnector.getRecommendBookList();
        } catch(Exception e) {
            log.error(" error get recommend list ", e);
        }

        List<Book> bookList = bookRepo.findAll();
        for( Book book : bookList ) {
            if(recommendedList.contains(book)) {
                book.setRecommended(Boolean.TRUE);
            } else {
                book.setRecommended(Boolean.FALSE);
            }
        }

        Collections.sort(bookList, new BookNameAndRecommendedComparator());

        return ResponseEntity.ok(bookList);
    }
}
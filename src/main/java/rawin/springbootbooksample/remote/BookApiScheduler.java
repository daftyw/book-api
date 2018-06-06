package rawin.springbootbooksample.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import rawin.springbootbooksample.mongo.model.Book;
import rawin.springbootbooksample.mongo.repo.BookRepository;

@Component
public class BookApiScheduler {

    private BookRepository bookRepo;
    private BookConnector bookConnector;

    /**
     * @param bookRepo the bookRepo to set
     */
    @Autowired
    public void setBookRepo(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    /**
     * @param bookConnector the bookConnector to set
     */
    @Autowired
    public void setBookConnector(BookConnector bookConnector) {
        this.bookConnector = bookConnector;
    }

    /**
     * Connect to book service every week on 1am Monday
     */
    @Scheduled(cron = "0 0 1 ? * 2")
    public void downloadBookAtSunday() {
        List<Book> remoteBookList = bookConnector.getBookList();
        if(remoteBookList != null) {
            bookRepo.deleteAll();
            bookRepo.save(remoteBookList);
        }
    }
}
package rawin.springbootbooksample.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import rawin.springbootbooksample.model.Login;
import rawin.springbootbooksample.model.LoginResponse;
import rawin.springbootbooksample.model.OrderRequest;
import rawin.springbootbooksample.model.OrderResponse;
import rawin.springbootbooksample.model.Register;
import rawin.springbootbooksample.mongo.model.Book;
import rawin.springbootbooksample.mongo.model.User;
import rawin.springbootbooksample.mongo.repo.BookRepository;
import rawin.springbootbooksample.mongo.repo.OrderRepository;
import rawin.springbootbooksample.mongo.repo.UserRepository;
import rawin.springbootbooksample.security.SecurityService;

@Controller
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepo;
    private BookRepository bookRepository;
    private OrderRepository orderRepository;

    private SecurityService secService;

    /**
     * @param userRepo the userRepo to set
     */
    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * @param bookRepository the bookRepository to set
     */
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * @param orderRepository the orderRepository to set
     */
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * @param secService the secService to set
     */
    @Autowired
    public void setSecService(SecurityService secService) {
        this.secService = secService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody Login login) {
        User user = userRepo.findByUsername(login.getUsername());

        LoginResponse loginResponse = new LoginResponse();

        if (user == null) {
            loginResponse.setResultCode("error_login");
            loginResponse.setResultDesc("Error in login parameter(s)");
            return ResponseEntity.ok(loginResponse);
        }

        if (!Objects.equal(User.encryptPassword(login.getPassword()), user.getEncPassword())) {
            loginResponse.setResultCode("error_login");
            loginResponse.setResultDesc("Error in login parameter(s)");
            return ResponseEntity.ok(loginResponse);
        }

        try {
            String generatedToken = UUID.randomUUID().toString();
            user.setLoginToken(generatedToken);
            userRepo.save(user);

            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            loginResponse.setResultCode("success");
            loginResponse.setToken(generatedToken);

            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            log.error("register-error", e);
            e.printStackTrace();
            return ResponseEntity.status(503).build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<User> getCurrencyUser() {
        User user = secService.getLoggedInUser();
        User userOut = new User();
        BeanUtils.copyProperties(user, userOut);
        userOut.setEncPassword(null);
        return ResponseEntity.ok(userOut);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        User user = secService.getLoggedInUser();
        user.setLoginToken(null);
        userRepo.save(user);
        
        // clear security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public ResponseEntity<Void> register(@RequestBody Register r) {
        User usr = userRepo.findByUsername(r.getUsername());
        if (usr != null) {
            return ResponseEntity.badRequest().build();
        }
        User u = User.createWithEncryptedPassword(r.getUsername(), r.getPassword());
        u.setDob(r.getDob());

        try {
            u = userRepo.save(u);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("register-error", e);
            e.printStackTrace();
            return ResponseEntity.status(503).build();
        }
    }

    @PostMapping("/users/orders")
    public ResponseEntity<OrderResponse> orders(@RequestBody OrderRequest orderReq) {
        log.debug("invoke orders()");
        log.debug("order request: {}", orderReq);
        // find book
        List<Book> bookInOrderList = Arrays.stream(orderReq.getOrders()).map(bookRepository::findById)
                .peek(book -> log.debug("receive book {}", book)).collect(Collectors.toList());

        if (bookInOrderList.stream().anyMatch(book -> book == null)) {
            return ResponseEntity.badRequest().build();
        }

        final User user = secService.getLoggedInUser();

        Double total = 0D;
        for (Book book : bookInOrderList) {
            if (orderRepository.findByBookIdAndUserId(String.valueOf(book.getId()), user.getId()) != null) {
                log.debug("bookId: {} is already ordered");
                continue;
            }

            if (user.getOrders() == null) {
                user.setOrders(new HashSet());
            }

            user.getOrders().add(book.getId());
            total += book.getPrice();
        }

        userRepo.save(user);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setPrice(total);

        return ResponseEntity.ok(orderResponse);
    }
}

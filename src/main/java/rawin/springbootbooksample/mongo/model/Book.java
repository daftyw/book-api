package rawin.springbootbooksample.mongo.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;

public class Book {
    // {"price":393.22,"book_name":"The Alice Network","author_name":"Kate
    // Quinn","id":11}

    @JsonProperty
    private Double price;

    @JsonProperty("book_name")
    private String name;

    @JsonProperty("author_name")
    private String author;

    @Id
    private Integer id;

    @JsonProperty("is_recommended")
    private Boolean recommended;

    public Book() {

    }

    private Book(BookBuilder builder) {
        id = builder.id;
        author = builder.author;
        name = builder.name;
        price = builder.price;
        recommended = builder.recommended;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the recommended
     */
    public Boolean getRecommended() {
        return recommended;
    }

    /**
     * @param recommended the recommended to set
     */
    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || (!(obj instanceof Book)))
            return false;
        Book that = (Book) obj;
        if (id == null && that.id == null) 
            return super.equals(obj);
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    public static class BookBuilder {
        private Double price;
        private String name;
        private String author;
        private Integer id;
        private Boolean recommended;

        public BookBuilder price(Double price) {
            this.price = price;
            return this;
        }
        public BookBuilder name(String name) {
            this.name = name;
            return this;
        }
        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }
        public BookBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        public BookBuilder recommended(Boolean recommended) {
            this.recommended = recommended;
            return this;
        }
        public Book build() {
            return new Book(this);
        }
    }
}
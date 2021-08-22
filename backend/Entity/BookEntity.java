package Entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "e-book", catalog = "")
public class BookEntity {
    private int id;
    private String name;
    private int price;
    private int stock;
    private int isbn;
    private String author;
    private String press;
    private String intro;
    private String img;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "stock")
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "isbn")
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "press")
    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    @Basic
    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return id == that.id &&
                price == that.price &&
                stock == that.stock &&
                isbn == that.isbn &&
                Objects.equals(name, that.name) &&
                Objects.equals(author, that.author) &&
                Objects.equals(press, that.press) &&
                Objects.equals(intro, that.intro) &&
                Objects.equals(img, that.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock, isbn, author, press, intro, img);
    }
}

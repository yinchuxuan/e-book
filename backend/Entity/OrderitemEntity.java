package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orderitem", schema = "e-book", catalog = "")
public class OrderitemEntity {
    private int orderId;
    private String bookSet;
    private String username;
    private String time;

    @Id
    @Column(name = "orderId")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "bookSet")
    public String getBookSet() {
        return bookSet;
    }

    public void setBookSet(String bookSet) {
        this.bookSet = bookSet;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderitemEntity that = (OrderitemEntity) o;
        return orderId == that.orderId &&
                Objects.equals(bookSet, that.bookSet) &&
                Objects.equals(username, that.username) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, bookSet, username, time);
    }
}

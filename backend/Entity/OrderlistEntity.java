package Entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orderlist", schema = "e-book", catalog = "")
public class OrderlistEntity {
    private int orderId;
    private String username;
    private String time;
    private int totalcash;

    @Id
    @Column(name = "orderId")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    @Basic
    @Column(name = "totalcash")
    public int getTotalcash() {
        return totalcash;
    }

    public void setTotalcash(int totalcash) {
        this.totalcash = totalcash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderlistEntity that = (OrderlistEntity) o;
        return orderId == that.orderId &&
                totalcash == that.totalcash &&
                Objects.equals(username, that.username) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, username, time, totalcash);
    }
}

package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "e-book", catalog = "")
public class UserEntity {
    private boolean isAd;
    private String password;
    private boolean isBanned;
    private String username;
    private String email;

    @Basic
    @Column(name = "isAd")
    public boolean getIsAd() {
        return isAd;
    }

    public void setIsAd(Boolean isAd) {
        this.isAd = isAd;
    }

    public void setIsAd(boolean isAd) { this.isAd = isAd; }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "isBanned")
    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return isAd == that.isAd &&
                isBanned == that.isBanned &&
                Objects.equals(password, that.password) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAd, password, isBanned, username, email);
    }
}

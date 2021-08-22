package DAO;

import Entity.UserEntity;

import java.util.ArrayList;

public interface UserDao {

    UserEntity findUser(String username);

    ArrayList<UserEntity> findAllUsers();

    Boolean SaveUser(UserEntity user);

    void updateUser(UserEntity user);

    Boolean DeleteUser(String username);
}

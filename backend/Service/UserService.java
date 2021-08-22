package Service;

import Entity.UserEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public interface UserService {

    ArrayList<UserEntity> findAllUsers();

    UserEntity findUser(String username);

    void AddUser(String username,String password,String email,Boolean isAd,Boolean isBanned);

    void DeleteUser(String username);

    void AmendUserPassword(String username,String password);

    void AmendUserEmail(String username,String email);

    void AmendUserIsBanned(String username);

    Boolean CheckEmail(String Email);

    Boolean CheckUsername(String Username);

}

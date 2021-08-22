package ServiceImpl;

import Entity.Databaseimpl;
import Service.UserService;
import Entity.UserEntity;
import DAO.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public boolean check(String message,String usage){
        try {
            Databaseimpl databaseimpl = new Databaseimpl();
            Iterator iter = databaseimpl.getIter("user");
            while (iter.hasNext()) {
                UserEntity user = (UserEntity) iter.next();
                if (usage.equals("username")) {
                    if (user.getUsername().equals(message)) {
                        return true;
                    }
                } else if (usage.equals("email")) {
                    if (user.getEmail().equals(message)) {
                        return true;
                    }
                }
            }
            return false;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<UserEntity> findAllUsers(){
        try {
            ArrayList<UserEntity> users = userDao.findAllUsers();
            return users;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserEntity findUser(String username){
        try {
            UserEntity user = userDao.findUser(username);
            return user;
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void AddUser(String username,String password,String email,Boolean isAd,Boolean isBanned){
        try{
            UserEntity User = new UserEntity();
            User.setUsername(username);
            User.setPassword(password);
            User.setEmail(email);
            User.setIsAd(isAd);
            User.setIsBanned(isBanned);
            userDao.SaveUser(User);
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteUser(String username){

    }

    @Override
    public void AmendUserPassword(String username,String password){
            try{
                UserEntity user = userDao.findUser(username);
                user.setPassword(password);
                userDao.updateUser(user);
            }catch(Throwable e){
                System.err.println(e);
                e.printStackTrace();
            }
        }

    @Override
    public void AmendUserEmail(String username,String email){
        try{
            UserEntity user = userDao.findUser(username);
            user.setEmail(email);
            userDao.updateUser(user);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void AmendUserIsBanned(String username){
        try{
            UserEntity user = userDao.findUser(username);
            user.setIsBanned(!user.getIsBanned());
            userDao.updateUser(user);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public Boolean CheckEmail(String Email){
        try{
            return check(Email,"email");
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean CheckUsername(String Username){
        try{
            return check(Username,"username");
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }
}

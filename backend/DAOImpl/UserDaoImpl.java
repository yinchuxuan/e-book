package DAOImpl;

import DAO.UserDao;
import Entity.UserEntity;
import org.hibernate.Transaction;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.ListIterator;
import Entity.Databaseimpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Override
    public UserEntity findUser(String username){
        try {
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("user");
            while (iter.hasNext()) {
                UserEntity user = (UserEntity) iter.next();
                if (user.getUsername().equals(username)) {
                    break;
                }
            }
            iter.previous();
            UserEntity user = (UserEntity) iter.next();
            return user;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<UserEntity> findAllUsers(){
        try {
            ArrayList<UserEntity> users = new ArrayList<>();
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("user");
            while (iter.hasNext()) {
                UserEntity user = (UserEntity) iter.next();
                users.add(user);
            }
            return users;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean SaveUser(UserEntity user){
        try{
            Databaseimpl databaseimpl = new Databaseimpl();
            Session session = databaseimpl.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return true;
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateUser(UserEntity user){
        try{
            Databaseimpl databaseimpl = new Databaseimpl();
            Session session = databaseimpl.getSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public Boolean DeleteUser(String username){
        return true;
    }
}

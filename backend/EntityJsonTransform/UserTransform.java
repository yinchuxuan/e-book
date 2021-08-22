package EntityJsonTransform;

import Entity.UserEntity;
import net.sf.json.JSONObject;

public class UserTransform {

    public JSONObject getJsonFromEntity(UserEntity user){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUsername());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("isBanned", user.getIsBanned());
            jsonObject.put("isAd",user.getIsAd());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getJsonManagerFromEntity(UserEntity user){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first", user.getUsername());
            jsonObject.put("second", user.getPassword());
            jsonObject.put("third", user.getEmail());
            jsonObject.put("fourth", user.getIsBanned());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public UserEntity getEntityFromJson(JSONObject user){
        try {
            UserEntity User = new UserEntity();
            User.setUsername((String)user.get("username"));
            User.setPassword((String)user.get("password"));
            User.setEmail((String)user.get("email"));
            User.setIsBanned((Boolean)user.get("isBanned"));
            User.setIsAd((Boolean)user.get("isAd"));
            return User;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}

package Controller;

import Entity.UserEntity;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import Service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin
@Controller
@RequestMapping("/LoginController")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public void Login(@RequestParam("username") String username,@RequestParam("password") String password, HttpSession httpSession,HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            JSONObject jsonObject = new JSONObject();
            if(!userService.CheckUsername(username)){
                jsonObject.put("error","username");
            }else {
                UserEntity user = userService.findUser(username);
                if (!user.getPassword().equals(password)) {
                    jsonObject.put("error","password");
                } else if (user.getIsBanned()) {
                    jsonObject.put("error","isBanned");
                } else {
                    if (user.getIsAd()) {
                        httpSession.setAttribute("username",username);
                        httpSession.setAttribute("isAd","isAd");
                        jsonObject.put("isAd",true);
                    } else {
                        httpSession.setAttribute("username",username);
                        httpSession.setAttribute("isAd","notAd");
                        jsonObject.put("isAd",false);
                    }
                    jsonObject.put("error","noError");
                }
            }
            response.getWriter().println(jsonObject);
            response.getWriter().flush();
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/redirectToRegister")
    public String redirectToRegister()
    {
        try{
            return "register";
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}

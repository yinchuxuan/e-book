package Controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/RegisterController")
public class RegisterController {

    @Autowired UserService userService;

    @RequestMapping("/register")
    public void Register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("repassword") String repassword, HttpServletResponse response){
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            JSONObject jsonObject = new JSONObject();
            if(userService.CheckUsername(username)){
                jsonObject.put("error","username");
            }else if (userService.CheckEmail(email)) {
                jsonObject.put("error","email");
            }else{
                userService.AddUser(username, password, email, false, false);
                jsonObject.put("error","noError");
            }
            response.getWriter().println(jsonObject);
            response.getWriter().flush();
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}

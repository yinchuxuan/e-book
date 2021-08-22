package Controller;

import Entity.BookEntity;
import EntityJsonTransform.BookTransform;
import Service.BookService;
import Service.PicService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;

@CrossOrigin
@Controller
@RequestMapping("/CartController")
public class CartController {

    @Autowired
    private BookService bookService;

    @Autowired
    private PicService picService;

    @RequestMapping("/showCart")
    public void showCart(HttpSession httpSession, HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            ArrayList<String> cart = (ArrayList<String>) httpSession.getAttribute("cart");
            int size = cart.size();
            String[] bookSet = cart.toArray(new String[size]);
            JSONArray jsonArray = new JSONArray();
            BookTransform bookTransform = new BookTransform();
            for(int i = 0;i < size;i++){
                BookEntity book = bookService.findBook(bookSet[i]);
                JSONObject jsonObject = bookTransform.getJsonFromEntity(book);
                JSONObject pic = picService.getBookPic(book.getName());
                jsonObject.put("img",pic);
                jsonArray.add(jsonObject);
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/joinCart")
    public String joinCart(HttpSession httpSession, @RequestParam("name") String name, HttpServletRequest request){
        try{
            if(httpSession.getAttribute("cart")!=null) {
                ArrayList<String> cart = (ArrayList<String>) httpSession.getAttribute("cart");
                cart.add(name);
                httpSession.setAttribute("cart", cart);
            }else{
                ArrayList<String> cart = new ArrayList<>();
                cart.add(name);
                httpSession.setAttribute("cart", cart);
            }
            request.setAttribute("action","joinCart");
            return "orderItem";
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/deleteCartItem")
    public String DeleteCartItem(HttpSession httpSession,@RequestParam("name") String name,HttpServletRequest request) {
        try {
            ArrayList<String> cart = (ArrayList<String>) httpSession.getAttribute("cart");
            Iterator<String> iter = cart.iterator();
            while(iter.hasNext()) {
                String item = iter.next();
                if(item.equals(name)){
                    iter.remove();
                }
            }
            if(cart.size()==0) {
                httpSession.setAttribute("cart",null);
            }else {
                httpSession.setAttribute("cart", cart);
            }
            request.setAttribute("action","delete");
            return "orderItem";
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/clearCart")
    public String clearCart(HttpSession httpSession,@RequestParam("TotalCash") int TotalCash){
        try{
            httpSession.setAttribute("TotalCash",TotalCash);
            return "redirect:/OrderController/addOrder";
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}

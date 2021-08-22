package EntityJsonTransform;

import Entity.OrderlistEntity;
import net.sf.json.JSONObject;

public class OrderTransform {

    public JSONObject getJsonFromEntity(OrderlistEntity order){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",order.getUsername());
            jsonObject.put("TotalCash",order.getTotalcash());
            jsonObject.put("orderId",order.getOrderId());
            jsonObject.put("time",order.getTime());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getJsonManagerFromEntity(OrderlistEntity order){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("second",order.getUsername());
            jsonObject.put("fourth",order.getTotalcash());
            jsonObject.put("first",order.getOrderId());
            jsonObject.put("third",order.getTime());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public OrderlistEntity getEntityFromJson(JSONObject order){
        try{
            OrderlistEntity Order = new OrderlistEntity();
            Order.setUsername((String)order.get("username"));
            Order.setOrderId((int)order.get("orderId"));
            Order.setTotalcash((int)order.get("TotalCash"));
            Order.setTime((String)order.get("time"));
            return Order;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}

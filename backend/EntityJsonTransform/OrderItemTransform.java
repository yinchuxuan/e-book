package EntityJsonTransform;

import Entity.OrderitemEntity;
import net.sf.json.JSONObject;

public class OrderItemTransform {

    public JSONObject getJsonFromEntity(OrderitemEntity orderItem){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",orderItem.getUsername());
            jsonObject.put("bookSet",orderItem.getBookSet());
            jsonObject.put("orderId",orderItem.getOrderId());
            jsonObject.put("time",orderItem.getTime());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public OrderitemEntity getEntityFromJson(JSONObject orderItem){
        try{
            OrderitemEntity OrderItem = new OrderitemEntity();
            OrderItem.setUsername((String)orderItem.get("username"));
            OrderItem.setOrderId((int)orderItem.get("orderId"));
            OrderItem.setBookSet((String)orderItem.get("bookSet"));
            OrderItem.setTime((String)orderItem.get("time"));
            return OrderItem;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}

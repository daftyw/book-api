package rawin.springbootbooksample.model;

import java.util.Arrays;
import java.util.Set;

public class OrderRequest {
    private Integer[] orders;

    /**
     * @return the orders
     */
    public Integer[] getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(Integer[] orders) {
        this.orders = orders;
    }

    @Override
    public String toString() { 
        String orderStr = "orders: null";
        if(orders != null) {
            orderStr = Arrays.asList(orders).stream()
                .map(String::valueOf)
                .reduce("orders:", (or, s) -> or.concat(",").concat(s));            
        }
        return "OrderRequest: " + orderStr;
    }
}

package com.sweetbake.util;

import com.sweetbake.dao.OrderDAO;
import com.sweetbake.model.Order;
import com.sweetbake.model.OrderOption;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestOrderDAO {

    public static void main(String[] args) {
        
try {
    OrderDAO dao = new OrderDAO();

    boolean ready = dao.updateOrderStatus(
            1, "READY", 1, null
    );

    boolean pickedUp = dao.updateOrderStatus(
            1, "PICKED_UP", 1, null
    );

    System.out.println("Ready: " + ready);
    System.out.println("Picked up: " + pickedUp);

} catch (Exception e) {
    e.printStackTrace();
}

    }
}

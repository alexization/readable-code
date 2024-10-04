package cleancode.assign;

import java.util.List;
import java.util.logging.Logger;

public class Order {

    private static final Logger log = Logger.getGlobal();

    private final Customer customer;
    private final List<Item> orderItems;

    public Order(Customer customer, List<Item> orderItems) {
        this.customer = customer;
        this.orderItems = orderItems;
    }

    public boolean validateOrder() {
        if (orderItems.isEmpty()) {
            log.info("주문 항목이 없습니다.");
            return false;
        }

        if (isNotAvailablePrice()) {
            log.info("올바르지 않은 총 가격입니다.");
            return false;
        }

        if (hasNotCustomerInfo()) {
            log.info("사용자 정보가 없습니다.");
            return false;
        }

        return true;
    }

    private boolean isNotAvailablePrice() {
        return getTotalPrice() <= 0;
    }

    private int getTotalPrice() {
        int totalPrice = 0;
        for (Item item : orderItems) totalPrice += item.getPrice();

        return totalPrice;
    }

    private boolean hasNotCustomerInfo() {
        return customer == null;
    }
}

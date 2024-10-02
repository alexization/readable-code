package cleancode.assign;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer("CustomerA");
        List<Item> items = new ArrayList<>();

        items.add(new Item("COFEE", 2000));
        items.add(new Item("BREAD", 4000));

        Order order = new Order(customer, items);

        if (order.validateOrder()) {
            System.out.println("정상적으로 주문 되었습니다");
        } else {
            System.out.println("주문이 실패했습니다.");
        }
    }
}

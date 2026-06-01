package com.mentorship.food_delivery_app.order.listener;

ndimport com.mentorship.food_delivery_app.order.event.OrderCanceledEvent;
import com.mentorship.food_delivery_app.order.event.OrderConfirmedEvent;
import com.mentorship.food_delivery_app.order.event.OrderPlacedEvent;
import com.mentorship.food_delivery_app.order.event.OrderStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderNotificationListener {

    @EventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        // send email + sms
        System.out.println("Order placed email sent");
    }

    @EventListener
    public void onOrderStatusUpdated(OrderStatusUpdatedEvent event) {
        System.out.println("Order status updated notification sent");
    }

    @EventListener
    public void OrderCanceledEvent(OrderCanceledEvent event) {
        System.out.println("Order cancelled  notification sent");
    }
    @EventListener
    public void OrderConfirmedEvent(OrderConfirmedEvent event) {
        System.out.println("Order confirmed  notification sent");
    }


}

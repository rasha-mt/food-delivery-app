package com.mentorship.food_delivery_app.order.service;

import com.mentorship.food_delivery_app.customer.model.Customer;
import com.mentorship.food_delivery_app.customer.service.CustomerService;
import com.mentorship.food_delivery_app.order.dto.OrderResponseDto;
import com.mentorship.food_delivery_app.order.dto.PagedResponse;
import com.mentorship.food_delivery_app.order.dto.requests.OrderItemRequest;
import com.mentorship.food_delivery_app.order.dto.requests.PlaceOrderRequest;
import com.mentorship.food_delivery_app.order.enums.OrderStatus;
import com.mentorship.food_delivery_app.order.event.OrderCanceledEvent;
import com.mentorship.food_delivery_app.order.event.OrderConfirmedEvent;
import com.mentorship.food_delivery_app.order.event.OrderPlacedEvent;
import com.mentorship.food_delivery_app.order.event.OrderStatusUpdatedEvent;
import com.mentorship.food_delivery_app.order.exceptions.InvalidOrderStatusException;
import com.mentorship.food_delivery_app.order.exceptions.OrderAlreadyProcessedException;
import com.mentorship.food_delivery_app.order.exceptions.OrderNotFoundException;
import com.mentorship.food_delivery_app.order.model.Order;
import com.mentorship.food_delivery_app.order.model.OrderItem;
import com.mentorship.food_delivery_app.order.repository.OrderRepository;
import com.mentorship.food_delivery_app.restaurant.model.MenuItem;
import com.mentorship.food_delivery_app.restaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomerService customerService;
    private final MenuItemService menuItemService;

    public Order placeOrder(PlaceOrderRequest request) {

        Customer customer = customerService.getCustomer();

        Order order = Order.builder()
                .customer(customer)
                .restaurantId(request.restaurantId())
                .status(OrderStatus.PENDING)
                .totalPrice(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.items()) {
            MenuItem menuItem =menuItemService.getMenuItem(itemReq.menuItemId());

            OrderItem item = OrderItem.builder()
                    .menuItemId(menuItem.getId())
                    .menuItemName(menuItem.getMenuItemName())
                    .quantity(itemReq.quantity())
                    .unitPrice(menuItem.getMenuItemPrice())
                    .subTotal(
                            menuItem.getMenuItemPrice()
                                    .multiply(BigDecimal.valueOf(itemReq.quantity()))
                    )
                    .build();

            order.addItem(item); //

            total = total.add(item.getSubTotal());
        }

        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);

        eventPublisher.publishEvent(new OrderPlacedEvent(saved));

        return saved;
    }

    public Order updateStatus(UUID orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);

        eventPublisher.publishEvent(new OrderStatusUpdatedEvent(updated));

        return updated;
    }

    public List<OrderResponseDto> getCustomerOrders(Customer customer) {

        List<Order> orders =
                orderRepository.findByCustomerId(customer.getId());
        return orders.stream()
                .map(OrderResponseDto::from)
                .toList();
    }

    public OrderResponseDto getOrderDetails(UUID orderId) {
        Order order= orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        return  OrderResponseDto.from(order);
    }


    public OrderResponseDto acceptOrder(UUID orderId) {

        Order order = getOrder(orderId);

        validatePendingOrder(order);

        order.setStatus(OrderStatus.CONFIRMED);
        eventPublisher.publishEvent(new OrderConfirmedEvent(order));

        return OrderResponseDto.from(order);
    }

    public OrderResponseDto cancelOrder(UUID orderId) {

        Order order = getOrder(orderId);

        validateCancelable(order);

        order.setStatus(OrderStatus.CANCELED);
        eventPublisher.publishEvent(new OrderCanceledEvent(order));

        return  OrderResponseDto.from(order);
    }

    private void validateCancelable(Order order) {

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOrderStatusException();
        }

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new OrderAlreadyProcessedException();
        }
    }

    private void validatePendingOrder(Order order) {

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderAlreadyProcessedException();
        }
    }

    private Order getOrder(UUID orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    public PagedResponse<OrderResponseDto> getMyOrdersHistory(
            int page,
            int size
    ) {

        Customer customer = customerService.getCustomer();
        Page<Order> orders =
                orderRepository.findByCustomerId(
                        customer.getId(),
                        PageRequest.of(page, size)
                );

        return new PagedResponse<>(
                orders.getContent()
                        .stream()
                        .map(OrderResponseDto::from)
                        .toList(),
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalElements(),
                orders.getTotalPages()
        );
    }


}
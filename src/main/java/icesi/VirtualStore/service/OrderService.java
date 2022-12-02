package icesi.VirtualStore.service;

import icesi.VirtualStore.constant.OrderStatus;
import icesi.VirtualStore.dto.OrderItemDTO;
import icesi.VirtualStore.model.Order;
import icesi.VirtualStore.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

        public Order getOrder(UUID orderId);
        public Order createOrder(Order order, UUID userId, List<OrderItemDTO> orderItems);

        public void updateOrder(UUID orderId, String status);

        public void deleteOrder(UUID orderId);

        public List<Order> getOrders();

        public List<Order> getUserOrders(UUID userId);

}

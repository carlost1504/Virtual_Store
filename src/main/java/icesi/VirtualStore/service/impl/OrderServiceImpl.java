package icesi.VirtualStore.service.impl;


import icesi.VirtualStore.constant.VirtualStoreErrorCode;
import icesi.VirtualStore.dto.OrderItemDTO;
import icesi.VirtualStore.error.exception.VirtualStoreError;
import icesi.VirtualStore.error.exception.VirtualStoreException;
import icesi.VirtualStore.model.*;
import icesi.VirtualStore.repository.ItemRepository;
import icesi.VirtualStore.repository.OrderItemRepository;
import icesi.VirtualStore.repository.OrderRepository;
import icesi.VirtualStore.repository.UserRepository;
import icesi.VirtualStore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;

    public final OrderItemRepository orderItemRepository;

    public final UserRepository userRepository;

    public final ItemRepository itemRepository;



    @Override
    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new VirtualStoreException(HttpStatus.NOT_FOUND, new VirtualStoreError(VirtualStoreErrorCode.CODE_O_01, VirtualStoreErrorCode.CODE_O_01.getMessage())));
    }

    @Override
    public Order createOrder(Order order, UUID userId, List<OrderItemDTO> items) {
        User user = userRepository.findById(userId).orElseThrow(()-> new VirtualStoreException(HttpStatus.NOT_FOUND, new VirtualStoreError(VirtualStoreErrorCode.CODE_O_01, VirtualStoreErrorCode.CODE_O_01.getMessage())));

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemDTO item : items){
            List<Item> list = itemRepository.findByAvailableAndItemType_ItemTypeId(true, item.getItemId());

            if(list.size() < item.getQuantity()){
                throw new VirtualStoreException(HttpStatus.NOT_FOUND, new VirtualStoreError(VirtualStoreErrorCode.CODE_I_02, VirtualStoreErrorCode.CODE_I_02.getMessage()));
            }

            list = list.stream().limit(item.getQuantity()).collect(Collectors.toList());

            OrderItem orderItem = OrderItem.builder().quantity(item.getQuantity()).items(list).build();

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);
        orderItems.forEach(it-> {
            it.getItems().forEach(item -> {
                item.setAvailable(false);
                itemRepository.updateAvailableByItemId(false, item.getItemId());
            });
        });

        double total = orderItems.stream().reduce(0.0, (a, b) ->{
            Optional<Item> item = b.getItems().stream().findFirst();
            return item.map(value -> a + b.getQuantity() * value.getItemType().getPrice()).orElse(a);
        } , Double::sum);

        order.setTotal(total);

        order.setOrderItemList(orderItems);

        return orderRepository.save(order);
    }

    @Override
    public void updateOrder(UUID orderId, String status) {

        int res = orderRepository.updateStatusByOrderId(status,orderId);

        if(res == 0){
            throw new VirtualStoreException(HttpStatus.NOT_FOUND, new VirtualStoreError(VirtualStoreErrorCode.CODE_O_01, VirtualStoreErrorCode.CODE_O_01.getMessage()));
        }
    }

    @Override
    public void deleteOrder(UUID orderId) {
         orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public List<Order> getUserOrders(UUID userId) {
        return orderRepository.findByUser_UserId(userId);
    }
}

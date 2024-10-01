package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.BasketDTO;
import com.wexad.BurgerHub.dto.OrderItemDTO;
import com.wexad.BurgerHub.model.OrderItem;
import com.wexad.BurgerHub.repository.OrderItemRepository;
import com.wexad.BurgerHub.security.SessionUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.wexad.BurgerHub.mapper.OrderMapper.ORDER_ITEM_MAPPER;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final SessionUser sessionUser;
    private final ProductService productService;

    public OrderItemService(OrderItemRepository orderItemRepository, SessionUser sessionUser, ProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.sessionUser = sessionUser;
        this.productService = productService;
    }

    public void minusProduct(Long productId) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findByUserIdAndProductId(sessionUser.id(), productId);

        if (optionalOrderItem.isPresent()) {
            OrderItem orderItem = optionalOrderItem.get();

            if (orderItem.getCount() == 1) {
                orderItemRepository.delete(orderItem);
            } else {
                orderItem.setCount(orderItem.getCount() - 1);
                orderItemRepository.save(orderItem);
            }
        }
    }


    public void addProduct(Long productId) {
        AtomicReference<Boolean> isExists = new AtomicReference<>(false);
        List<OrderItem> orderItemList = orderItemRepository.findAllByUserId(sessionUser.id());
        if (orderItemList.isEmpty()) {
            createOrderItem(productId);
        } else {
            orderItemList.forEach(orderItem -> {
                if (orderItem.getProduct().getId().equals(productId)) {
                    orderItem.setCount(orderItem.getCount() + 1);
                    orderItemRepository.save(orderItem);
                    isExists.set(true);
                }
            });
            if (!isExists.get()) {
                createOrderItem(productId);
            }
        }

    }

    public void createOrderItem(Long productId) {
        orderItemRepository.save(new OrderItem(productService.findById(productId), sessionUser.user(), 1L));
    }

    public List<OrderItemDTO> getOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAllByUserId(sessionUser.id());
        return ORDER_ITEM_MAPPER.toDtoList(orderItems);
    }

    public void deleteCardItems() {
        Long userId = sessionUser.id();
        orderItemRepository.deleteByUserId(userId);
    }

    public BasketDTO getBasket() {
        List<OrderItemDTO> orderItems = getOrderItems();
        AtomicReference<Long> count = new AtomicReference<>(0L);
        AtomicReference<Double> price = new AtomicReference<>(0.0);
        orderItems.forEach(orderItem -> {
            count.updateAndGet(v -> v + orderItem.count());
            price.updateAndGet(v -> v + orderItem.price() * orderItem.count());
        });
        return new BasketDTO(orderItems, count.get(), price.get());
    }
}

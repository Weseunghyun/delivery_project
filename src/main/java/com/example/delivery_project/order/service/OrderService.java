package com.example.delivery_project.order.service;

import com.example.delivery_project.menu.entity.Menu;
import com.example.delivery_project.menu.entity.MenuDelete;
import com.example.delivery_project.menu.repository.MenuRepository;
import com.example.delivery_project.order.dto.OrderRequestDto;
import com.example.delivery_project.order.dto.OrderResponseDto;
import com.example.delivery_project.order.dto.UpdateOrderRequestDto;
import com.example.delivery_project.order.entity.Order;
import com.example.delivery_project.order.entity.OrderStatus;
import com.example.delivery_project.order.exception.OrderErrorCode;
import com.example.delivery_project.order.exception.OrderException;
import com.example.delivery_project.order.repository.OrderRepository;
import com.example.delivery_project.store.entity.Store;
import com.example.delivery_project.user.entity.Authority;
import com.example.delivery_project.user.entity.User;
import com.example.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public Order findOderById(Long id) {

        return orderRepository.findByIdOrElseThrow(id);
    }

    @Transactional
    public OrderResponseDto orderFinished(OrderRequestDto orderRequestDto, Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);
        Authority findAuthority = findUser.getAuthority();

        if (!findAuthority.equals(Authority.USER)) {
            throw new OrderException(OrderErrorCode.ORDER_ONLY_USER);
        }

        Menu findMenu = menuRepository.findByIdOrElseThrow(orderRequestDto.getMenuId());
        Store findStore = findMenu.getStore();
        LocalTime nowTime = LocalTime.now();
        LocalTime openTime = findStore.getOpenTime();
        LocalTime closeTime = findStore.getCloseTime();

        if (!openTime.isBefore(nowTime) || !closeTime.isAfter(nowTime)) {
            throw new OrderException(OrderErrorCode.NOT_OPEN_OR_CLOSE_STORE);
        }

        if (findMenu.getMenuDelete().equals(MenuDelete.DEACTIVATE)) {
            throw new OrderException(OrderErrorCode.NOT_FOUND_MENU);
        }

        int findPrice = findMenu.getPrice();

        if (findStore.getMinimumOrderPrice() > findPrice) {
            throw new OrderException(OrderErrorCode.MINIMUM_PRICE_NOT_ENOUGH);
        }

        Order order = new Order(findUser, findMenu, findStore);
        order.setOrderStatus(OrderStatus.ORDER_FINISHED);

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    public OrderResponseDto updateOrder(Long orderId, UpdateOrderRequestDto orderRequestDto) {

        Order findOrder = findOderById(orderId);

        findOrder.updateOrderStatus(orderRequestDto);

        Order savedOrder = orderRepository.save(findOrder);
        return new OrderResponseDto(savedOrder);
    }
}

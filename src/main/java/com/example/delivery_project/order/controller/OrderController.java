package com.example.delivery_project.order.controller;

import com.example.delivery_project.common.constant.UserSession;
import com.example.delivery_project.order.dto.OrderRequestDto;
import com.example.delivery_project.order.dto.OrderResponseDto;
import com.example.delivery_project.order.dto.UpdateOrderRequestDto;
import com.example.delivery_project.order.exception.OrderException;
import com.example.delivery_project.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> userOrder(
        @RequestBody OrderRequestDto orderRequestDto,
        @SessionAttribute(UserSession.USER_ID) Long userId
    ) {

        OrderResponseDto orderFinished = orderService.orderFinished(orderRequestDto, userId);

        return new ResponseEntity<>(orderFinished, HttpStatus.CREATED);
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
        @PathVariable(name = "orderId") Long orderId,
        @RequestBody UpdateOrderRequestDto updateorderRequestDto
    ) {

        OrderResponseDto updateOrder = orderService.updateOrder(orderId, updateorderRequestDto);

        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

}

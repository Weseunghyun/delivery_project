package com.example.delivery_project.order.exception;


import lombok.Getter;

@Getter
public class OrderException  extends RuntimeException {

    private final OrderErrorCode errorCode;

    public OrderException(OrderErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

}

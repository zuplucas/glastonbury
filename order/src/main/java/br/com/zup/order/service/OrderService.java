package br.com.zup.order.service;

import br.com.zup.order.controller.request.CreateOrderRequest;
import br.com.zup.order.controller.response.OrderResponse;

import java.util.List;

public interface OrderService {

    String save(CreateOrderRequest request);

    List<OrderResponse> findAll();

}

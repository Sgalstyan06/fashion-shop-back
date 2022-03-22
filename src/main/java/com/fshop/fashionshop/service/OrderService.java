package com.fshop.fashionshop.service;

import com.fshop.fashionshop.model.Order;
import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.model.dto.requestDto.OrderUpdateReqDto;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order getById(long id);

    List<Order> getAll();

    Order update(long id, OrderUpdateReqDto order);

    void delete(long id);
}

package com.fshop.fashionshop.service.impl;

import com.fshop.fashionshop.model.Order;
import com.fshop.fashionshop.model.dto.requestDto.OrderUpdateReqDto;
import com.fshop.fashionshop.repository.OrderRepository;
import com.fshop.fashionshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order create(Order order) {
        return null;
    }


    @Override
    public Order getById(long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "product with id:" + id + "  not found in database")
                );
    }


    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }


    @Transactional
    @Override
    public Order update(long id, OrderUpdateReqDto reqDto) {
        Order fromDb = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "product with id:" + id + "  not found in database")
                );
        fromDb.setCount(reqDto.getCount());
        fromDb.setOrderStatus(reqDto.getOrderStatus());

        return fromDb;

    }

    @Override
    public void delete(long id) {

    }
}

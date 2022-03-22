package com.fshop.fashionshop.controller;

import com.fshop.fashionshop.model.Order;
import com.fshop.fashionshop.model.dto.requestDto.OrderUpdateReqDto;
import com.fshop.fashionshop.service.OrderService;
import com.fshop.fashionshop.validation.dto.OrderDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/{id}")
    Order getById(@PathVariable long id) {
        return orderService.getById(id);
    }

    @GetMapping()
    List<Order> getAll() {
        return orderService.getAll();
    }


    @PostMapping
    Order create(Order order) {
        return null;
    }


    @PutMapping("/{id}")
    Order update(@PathVariable long id, OrderUpdateReqDto reqDto) {
        if (!OrderDtoValidator.chekOrderUpdateDto(reqDto)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user data is invalid to update users order"
            );
        }
        return orderService.update(id, reqDto);
    }
}

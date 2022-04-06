package com.fshop.fashionshop.controller;

import com.fshop.fashionshop.model.Order;
import com.fshop.fashionshop.model.commons.enums.OrderStatus;
import com.fshop.fashionshop.model.dto.requestDto.OrderUpdateReqDto;
import com.fshop.fashionshop.model.dto.responseDto.ResponseDto;
import com.fshop.fashionshop.service.OrderService;
import com.fshop.fashionshop.validation.OrderValidator;
import com.fshop.fashionshop.validation.UserValidator;
import com.fshop.fashionshop.validation.dto.OrderDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user-order")
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader("user_id") String userId) {

        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }
        return ResponseEntity.ok(orderService.getAllById(userId));

    }

    @GetMapping("/order-status")
    ResponseEntity<List<Order>> getOrderByStatus(@RequestHeader("user_id") String userId, @RequestHeader("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }

        return ResponseEntity.ok(orderService.getOrderByStatus(userId, orderStatus));
    }

    @PostMapping
    ResponseEntity<ResponseDto> create(@RequestBody Order order) {
        if (!OrderValidator.validateOrder(order)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order Structure for accepting Order"
            );
        }
        Order created = orderService.create(order);
        ResponseDto responseDto = new ResponseDto("Order created.");
        responseDto.addInfo("OrderId", String.valueOf(created.getId()));
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/change-status/{order_id}/{status}")
    ResponseEntity<ResponseDto> changeStatus(@RequestHeader("user_id") String userId,
                                             @PathVariable("order_id") Long orderId,
                                             @PathVariable("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, plz SignUp at first"
            );
        }
        orderService.changeStatus(orderId, orderStatus);
        ResponseDto responseDto = new ResponseDto("Status is change.");
        responseDto.addInfo("OrderStatus", String.valueOf(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{order_id}")
    ResponseEntity<ResponseDto> delete(@PathVariable("order_id") Long id) {
        orderService.delete(id);
        ResponseDto responseDto = new ResponseDto("Order GBRSH.");
        responseDto.addInfo("OrderId", String.valueOf(id));
        return ResponseEntity.ok(responseDto);
    }
}

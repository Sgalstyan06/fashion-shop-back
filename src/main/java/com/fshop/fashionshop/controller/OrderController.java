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

    /***
     *
     * @return all orders
     */
    @GetMapping("get-all")
    ResponseEntity<List<Order>> getAll() {

        return ResponseEntity.ok(orderService.getAll());
    }

    /***
     *
     * @param userId is used to get all the orders made by current user
     * @return returns to front-end all the orders by current user,if process has been done authorized/ unauthorized
     */
    @GetMapping("/user-order")
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader String userId) {

        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, please SignUp at first"
            );
        }
        return ResponseEntity.ok(orderService.getAllById(userId));

    }

    /***
     *
     * @param userId is used to get all the orders made by current user
     * @param orderStatus is used to get orders with this mentioned status
     * @return returns an array of orders that matched provided user id and order status
     */
    @GetMapping("/order-status")
    ResponseEntity<List<Order>> getOrderByStatus(@RequestHeader String userId,
                                                 @RequestHeader("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, please SignUp at first"
            );
        }

        return ResponseEntity.ok(orderService.getOrderByStatus(userId, orderStatus));
    }

    /***
     *
     * @param order is made from the provided information by front-end which includes
     *            •product
     *            •user
     *            •additional order details
     * @param userId property is used to determine if the user has authorisation to make changes in database
     * @return responseDto to inform front-end that process has been done successfully/ failed
     */
    @PostMapping
    ResponseEntity<ResponseDto> create(@RequestBody Order order,
                                       @RequestHeader String userId) {
        if (!OrderValidator.validateOrder(order, userId)) {
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

    /***
     *
     * @param userId property is used to determine if the user has authorisation to make changes in database
     * @param orderId is to get the necessary order which status will be changed
     * @param orderStatus is the new status for the current order
     * @return responseDto to inform front-end that process has been done successfully/ failed
     *
     */
    @PutMapping("/change-status/{order_id}/{status}")
    ResponseEntity<ResponseDto> changeStatus(@RequestHeader("user_id") String userId,
                                             @PathVariable("order_id") Long orderId,
                                             @PathVariable("status") OrderStatus orderStatus){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is UNAUTHORIZED, please SignUp at first"
            );
        }
        orderService.changeStatus(orderId, orderStatus);
        ResponseDto responseDto = new ResponseDto("Status is change.");
        responseDto.addInfo("OrderStatus", String.valueOf(orderId));
        return ResponseEntity.ok(responseDto);
    }

    /***
     *
     * @param id is used to find the corresponding order that will be deleted
     * @param userId property is used to determine if the user has authorisation to make changes in database
     * @return responseDto to inform front-end that process has been done successfully/ failed
     */
    @DeleteMapping("/{idOrder}")
    ResponseEntity<ResponseDto> delete(@PathVariable("idOrder") Long id,
                                       @RequestHeader String userId) {
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is unauthorized, please sign in first:"
            );
        }
        orderService.delete(id);
        ResponseDto responseDto = new ResponseDto("Order deleted.");
        responseDto.addInfo("OrderId", String.valueOf(id));
        return ResponseEntity.ok(responseDto);
    }
}

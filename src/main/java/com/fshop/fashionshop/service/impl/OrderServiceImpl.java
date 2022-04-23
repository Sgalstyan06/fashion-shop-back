package com.fshop.fashionshop.service.impl;

import com.fshop.fashionshop.model.Order;
import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.model.commons.enums.OrderStatus;
import com.fshop.fashionshop.repository.OrderRepository;
import com.fshop.fashionshop.service.OrderService;
import com.fshop.fashionshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    /***
     *
     * @param order is the product that will be added in DB
     * @return new product which has added
     */
    @Override
    public Order create(Order order) {
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    /***
     *
     * @param id with the help of it will find the object from DB.
     * @return returns founded object or throws @ResponseStatusException(BAD_REQUEST).
     */
    @Override
    public List<Order> getAllById(String id) {

        List<Order> userOrders = orderRepository.getAllByUserId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Orders with user_id:" + id + "  not found in database")
                );
        Collections.reverse(userOrders);
        return userOrders;
    }

    /***
     *
     * @return all data from DB, if there is not any data will return empty List.
     */
    @Override
    public List<Order> getAll() {
        List<Order> allOrder = orderRepository.findAll();
        Collections.reverse(allOrder);
        return allOrder;
    }

    /***
     *
     * @param id find the order with provided id and deletes it
     */
    @Override
    public void delete(Long id) {

        orderRepository.deleteById(id);
    }

    /***
     *
     * @param userId property is used to determine if the user
     *               has authorisation to get orders by provided status from DB
     * @param orderStatus is the provided status
     * @return all the orders that satisfied the request's conditions
     */
    @Override
    public List<Order> getOrderByStatus(String userId, OrderStatus orderStatus) {
        return getAllById(userId).stream()
                .filter(item -> item.getOrderStatus() == orderStatus)
                .collect(Collectors.toList());
    }

    /***
     *
     * @param orderId finds the necessary order from DB by provided orderId
     * @param orderStatus change the status of the found order
     */
    @Override
    @Transactional
    public void changeStatus(Long orderId, OrderStatus orderStatus) {
        Order fromDb = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Order with id:" + orderId + "  not found in database"));
        Product product = productService.getById(fromDb.getProduct().getId());
        product.updateStock(fromDb.getOrderStatus(), orderStatus, fromDb.getCount());
        fromDb.setOrderStatus(orderStatus);
    }


}

package com.fshop.fashionshop.repository;

import com.fshop.fashionshop.model.Order;
import com.fshop.fashionshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

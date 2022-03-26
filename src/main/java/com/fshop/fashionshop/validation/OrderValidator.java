package com.fshop.fashionshop.validation;

import com.fshop.fashionshop.model.Order;

public final class OrderValidator {
    public static boolean validateOrder(Order order) {
        if (!UserValidator.checkUserAuthorized(order.getUser().getId()) &&
                ProductValidator.validateCreateProduct(order.getProduct()) &&
                !(order.getCount() > ValidationConstants.ORDER_PRODUCT_COUNT_MIN_VALUE &&
                        order.getCount() <= ValidationConstants.ORDER_PRODUCT_COUNT_MAX_VALUE
                ) ||
                order.getDate() == 0) {
            return false;
        }
        return true;
    }
}

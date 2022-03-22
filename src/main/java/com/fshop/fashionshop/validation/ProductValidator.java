package com.fshop.fashionshop.validation;

import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.validation.commons.DescriptionValidator;
import com.fshop.fashionshop.validation.commons.ImageValidator;
import com.fshop.fashionshop.validation.commons.StockValidator;

public final class ProductValidator {
    public static boolean validateUpdateProduct(Product product) {
        ImageValidator.checkDefaultImage(product);

        return product.getName().length() != 0 &&
                product.getPrice() >= 0 &&
                StockValidator.validateStock(product.getStock()) &&
                DescriptionValidator.validateDescription(product.getDescription());

    }

    public static boolean validateCreateProduct(Product product) {
        return validateUpdateProduct(product);
    }
}

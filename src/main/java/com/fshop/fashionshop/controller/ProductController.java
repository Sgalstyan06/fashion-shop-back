package com.fshop.fashionshop.controller;

import com.fshop.fashionshop.model.Product;
import com.fshop.fashionshop.model.dto.responseDto.ResponseDto;
import com.fshop.fashionshop.service.ImageService;
import com.fshop.fashionshop.service.ProductService;
import com.fshop.fashionshop.validation.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    /***
     *
     * @param id is used to find the corresponding product with current id
     * @return the necessary product by provided id
     */
    @GetMapping("/{id}")
    ResponseEntity<Product> getById(@PathVariable long id) {

        return ResponseEntity.ok(productService.getById(id));
    }

    /***
     *
     * @return  all products
     */
    @GetMapping()
    ResponseEntity<List<Product>> getAll() {

        return ResponseEntity.ok(productService.getAll());
    }

    /***
     *
     * @param product  is made from the provided information by front-end which includes
     *                  •name
     *                  •price
     *                  •additional product details
     * @param userId property is used to determine if the user has authorisation to make changes in database
     * @return responseDto to inform front-end that process has been done successfully/ failed
     */
    @PostMapping
    ResponseEntity<ResponseDto> create(@RequestBody Product product,
                                       @RequestHeader String userId){
        if(!ProductValidator.validateCreateProduct(product, userId)){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "user data is invalid to create product");
        }
        Product created = productService.create(product);
        ResponseDto responseDto = new ResponseDto("Product created.");
        responseDto.addInfo("productId", String.valueOf(created.getId()));
        return ResponseEntity.ok(responseDto);
    }

    /***
     *
     * @param id is to get the necessary product which will be updated
     * @param product is the new information to update product
     * @param userId property is used to determine if the user has authorisation to make changes in database
     * @return responseDto to inform front-end that process has been done successfully/ failed
     */
    @PutMapping("/{id}")
    ResponseEntity<ResponseDto> update(@PathVariable Long id,
                                       @RequestBody Product product,
                                       @RequestHeader String userId){
        if (!ProductValidator.validateUpdateProduct(product, userId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user data is invalid to update product with id:" + id
            );
        }
        Product updated = productService.update(product, id);
        ResponseDto responseDto = new ResponseDto("Product updated.");
        responseDto.addInfo("productId", String.valueOf(updated.getId()));
        return ResponseEntity.ok(responseDto);
    }

    /***
     *
     * @param id is to find the product which will be deleted
     * @param userId property is used to determine if the user has authorisation to make changes in database
     * @return responseDto to inform front-end that process has been done successfully/ failed
     */
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDto> delete(@PathVariable long id, @RequestHeader String userId){
        if (!ProductValidator.validateDeleteProduct(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is unauthorized, please sign in first:"
            );
        }
        imageService.delete(id);
        productService.delete(id);
        ResponseDto responseDto = new ResponseDto("Product deleted.");
        responseDto.addInfo("productId", String.valueOf(id));
        return ResponseEntity.ok(responseDto);
    }


}

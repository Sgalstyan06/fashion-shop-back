package com.fshop.fashionshop.model;

import com.fshop.fashionshop.model.commons.Description;
import com.fshop.fashionshop.model.commons.Image;
import com.fshop.fashionshop.model.commons.Stock;
import com.fshop.fashionshop.model.commons.enums.Currency;
import com.fshop.fashionshop.model.commons.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Float price;

    @OneToOne(cascade = CascadeType.ALL)
    private Description description;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(cascade = CascadeType.ALL)
    private Stock stock;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> img;

    public void updateStock(OrderStatus oldStatus, OrderStatus newStatus, int count) {
        if (!stock.getIsAvailable()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"products in stock is not available or the count is not enough!");
        }
        switch (oldStatus) {
            case PENDING:
            case UNPAID:
                if (newStatus == OrderStatus.PAID) {
                    Stock stock = this.getStock();
                    stock.setCount(stock.getCount() - count);
                }
                break;
            case PAID:
            case SENT:
            case DONE:
                break;
        }

        if (stock.getCount() == 0) {
            stock.setIsAvailable(false);
        }
    }

    @Override
    public String toString() {
        return "" +
                "" + id +
                "" + name +
                "" + price +
                "" + description +
                "" + currency +
                "" + stock +
                "" + img;
    }
}

package com.batch.example.BootBatch3X.config;

import com.batch.example.BootBatch3X.model.Product;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process( Product item) throws Exception {
        int disc_Percentage = Integer.parseInt(item.getDiscount());
        double price = Integer.parseInt(item.getPrice());
        double selling_Price = price - ((disc_Percentage / 100) * price);
        item.setDiscount_price(Double.toString(selling_Price));
        return item;
    }
}

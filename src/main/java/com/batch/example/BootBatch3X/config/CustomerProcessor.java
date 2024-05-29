package com.batch.example.BootBatch3X.config;

import com.batch.example.BootBatch3X.model.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        System.out.println("Hi----------------");
        return customer;
    }
}

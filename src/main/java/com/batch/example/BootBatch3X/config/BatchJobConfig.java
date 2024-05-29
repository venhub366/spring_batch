package com.batch.example.BootBatch3X.config;

import com.batch.example.BootBatch3X.constant.Queries;
import com.batch.example.BootBatch3X.model.Customer;
import com.batch.example.BootBatch3X.model.Product;
import jakarta.persistence.PreRemove;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchJobConfig {


    @Bean("csv-reader")
    public Job getJobBean(JobRepository jobRepository,
                          JobCompletionListenerImpl listener,
                          @Qualifier("step1")Step getStep) {
        return new JobBuilder("csv-reader", jobRepository)
                .listener(listener)
                .start(getStep)
                .build();
    }

    @Bean("jobStep")
    @Qualifier("step1")
    public Step getStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Product> itemReader,
            ItemProcessor<Product, Product> itemProcessor,
            ItemWriter<Product> itemWriter
    ) {
        return new StepBuilder("jobStep", jobRepository)
                .<Product, Product>chunk(5, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

    }

    @Bean
    public FlatFileItemReader<Product> fileItemReader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("reader")
                .resource(new ClassPathResource("data.csv"))
                .linesToSkip(1)
                .delimited()
                .names("productId", "title", "description", "price", "discount")
                .targetType(Product.class)
                .build();
    }

    @Bean
    public ItemProcessor<Product, Product> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<Product> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("INSERT INTO PRODUCTS(product_id,title,description,price,discount,discount_price) values(:product_id,:title," +
                        ":description,:price,:discount,:discount_price)")
                .dataSource(dataSource)
                .beanMapped().build();
    }

    //FOR CUSTOMER
    @Bean("customerJob")
    public Job customerJob(JobRepository custJobRepository,
                           @Qualifier("step2") Step getCustomerStep) {
        return new JobBuilder("customerJob", custJobRepository)
                .incrementer(new RunIdIncrementer())
                .start(getCustomerStep)
                .build();
    }

    @Bean("customerStep")
    @Qualifier("step2")
    public Step getCustomerStep(JobRepository custJobRepository,
                                PlatformTransactionManager custTransactionManager,
                                ItemReader<Customer> custReader,
                                ItemProcessor<Customer, Customer> custProcessor,
                                ItemWriter<Customer> custItemWriter
    ) {
        return new StepBuilder("customerStep", custJobRepository)
                .<Customer, Customer>chunk(10, custTransactionManager)
                .reader(custReader)
                .processor(custProcessor)
                .writer(custItemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> customerItemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customer_reader")
                .resource(new ClassPathResource("customers.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob")
                .targetType(Customer.class)
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> custItemProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    public ItemWriter<Customer> custItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .sql(Queries.customer_insert)
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }


}

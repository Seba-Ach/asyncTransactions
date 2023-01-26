package com.async.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class AsyncTransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncTransactionsApplication.class, args);
    }

}

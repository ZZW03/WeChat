package com.zzw.tcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TcpServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TcpServiceApplication.class, args);
    }
}

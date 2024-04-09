package com.zzw.tcp.attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Component
public class Rabbitmq {

    @Value("${spring.host}")
    private String host;

    @Value("${spring.port}")
    private Integer port;

    @Value("${spring.virtualHost}")
    private String virtualHost;

    @Value("${spring.userName}")
    private String userName;

    @Value("${spring.password}")
    private String password;
}

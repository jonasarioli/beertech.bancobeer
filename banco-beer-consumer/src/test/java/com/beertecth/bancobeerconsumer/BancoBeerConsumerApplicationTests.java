package com.beertecth.bancobeerconsumer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class BancoBeerConsumerApplicationTests {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

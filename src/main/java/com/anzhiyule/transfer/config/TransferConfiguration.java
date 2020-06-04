package com.anzhiyule.transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class TransferConfiguration {

    @Bean
    public void init() {
        try {
            System.out.println("-----------------------------");
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println("The local IP is [" + ip + "].");
            System.out.println("-----------------------------");
            System.out.println("-----------------------------");
            new RestTemplate().getForObject(new URI("http://file.anzhiyule.com/reg?ip=" + ip), Object.class);
            System.out.println("DNS register success. could visit by http://file.anzhiyule.com.");
        } catch (IOException e) {
            System.out.println("DNS register fail. please visit by local IP.");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------");
    }
}

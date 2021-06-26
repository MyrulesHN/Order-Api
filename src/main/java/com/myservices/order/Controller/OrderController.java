package com.myservices.order.Controller;



import com.netflix.discovery.shared.Application;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableDiscoveryClient
public class OrderController {

    public static final String PRODUCT_SERVICE_NAME="product-service";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${order.path}")
    private String applicationPath;

    @GetMapping("/test")
    public String show(){
        return "Welcome to the spring boot world";
    }


    @GetMapping("/callProduct")
    public String callProduct(){
        String serviceName = discoveryClient.getServices().stream().filter(s->s.equalsIgnoreCase(PRODUCT_SERVICE_NAME)).findFirst().get();
        String url = prepareApplicationUrl(serviceName.toUpperCase(),applicationPath);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);
                return responseEntity.toString();
    }

    private String prepareApplicationUrl(String applicationName,String applicationPath){
        return "http://"+applicationName+applicationPath;
    }
}

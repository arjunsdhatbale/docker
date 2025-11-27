package com.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/home")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    RestTemplate restTemplate;

//    @GetMapping("/home")
//    public String hello(){
//        System.out.println("Hello Arjun");
//        return "hello Arjun. subhsh Dhatbale....!";
//    }
    @GetMapping("/user-home")
    public String userHome() {
        logger.info("Request received for user home serivice from order home service .");
        //String orderResponse = restTemplate.getForObject("http://localhost:8081/home/order-home", String.class);
        return "Hello, I am User Service.";
    }

    @GetMapping("/order-home-response")
    public String orderHomeResponse() {
        logger.info("Request received for user home serivice from order home service .");
        String orderResponse = restTemplate.getForObject("http://localhost:8081/home/order-response", String.class);
        return "Response from order : " + orderResponse;

    }

}

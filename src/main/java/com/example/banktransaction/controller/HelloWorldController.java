package com.example.banktransaction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author qianxinxu
 * @Version 1.0
 * @Date 2025/4/11
 */
@RestController
public class HelloWorldController {

   @RequestMapping("/hello")
   public String index() {
          return "Hello World";
   }
}

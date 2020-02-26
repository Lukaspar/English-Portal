package pl.lukaspar.noname.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Main {

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World!";
    }

}

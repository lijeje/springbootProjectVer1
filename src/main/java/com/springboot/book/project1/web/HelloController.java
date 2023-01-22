package com.springboot.book.project1.web;

import com.springboot.book.project1.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 JSON으로 반혼하는 컨트롤러로 만들어줌. 예전의 @ResponseBody역할
public class HelloController {

    @GetMapping("/hello")// Http Method인 Get의 요청을 받을 수 있는 API생성 예전의 @RequestMapping
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}

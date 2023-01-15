package com.springboot.book.project1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 이설정으로 인해 스프링 Bean 읽기와 생성이 자동 설정 됨.
public class Application {//프로젝트의 메인클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

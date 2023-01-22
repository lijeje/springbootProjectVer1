package com.springboot.book.project1.web.dto;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);


        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);



//        assertj의 장점 : 1.자동완성이 확실하게 지원되고, 2. 추가적인 라이브러리가 필요하지 않음
//        assertThat : assertj(테스트 검증 라이브러리)의 검증메소드,메소드 체이닝 지원
//        isEqualto  :assertj의 비교동등메소드.

    }
}
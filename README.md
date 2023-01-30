# springbootProjectVer1
## Chaper 1. 인텔리제이로 스프링부트 시작하기

- 인텔리제이를 이용한 간단한 환경 구축과 깃허브와의 연결설명

## Chaper 2. 스프링부트에서 테스트코드를 작성하자



- 단위테스트?
    - 기능단위의 테스트 코드를 작성하는 것. 리팩토링에 포함되지 않음.
- TDD?
    - [https://repo.yona.io/doortts/blog/issue/1](https://repo.yona.io/doortts/blog/issue/1)

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 이설정으로 인해 스프링 Bean 읽기와 생성이 자동 설정 됨.
public class Application {//프로젝트의 메인클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
       
    }
}
```

- SpringApplication.run으로 인해 내장 WAS를 실행하여 스프링부트로 만들어진 Jar파일로 실행됨. 항상 서버에 톰캣을 설치할 필요가 없음.
- 스프링 부트에서는 내장WAS사용을 권장하는데 언제 어디서나 같은 환경에서 스프링 부트를 배포할 수 있기  때문.
- 내장 WAS도 성능에 큰 문제가 있지는 않음.

```java
@RestController //컨트롤러를 JSON으로 반혼하는 컨트롤러로 만들어줌. 예전의 @ResponseBody역할
public class HelloController {

    @GetMapping("/hello")// Http Method인 Get의 요청을 받을 수 있는 API생성 예전의 @RequestMapping
    public String hello(){
        return "hello";
    }
}
```

위에 작성한 코드들을 테스트코드로 검증

- 롬복 : 개발할 때 자주 사용하는 코드 Getter, Setter, 기본생성자, toString 등을 어노테이션으로 자동생성해주는 라이브러리

## Chaper 3. 스프링부트에서 JPA로 데이터베이스 다뤄보자

SQL Mapper (쿼리맵핑): iBatis, MyBatis 

ORM(Object Relational Mapping 객체맵핑) : JPA 

### 3.1 JPA 소개

- Spring Data JPA
    - 인터페이스로서 자바 표준 명세서
    - 사용하기 위해서는 구현체(Hivernnate, Eclipse, Link etc)가 필요
        - Spring에서 JPA를 사용할때는 구현체들을 추상화시킨 Spring Data JPA 라는 모듈을 이용하여 JPA기술을 다룸.
        - Spring Data JPA의 장점
            - 구현체 교체의 용이성 (Spring Data JPA 내부에서 구현체 매핑 지원)
            - 저장소 교체의 용이성
            - CRUD 쿼리를 직접 작성할 필요가 없음
            - 객체지향 프로그래밍의 손쉬운 적용 가능
        - JPA 의 단점
            - 높은 러닝커브(객체지향프로그래밍과 관계형 데이터베이스를 둘 다 이해해야 함)

### 3.2 프로젝트에 Spring Data Jpa 적용하기

- build.gradle에 의존성 등록

```java
dependencies {
    implementation('org.springframework.boot:spring-boot-starter-web')
    implementation('org.projectlombok:lombok')
    implementation('org.springframework.boot:spring-boot-starter-data-jpa')
    implementation('com.h2database:h2') 
    testImplementation('org.springframework.boot:spring-boot-starter-test')
}
```

> **spring-boot-starter-data-jpa**
> 
> - 스프링부트용 Spring Data Jpa 추상화 라이브러리
> - 스프링부트 버전에 맞춰 자동으로 JPA 관련 라이브러리들의 버전을 관리

> **h2**
> 
> - 인메모리 관계형 데이터베이스
> - 별도의 설치없이 프로젝트 의존성만으로 관리가능
> - 메모리에서 실행. 애플리케이션 재시작마다 초기화. 테스트용으로 많이 사용



게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제영역이라고 생각하면 됨. 

```java

@Getter //롬복 어노테이션
@NoArgsConstructor //롬복 어노테이션. 기본 생성자 자동 추가
@Entity //JPA의 어노테이션
public class Posts {//실제 DB와 매칭될 클래스(=Entity 클래스)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 빌더패턴 클래스 생성. 생성자 상단 선언 시 생성자에 포함된 필드만 빌더에 포함.
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
```

**@ Entity**

- 테이블과 링크될 클래스
- 클래스의 네이밍기본값 = 언더스코어네이밍(sales_manager)

**@ Id**

- 해당 테이블의 pk필드

**@ GeneratedValue**

- PK의 생성규칙
- 스프링부트 2.0에서는 GenerationType.IDENTITY를 추가해야만 auto_increment(자동증가) 됨
- [스프링부트 2.0버전과 1.5버전의 차이](https://jojoldu.tistory.com/295)

**@ Column**

- 테이블의 컬럼을 의미. 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨

- Entity class에서는 절대 setter 메소드를 만들지 않는다. 대신 해당 필드값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야만 한다.

```java
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
```

DB Layer 접근자(iBatis,MyBatis에서의 DAO 역할)

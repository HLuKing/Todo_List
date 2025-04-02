package hello.hello_spring;

import hello.hello_spring.repository.*;
import hello.hello_spring.sevice.MemberService;
import hello.hello_spring.sevice.ListService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

// 자바 코드로 직접 스프링 빈 등록하기
// 나중에 데이터베이스에 연결하기 위해 스프링 빈으로 등록하여 나중에 구현 클래스를 변경할때 용이하다.
// Autowired를 통한 DI는 스프링 빈으로 등록하지 않거나, 내가 직접 생성한 객체(new로 생성한것들)에서는 동작하지 않는다.
@Configuration
public class SpringConfig implements WebMvcConfigurer {

    private final MemberRepository memberRepository;
    private final ListRepository listRepository;  // 이게 JPA 구현체로 주입되게 되어야 함

    @Autowired
    public SpringConfig(MemberRepository memberRepository, ListRepository listRepository) {
        this.memberRepository = memberRepository;
        this.listRepository = listRepository;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로 허용
                .allowedOrigins("*") // 모든 출처 허용
                .allowedMethods("*") // GET, POST 등
                .allowedHeaders("*");
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    @Bean
    public ListService listService() {
        return new ListService(listRepository, memberRepository);
    }
}
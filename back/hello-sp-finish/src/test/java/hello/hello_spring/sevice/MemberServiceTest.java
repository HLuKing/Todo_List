package hello.hello_spring.sevice;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;// new로 리파지토리를 생성해서 내용물이 달라질 수 있다.
    MemoryMemberRepository memberRepository;  //밑에서 만든 메모리리포지토리를 넣고,

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository(); // 테스트가 실행될때마다 독립적으로 실행하면서 생성이 되는데, 각 테스트를 실행하기 전에 메모리멤머리포지토리를 만들고,
        memberService = new MemberService(memberRepository); // 넣은것을 맴버서비스에 넣어준다 그러면 같은 메모리멤버리포지토리가 사용된다
        // 멤버 서비스 입장에서 직접이용하지 않고 외부에서 넣어준다. 이것을 디팬더지이젝션 DI
    }

    @AfterEach                      // 하나의 테스트가 끝나면 실행됨
    public void afterEach() {       // 테스트가 끝나고 초기화해줌
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given 상황이 주어졌을 때
        Member member = new Member();
        member.setName("spring");

        //when 이것을 실행했을 때
        Long saveId = memberService.join(member);

        //then 결과가 이것이 나와야 함
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        //when

        //then
    }

    @Test
    void findOne() {
    }
}
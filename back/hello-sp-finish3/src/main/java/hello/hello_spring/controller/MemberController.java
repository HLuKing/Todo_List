package hello.hello_spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ui.Model;
import hello.hello_spring.domain.Member;
import hello.hello_spring.sevice.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/user")
public class MemberController {
    // @autowired Member... 하는 식인 필드주입식 이 있지만, 수정이 불가능해서 선호하지 않음
    // setter 주입방법 셋터인젝션이라 하며 누군가가 멤버컨트롤러로 호출했을 때 public로 열려있어야 함
    // public으로 노출이 되서 중간에 바뀌면 문제가 됨
    // DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다. 의존관계가 실행중에 동적으로 변하는 경우는 거의(아에)없으므로, 생성자 주입을 권장한다.
    private final MemberService memberService; // new로 생성하면 다른 여러 컨트롤러들이 가져다 사용할 수 있다. 그리고 MeberService는 기능이 별로 없기에 여러개의 인스턴스를 생성할 필요가 없다. 하나만 생성하고 공용으로 사용하면 된다
    // 이렇게 하는것을 컴포넌트 스캔과 자동 의존관계 설정이다.
    @Autowired // autowire로 원래 MemberService를 자동으로 넣어주지 않던것을 넣어주게 한다 (Service에서 Repository를 가져올때도 동일)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @Operation(summary = "새로운 계정 생성", description = "사용자가 새로운 계정을 생성합니다.")
    public Member create(@RequestBody MemberForm form) { //body에서 input값을 받아올때 사용
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAccountId(form.getAccountId());

        Member join = memberService.join(member);

        return join;
    }
}

package hello.hello_spring.controller;


import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.ListRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.util.JwtUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import hello.hello_spring.domain.TodoList;
import hello.hello_spring.sevice.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/members/todos")
public class ListController {
    // @autowired Member... 하는 식인 필드주입식 이 있지만, 수정이 불가능해서 선호하지 않음
    // setter 주입방법 셋터인젝션이라 하며 누군가가 멤버컨트롤러로 호출했을 때 public로 열려있어야 함
    // public으로 노출이 되서 중간에 바뀌면 문제가 됨
    // DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다. 의존관계가 실행중에 동적으로 변하는 경우는 거의(아에)없으므로, 생성자 주입을 권장한다.
    private final ListService listService; // new로 생성하면 다른 여러 컨트롤러들이 가져다 사용할 수 있다. 그리고 MeberService는 기능이 별로 없기에 여러개의 인스턴스를 생성할 필요가 없다. 하나만 생성하고 공용으로 사용하면 된다
    private final MemberRepository memberRepository;
    private final ListRepository listRepository;
    private final JwtUtil jwtUtil;

    // 이렇게 하는것을 컴포넌트 스캔과 자동 의존관계 설정이다.
    @Autowired // autowire로 원래 MemberService를 자동으로 넣어주지 않던것을 넣어주게 한다 (Service에서 Repository를 가져올때도 동일)
    public ListController(ListService listService,MemberRepository memberRepository, ListRepository listRepository, JwtUtil jwtUtil) {
        this.listService = listService;
        this.memberRepository = memberRepository;
        this.listRepository = listRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<TodoList> create(
            @RequestBody ListForm form,
            HttpServletRequest request) { //body에서 input값을 받아올때 사용
        // 1. 세션에서 memberId 추출
        Long memberId = (Long) request.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(401).body(null); // 로그인 안 된 경우
        }
        Member member = memberRepository.findById(memberId).orElseThrow();


        TodoList todolist = new TodoList();
        todolist.setTitle(form.getTitle());
        todolist.setContent(form.getContent());
        todolist.setMember(member);

        return ResponseEntity.ok(listRepository.save(todolist));
    }

    @GetMapping
    public ResponseEntity<List<TodoList>> getTodos(
            HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        System.out.println("→ [ListController] 필터에서 전달받은 memberId: " + memberId);
        if (memberId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(listService.getTodos(memberId));
    }

    @PutMapping
    public ResponseEntity<TodoList> updateTodo(
            @RequestBody ListForm form,
            HttpServletRequest request
    ) {
        Long memberId = (Long) request.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(401).build();
        }

        // title과 content로 기존 Todo 찾기
        TodoList todoList = listRepository.findByTitleAndMemberId(form.getTitle(), memberId)
                .orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없습니다."));

        // 내용 수정
        todoList.setTitle(form.getNewTitle());      // 새로운 필드
        todoList.setContent(form.getNewContent());  // 새로운 필드

        return ResponseEntity.ok(listRepository.save(todoList));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTodo(
            @RequestBody ListForm form,
            HttpServletRequest request
    ) {
        Long memberId = (Long) request.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(401).build();
        }

        TodoList todoList = listRepository.findByTitleAndMemberId(form.getTitle(), memberId)
                .orElseThrow(() -> new IllegalArgumentException("할 일을 찾을 수 없습니다."));

        listRepository.delete(todoList);
        return ResponseEntity.noContent().build();
    }


}

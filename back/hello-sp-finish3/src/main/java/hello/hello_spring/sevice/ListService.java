package hello.hello_spring.sevice;

import hello.hello_spring.domain.Member;
import hello.hello_spring.domain.TodoList;
import hello.hello_spring.repository.ListRepository;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ListService {

    private ListRepository listRepository;
    private MemberRepository memberRepository;

    @Autowired
    public ListService(ListRepository listRepository, MemberRepository memberRepository) {
        this.listRepository = listRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     *
     *
     */


    public TodoList createTodo(TodoList todolist, Long memberId) {

        // 같은 이름이 있는 중복 회원X
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 회원 ID입니다."));
        vaildateDuplicateTodoList(todolist, memberId); // 중복 회원 검증
        todolist.setMember(member);
        return listRepository.save(todolist);
    }

    private void vaildateDuplicateTodoList(TodoList todolist, Long memberId) {
        listRepository.findByTitleAndMemberId(todolist.getTitle(), memberId)
                .ifPresent( m -> {
                    throw new IllegalStateException("이미 존재하는 할일입니다.");
                });
    }
    /*
     * 전체회원 조회
     */

    public List<TodoList> findTodoLists() {
        return listRepository.findAll();
    }

    public Optional<TodoList> findByTitle(String title) {
        return listRepository.findByTitle(title);
    }

    public Optional<TodoList> findByContent(String content) {
        return listRepository.findByContent(content);
    }

    public List<TodoList> getTodos(Long memberId) {
        return listRepository.findByMemberId(memberId);
    }

}

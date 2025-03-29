package hello.hello_spring.repository;

import hello.hello_spring.domain.TodoList;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryListRepository implements ListRepository {

    private static final Map<Long, TodoList> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public TodoList save(TodoList todolist) {
        if (todolist.getId() == null) {
            todolist.setId(sequence.incrementAndGet());
        }
        store.put(todolist.getId(), todolist);
        return todolist;
    }

    @Override
    public Optional<TodoList> findByTitle(String title) {
        return store.values().stream()
                .filter(todolist -> todolist.getTitle().equals(title))
                .findAny();             // 멤버에서 getTitle이 파라미터로 넘어온 Name과 같으면 반환 전부 돌아도 없으면 Optional에 Null이 포함되서 반환
    }

    @Override
    public Optional<TodoList> findByContent(String content) {
        return store.values().stream()
                .filter(todolist -> todolist.getContent().equals(content))
                .findAny();             // 멤버에서 getTitle이 파라미터로 넘어온 Name과 같으면 반환 전부 돌아도 없으면 Optional에 Null이 포함되서 반환
    }

    @Override
    public Optional<TodoList> findByTitleAndMemberId(String title, Long memberId) {
        return store.values().stream()
                .filter(todo -> todo.getTitle().equals(title))
                .filter(todo -> todo.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public List<TodoList> findByMemberId(Long memberId) {
        return store.values().stream()
                .filter(todo -> todo.getMember() != null)  // NPE 방지
                .filter(todo -> todo.getMember().getId().equals(memberId))
                .toList();
    }

    @Override
    public Optional<TodoList> findByIdAndMemberId(Long id, Long memberId) {
        return Optional.ofNullable(store.get(id))
                .filter(todo -> todo.getMember() != null)
                .filter(todo -> todo.getMember().getId().equals(memberId));
    }

    @Override
    public List<TodoList> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
    public void delete(TodoList todoList) {
        if (todoList != null && todoList.getId() != null){
            store.remove(todoList.getId());
        }
    }
}

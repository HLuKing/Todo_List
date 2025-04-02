package hello.hello_spring.repository;

import hello.hello_spring.domain.TodoList;

import java.util.List;
import java.util.Optional;

public interface ListRepository {
    TodoList save(TodoList todolist);
    Optional<TodoList> findByTitle(String title);
    Optional<TodoList> findByContent(String content);
    List<TodoList> findByMemberId(Long memberId);
    Optional<TodoList> findByIdAndMemberId(Long id, Long memberId);
    Optional<TodoList> findByTitleAndMemberId(String title, Long memberId);
    List<TodoList> findAll();
    void delete(TodoList todoList);
}

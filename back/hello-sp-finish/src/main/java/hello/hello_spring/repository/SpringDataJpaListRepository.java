package hello.hello_spring.repository;

import hello.hello_spring.domain.TodoList;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface SpringDataJpaListRepository extends JpaRepository<TodoList, Long> , ListRepository {

    @Override
    Optional<TodoList> findByTitle(String title);

    @Override
    Optional<TodoList> findByContent(String content);

    @Override
    List<TodoList> findByMemberId(Long memberId);

    @Override
    Optional<TodoList> findByIdAndMemberId(Long id, Long memberId);
}

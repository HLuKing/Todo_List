package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();             // 멤버에서 getName이 파라미터로 넘어온 Name과 같으면 반환 전부 돌아도 없으면 Optional에 Null이 포함되서 반환
    }

    @Override
    public Optional<Member> findByAccountId(String accountId) {
        return store.values().stream()
                .filter(member -> member.getAccountId().equals(accountId))
                .findAny();             // 멤버에서 getName이 파라미터로 넘어온 Name과 같으면 반환 전부 돌아도 없으면 Optional에 Null이 포함되서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}

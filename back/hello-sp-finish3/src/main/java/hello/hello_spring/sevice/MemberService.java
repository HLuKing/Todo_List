package hello.hello_spring.sevice;

import hello.hello_spring.domain.Member;
import hello.hello_spring.controller.MemberForm;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     *
     *
     */


    public Member join(Member member) {
        // 같은 이름이 있는 중복 회원X
        vaildateDuplicateMember(member); // 중복 회원 검증
        Member save = memberRepository.save(member);
        return save;
    }

    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent( m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    /*
    * 전체회원 조회
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long MemberId) {
        return memberRepository.findById(MemberId);
    }

    public Optional<Member> findByAccountId(String accountId) {
        return memberRepository.findByAccountId(accountId);
    }

}

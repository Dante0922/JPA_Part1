package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행되어야 한다.
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // readOnly = false인 경우만 따로 명시해줘야 함
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        // 멀티 쓰레드 환경에서 동시에 같은 이름으로 회원 가입이 가능하다.
        // 따라서 DB에 name을 unique 제약조건을 걸어줘야 한다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    @Test
    @Rollback(false) // 롤백을 하지 않고 DB에 쿼리를 날린다.
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");
        // when
        Long savedId = memberService.join(member);

        // then
        //em.flush(); // DB에 쿼리를 날린다. 그러나 Transactional이므로 롤백한다.
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        // Junit5의 expected를 이용한 예외처리
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//       try {
//           memberService.join(member2);
//       } catch (IllegalStateException e) {
//           return;
//       }

        // then
        //Assertions.fail("예외가 발생해야 한다."); // 이 코드가 실행되면 에러
    }

}
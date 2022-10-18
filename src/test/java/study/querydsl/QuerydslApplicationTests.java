package study.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @PersistenceContext
    EntityManager em;

    @Test
    void contextLoads() {
        Member member = new Member();
        em.persist(member);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember = QMember.member;

        Member result = query
                .selectFrom(qMember)
                .fetchOne();

        assertThat(result).isEqualTo(member);
    }
}

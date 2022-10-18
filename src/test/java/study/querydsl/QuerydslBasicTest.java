package study.querydsl;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

@SpringBootTest
@Transactional
class QuerydslBasicTest {

    @PersistenceContext
    EntityManager entityManager;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(entityManager);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        entityManager.persist(teamA);
        entityManager.persist(teamB);

        Member member = new Member("member1", 10, teamA);
        Member member1 = new Member("member2", 20, teamA);
        Member member2 = new Member("member3", 30, teamB);
        Member member3 = new Member("member4", 40, teamB);

        entityManager.persist(member);
        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void startJPQL() {
        // member 1을 찾아라
        Member findMember = entityManager.createQuery(
                        "select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl() {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void searchAndParam() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(10)
                )
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void resultFetch() {
        List<Member> fetch = queryFactory.selectFrom(member).fetch();

        Member fetchOne = queryFactory.selectFrom(member).fetchOne();

        Member fetchFirst = queryFactory.selectFrom(member).fetchFirst();

        queryFactory.select(Wildcard.count).from(member).fetchOne();
    }
}

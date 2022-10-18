package study.querydsl.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void testEntity() {
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

        List<Member> members = entityManager.createQuery(
                "select m from Member m", Member.class
        ).getResultList();

        for (Member m : members) {
            System.out.println("m = " + m);
            System.out.println("m.getTeam() = " + m.getTeam());
        }
    }
}

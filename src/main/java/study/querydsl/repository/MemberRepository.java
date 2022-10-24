package study.querydsl.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import study.querydsl.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsername(String username);
}

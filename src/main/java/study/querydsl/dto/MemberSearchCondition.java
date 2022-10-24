package study.querydsl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchCondition {

    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}

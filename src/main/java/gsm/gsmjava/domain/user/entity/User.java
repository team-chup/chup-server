package gsm.gsmjava.domain.user.entity;

import gsm.gsmjava.domain.portfolio.entity.Portfolio;
import gsm.gsmjava.domain.resume.entity.Resume;
import gsm.gsmjava.domain.user.service.dto.req.SignUpReqDto;
import gsm.gsmjava.domain.user.service.dto.req.UpdateInfoReqDto;
import gsm.gsmjava.domain.user.type.Authority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString @Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    private String oauthEmail;

    @Column(unique = true, columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(columnDefinition = "CHAR(4)")
    private String studentNumber;

    @Column(columnDefinition = "CHAR(11)")
    private String phoneNumber;

    @OneToOne(mappedBy = "user")
    private Resume resume;

    @OneToOne(mappedBy = "user")
    private Portfolio portfolio;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority = Authority.TEMP;

    public static User of(String oauthEmail) {
        return User.builder()
                .oauthEmail(oauthEmail)
                .authority(Authority.TEMP)
                .build();
    }

    public void update(UpdateInfoReqDto reqDto) {
        this.email = reqDto.getEmail();
        this.name = reqDto.getName();
        this.studentNumber = reqDto.getStudentNumber();
        this.phoneNumber = reqDto.getPhoneNumber();
    }

    public void signup(SignUpReqDto reqDto) {
        this.email = reqDto.getEmail();
        this.name = reqDto.getName();
        this.studentNumber = reqDto.getStudentNumber();
        this.phoneNumber = reqDto.getPhoneNumber();
        this.authority = Authority.USER;
    }

}

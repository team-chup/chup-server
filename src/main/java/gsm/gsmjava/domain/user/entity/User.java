package gsm.gsmjava.domain.user.entity;

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

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(nullable = false, columnDefinition = "CHAR(4)")
    private String studentNumber;

    @Column(nullable = false, columnDefinition = "CHAR(11)")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority = Authority.TEMP;
}

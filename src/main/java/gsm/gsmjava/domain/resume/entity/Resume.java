package gsm.gsmjava.domain.resume.entity;

import gsm.gsmjava.domain.resume.type.ResumeType;
import gsm.gsmjava.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private ResumeType type;

    private String url;

    public void update(ResumeType type, String url, String name) {
        this.name = name;
        this.type = type;
        this.url = url;
    }
}

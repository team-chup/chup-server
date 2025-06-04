package gsm.gsmjava.domain.application.entity;

import gsm.gsmjava.domain.application.type.ApplicationStatus;
import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.resume.type.ResumeType;
import gsm.gsmjava.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    private String applicantName;

    private String applicantEmail;

    private String applicantPhoneNumber;

    private String applicantStudentNumber;

    @Enumerated(EnumType.STRING)
    private ResumeType applicantResumeType;

    private String applicantResumeUrl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicantStatus;

    private LocalDateTime createdAt;
}

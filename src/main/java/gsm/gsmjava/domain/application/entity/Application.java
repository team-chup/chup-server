package gsm.gsmjava.domain.application.entity;

import gsm.gsmjava.domain.application.type.ApplicationStatus;
import gsm.gsmjava.domain.applicationresult.entity.ApplicationResult;
import gsm.gsmjava.domain.position.entity.Position;
import gsm.gsmjava.domain.posting.entity.Posting;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private ApplicationResult applicationResult;

    private String applicantName;

    private String applicantEmail;

    private String applicantPhoneNumber;

    private String applicantStudentNumber;

    private String applicantResumeUrl;

    private String applicantResumeName;

    private String applicantPortfolioUrl;

    private String applicantPortfolioName;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicantStatus;

    private LocalDateTime createdAt;

    public void announced() {
        this.applicantStatus = ApplicationStatus.ANNOUNCED;
    }
}

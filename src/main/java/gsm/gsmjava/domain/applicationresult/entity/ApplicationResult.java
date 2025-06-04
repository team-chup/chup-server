package gsm.gsmjava.domain.applicationresult.entity;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.applicationresult.type.ApplicationResultStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_application_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class ApplicationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    private ApplicationResultStatus applicationResultStatus;

    @Column(name = "application_failed_reason", nullable = true)
    private String applicationFailedReason;

    private LocalDateTime createdAt;
}

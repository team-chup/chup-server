package gsm.gsmjava.domain.posting.entity;

import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import gsm.gsmjava.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_posting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String companyName;

    private String companyDescription;

    @Enumerated(EnumType.STRING)
    private CompanyLocation companyLocation;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private LocalDateTime postingStartAt;

    private LocalDateTime postingEndAt;

    private Integer applicationCount = 0;

    private LocalDateTime createdAt = LocalDateTime.now();
}

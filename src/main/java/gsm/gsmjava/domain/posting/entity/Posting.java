package gsm.gsmjava.domain.posting.entity;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import gsm.gsmjava.domain.posting.service.dto.req.UpdatePostingReqDto;
import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import gsm.gsmjava.domain.postingfile.entity.PostingFile;
import gsm.gsmjava.domain.postingposition.entity.PostingPosition;
import gsm.gsmjava.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostingPosition> postingPositions;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostingFile> postingFiles;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

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

    private LocalDateTime updatedAt = LocalDateTime.now();

    public void add() {
        this.applicationCount++;
    }

    public void update(UpdatePostingReqDto reqDto) {
        this.companyName = reqDto.getCompanyName();
        this.companyDescription = reqDto.getCompanyDescription();
        this.companyLocation = reqDto.getCompanyLocation();
        this.employmentType = reqDto.getEmploymentType();
        this.postingStartAt = reqDto.getStartAt();
        this.postingEndAt = reqDto.getEndAt();

        this.updatedAt = LocalDateTime.now();
    }
}

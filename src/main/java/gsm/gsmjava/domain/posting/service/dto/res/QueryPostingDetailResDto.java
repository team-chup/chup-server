package gsm.gsmjava.domain.posting.service.dto.res;

import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto;
import gsm.gsmjava.domain.posting.service.dto.req.CreatePostingReqDto.PostingFile;
import gsm.gsmjava.domain.posting.type.CompanyLocation;
import gsm.gsmjava.domain.posting.type.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static gsm.gsmjava.domain.posting.service.dto.res.QueryPostingResDto.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPostingDetailResDto {
    private Long id;
    private String companyName;
    private String companyDescription;
    private CompanyLocation companyLocation;
    private EmploymentType employmentType;
    private List<PositionDto> positions;
    private List<PostingFile> files;
    private Integer applicationCount;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean applied;
}

package gsm.gsmjava.domain.application.service;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ApplicationDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.PositionDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ResultDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ResumeDto;
import gsm.gsmjava.domain.user.entity.User;
import gsm.gsmjava.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMyApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public MyApplicationResDto queryMy() {
        User currentUser = userUtil.getCurrentUser();
        List<Application> applications = applicationRepository.findByUser(currentUser);

        List<ApplicationDto> applicationDtos = applications.stream().map(application -> ApplicationDto.builder()
                .postingId(application.getPosting().getId())
                .companyName(application.getPosting().getCompanyName())
                .companyLocation(application.getPosting().getCompanyLocation())
                .employmentType(application.getPosting().getEmploymentType())
                .resume(ResumeDto.builder()
                        .name(application.getApplicantResumeName())
                        .type(application.getApplicantResumeType())
                        .url(application.getApplicantResumeUrl())
                        .build())
                .position(PositionDto.builder()
                        .id(application.getPosition().getId())
                        .name(application.getPosition().getName())
                        .build())
                .status(application.getApplicantStatus())
                .result(ResultDto.builder()
                        .status(application.getApplicationResult().getApplicationResultStatus())
                        .failedReason(application.getApplicationResult().getApplicationFailedReason())
                        .announcedAt(application.getApplicationResult().getCreatedAt())
                        .build())
                .createdAt(application.getCreatedAt())
                .build()
        ).toList();

        return new MyApplicationResDto(applications.size(), applicationDtos);
    }

}

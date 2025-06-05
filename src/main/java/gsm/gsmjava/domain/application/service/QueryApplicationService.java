package gsm.gsmjava.domain.application.service;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.domain.application.service.dto.res.ApplicationResDto;
import gsm.gsmjava.domain.application.service.dto.res.ApplicationResDto.ApplicantDto;
import gsm.gsmjava.domain.application.service.dto.res.ApplicationResDto.ApplicationDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.PositionDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ResultDto;
import gsm.gsmjava.domain.application.service.dto.res.MyApplicationResDto.ResumeDto;
import gsm.gsmjava.domain.posting.entity.Posting;
import gsm.gsmjava.domain.posting.repository.PostingRepository;
import gsm.gsmjava.global.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryApplicationService {

    private final PostingRepository postingRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional(readOnly = true)
    public ApplicationResDto queryByPosting(Long postingId) {
        Posting posting = postingRepository.findById(postingId).orElseThrow(
                () -> new ExpectedException("해당 공고는 존재하지 않습니다. id = " + postingId, HttpStatus.NOT_FOUND)
        );
        List<Application> applications = applicationRepository.findByPostingFetchJoin(posting);
        List<ApplicationDto> applicationDtoes = applications.stream().map(application -> ApplicationDto.builder()
                .id(application.getId())
                .applicant(ApplicantDto.builder()
                        .name(application.getApplicantName())
                        .email(application.getApplicantEmail())
                        .phoneNumber(application.getApplicantPhoneNumber())
                        .studentNumber(application.getApplicantStudentNumber())
                        .build())
                .position(PositionDto.builder()
                        .id(application.getPosition().getId())
                        .name(application.getPosition().getName())
                        .build())
                .resume(ResumeDto.builder()
                        .name(application.getApplicantResumeName())
                        .url(application.getApplicantResumeUrl())
                        .type(application.getApplicantResumeType())
                        .build())
                .status(application.getApplicantStatus())
                .result(application.getApplicationResult() == null ? null : ResultDto.builder()
                        .status(application.getApplicationResult().getApplicationResultStatus())
                        .announcedAt(application.getApplicationResult().getCreatedAt())
                        .failedReason(application.getApplicationResult().getApplicationFailedReason())
                        .build())
                .createdAt(application.getCreatedAt())
                .build()
        ).toList();

        return new ApplicationResDto(applicationDtoes.size(), applicationDtoes);
    }

}

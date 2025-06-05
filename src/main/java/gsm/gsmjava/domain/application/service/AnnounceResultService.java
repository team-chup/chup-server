package gsm.gsmjava.domain.application.service;

import gsm.gsmjava.domain.application.entity.Application;
import gsm.gsmjava.domain.application.event.AnnouncedEvent;
import gsm.gsmjava.domain.application.repository.ApplicationRepository;
import gsm.gsmjava.domain.application.service.dto.req.AnnounceReqDto;
import gsm.gsmjava.domain.application.type.ApplicationStatus;
import gsm.gsmjava.domain.applicationresult.entity.ApplicationResult;
import gsm.gsmjava.domain.applicationresult.repository.ApplicationResultRepository;
import gsm.gsmjava.global.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnnounceResultService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationResultRepository applicationResultRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void announce(Long applicationId, AnnounceReqDto reqDto) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new ExpectedException("해당 지원서는 존재하지 않습니다. id = " + applicationId, HttpStatus.NOT_FOUND)
        );

        if (application.getApplicantStatus() == ApplicationStatus.ANNOUNCED) {
            throw new ExpectedException("이미 결과 발표된 지원서 입니다.", HttpStatus.CONFLICT);
        }

        application.announced();
        applicationRepository.save(application);

        ApplicationResult applicationResult = ApplicationResult.builder()
                .application(application)
                .applicationResultStatus(reqDto.getStatus())
                .applicationFailedReason(reqDto.getFailedReason())
                .createdAt(LocalDateTime.now())
                .build();
        applicationResultRepository.save(applicationResult);

        applicationEventPublisher.publishEvent(
                AnnouncedEvent.builder()
                        .name(application.getApplicantName())
                        .email(application.getApplicantEmail())
                        .position(application.getPosition().getName())
                        .companyName(application.getPosting().getCompanyName())
                        .status(reqDto.getStatus())
                        .failedReason(reqDto.getFailedReason())
                        .postingId(application.getPosting().getId())
                        .build()
        );
    }

}

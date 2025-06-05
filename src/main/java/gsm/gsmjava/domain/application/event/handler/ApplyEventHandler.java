package gsm.gsmjava.domain.application.event.handler;

import gsm.gsmjava.domain.application.event.ApplyEvent;
import gsm.gsmjava.domain.posting.event.CreatePostingEvent;
import gsm.gsmjava.global.mail.MailService;
import gsm.gsmjava.infra.discord.properties.DiscordEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplyEventHandler {

    private final MailService mailService;

    @Async("asyncTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ApplyEvent event) {
        log.info("지원 완료 이벤트 핸들러 실행 - 지원 완료 메일 전송: {}", event);

        mailService.sendApplyCompleteMail(
                event.getApplicantEmail(),
                event.getApplicantName(),
                event.getApplicantPhoneNumber(),
                event.getCompanyName(),
                event.getPositionName(),
                event.getApplicantDate()
        );
    }

}

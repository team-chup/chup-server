package gsm.gsmjava.domain.application.event.handler;

import gsm.gsmjava.domain.application.event.AnnouncedEvent;
import gsm.gsmjava.global.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnnouncedEventHandler {

    private final MailService mailService;

    @Async("asyncTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(AnnouncedEvent event) {
        log.info("결과 통보 이벤트 핸들러 실행 - 서류 전형 결과 메일 전송: {}", event);

        switch (event.getStatus()) {
            case FIRST -> mailService.sendFirstPassMail(event.getEmail(), event.getName(), event.getCompanyName(), event.getPosition());
            case FAILED -> mailService.sendFirstFailMail(event.getEmail(), event.getName(), event.getCompanyName(), event.getPosition(), event.getFailedReason());
        }
    }

}

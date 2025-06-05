package gsm.gsmjava.global.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendApplyCompleteMail(String to, String name, String phone, String company, String position, LocalDateTime dateTime) {
        String html = loadApplyCompleteTemplate()
                .replace("{{name}}", name)
                .replace("{{email}}", to)
                .replace("{{phone}}", phone)
                .replace("{{company}}", company)
                .replace("{{position}}", position)
                .replace("{{date}}", dateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh:mm", Locale.KOREA)));

        sendHtmlMail(to, "[" + company + "] " + name + "님, 지원 완료 안내", html);
    }

    private void sendHtmlMail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }

    private String loadApplyCompleteTemplate() {
        try (InputStream input = getClass().getResourceAsStream("/templates/email/apply-complete.html")) {
            if (input == null) throw new FileNotFoundException("템플릿 파일을 찾을 수 없습니다.");
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("템플릿 로딩 실패", e);
        }
    }

}

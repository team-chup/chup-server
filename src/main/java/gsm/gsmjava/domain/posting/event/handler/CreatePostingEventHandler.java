package gsm.gsmjava.domain.posting.event.handler;

import gsm.gsmjava.domain.posting.event.CreatePostingEvent;
import gsm.gsmjava.infra.discord.properties.DiscordEnvironment;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CreatePostingEventHandler {

    private final DiscordEnvironment discordEnv;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    @Async("asyncTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(CreatePostingEvent event) {
        String webhookUrl = discordEnv.webhookUrl();

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", "📢 새로운 채용 공고가 등록되었어요!");
        embed.put("description", String.format(
                "🏢 회사명: **%s**\n\n" +
                "📝 회사 설명: %s...\n\n" +
                "💼 고용 형태: **%s**\n\n" +
                "📍 근무 지역: **%s**\n\n" +
                "🧑‍💻 모집 포지션: **%s**\n\n" +
                "🗓️ 모집 기간: **%s ~ %s**\n\n" +
                "🔗 [채용 공고 바로가기](https://chup.today/posting/%s)",
                event.getCompanyName(),
                event.getCompanyDescription().substring(0, Math.min(100, event.getCompanyDescription().length())),
                event.getEmploymentType().getName(),
                event.getCompanyLocation().getName(),
                String.join(", ", event.getPositions()),
                event.getStartAt().format(formatter),
                event.getEndAt().format(formatter),
                event.getPostingId()
        ));
        embed.put("color", 1347327);

        Map<String, Object> payload = Map.of("embeds", List.of(embed));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(webhookUrl, request, String.class);
    }

}

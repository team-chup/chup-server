package gsm.gsmjava.infra.discord.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordEnvironment(
        String webhookUrl
) {
}

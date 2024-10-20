package pl.maropce.etutor.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Getter
public class AppConfig {

    @Value("${app.url}")
    private String applicationURL;

}

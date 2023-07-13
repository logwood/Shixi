package org.example.shixi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
@ConfigurationProperties(prefix = "thinking.auth")
public class WhiteListProperties {
    private List<String> whiteList;
}

package io.github.architers.webhook.model.dingding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author luyi
 */
@Data
public class DingDingText {
    private String content;

    public DingDingText() {

    }

    public DingDingText(String content) {
        this.content = content;
    }
}

package io.github.architers.context.valid;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum EnumTest   {
    test("1", "一");

    private String code;

    private String caption;

    EnumTest(String code, String caption) {
        this.code = code;
        this.caption = caption;
    }


}

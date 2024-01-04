package io.github.architers.test.valid.eums;

import lombok.Getter;

@Getter
public enum YesOrNo {
    yes((byte) 1, "是"),
    no((byte) 2, "否");
    private final byte code;

    private final String caption;

    YesOrNo(byte code, String caption) {
        this.code = code;
        this.caption = caption;
    }
}

package io.github.architers.context.lock;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SingleLockParam  extends BaseLockParam{

    private String key;
}

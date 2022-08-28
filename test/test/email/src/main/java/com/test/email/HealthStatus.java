package com.test.email;

/**
 * @author luyi
 */

public enum HealthStatus {

    /**
     * 健康
     */
    healthy(1),

    /**
     * 亚健康
     */
    half_healthy(2),

    /**
     * 濒临死亡
     */
    dying(3),
    /**
     * 死亡
     */
    dead(4);

    private final Integer status;

    HealthStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}

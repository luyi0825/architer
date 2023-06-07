package io.github.architers.cache.caffeine;

public final class ExpireTimeLocal {
    public final static ThreadLocal<Long> expireTime = new ThreadLocal<>();

    public static void set(Long milliseconds) {
        expireTime.set(milliseconds);
    }

    public static Long get() {
        return expireTime.get();
    }

    public static void remove() {
        expireTime.remove();
    }
}

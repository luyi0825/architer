package io.github.architers.context.lock;

/**
 * @author luyi
 */
@FunctionalInterface
public interface LockExecuteFunction {

    Object execute() throws Throwable;

}

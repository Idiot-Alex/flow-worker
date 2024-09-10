package com.hotstrip.flow.worker.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * show mybatis sql for custom
 */
@Slf4j
public class MybatisLogImpl implements Log {

    public MybatisLogImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable throwable) {
        log.error(s, throwable);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}

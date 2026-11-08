package com.productionready.nplusone.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class QueryCounter implements StatementInspector {

    private static final ThreadLocal<Integer> COUNT = ThreadLocal.withInitial(() -> 0);

    @Override
    public String inspect(String sql) {
        COUNT.set(COUNT.get() + 1);
        return sql;
    }

    public static void reset() {
        COUNT.set(0);
    }

    public static int count() {
        return COUNT.get();
    }
}

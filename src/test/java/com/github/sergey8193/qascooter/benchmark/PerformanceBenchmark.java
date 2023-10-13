package com.github.sergey8193.qascooter.benchmark;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static java.util.Collections.singletonMap;

public class PerformanceBenchmark implements BeforeEachCallback, AfterEachCallback {

    private long launchTime;
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        launchTime = System.currentTimeMillis();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        launchTime = System.currentTimeMillis() - launchTime;
        report(extensionContext, launchTime);
    }

    private static void report(ExtensionContext context, long elapsedTime) {
        String key = String.format("Benchmark: test '%s' elapsed time", context.getDisplayName());
        String value = String.format("%d ms.", elapsedTime);
        context.publishReportEntry(singletonMap(key, value));
    }
}

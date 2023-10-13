package com.github.sergey8193.qascooter.pom;

import com.github.sergey8193.qascooter.benchmark.PerformanceBenchmark;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(PerformanceBenchmark.class)
@interface PerformanceBenchmarks {}

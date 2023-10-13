package com.github.sergey8193.qascooter;

import com.github.sergey8193.qascooter.pom.HomePageTest;
import com.github.sergey8193.qascooter.pom.NewOrderTest;
import com.github.sergey8193.qascooter.pom.PageHeaderTest;
import com.github.sergey8193.qascooter.pom.TrackPageTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.UniqueIdTrackingListener;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import java.util.logging.*;

@DisplayName("Check the faq answers contents")
public class TestLauncher {

    String encoding = System.getProperty("console.encoding", "utf-8");
    Scanner sc = new Scanner(System.in, encoding);
    PrintStream ps = new PrintStream(System.out, true, encoding);

    // Logger ******************************************************************

    private static Logger logger;

    public TestLauncher() throws UnsupportedEncodingException {
    }

    public static Logger loggerSetup(String loggerName, boolean isFile, boolean isConsole, Level level) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
        String textDateTime = formatter.format(LocalDateTime.now());

        logger = Logger.getLogger(loggerName);
        logger.setLevel(level);
        if (! isFile) {
            try {
            Handler fh = new FileHandler("./" + textDateTime + "-" + loggerName + ".log");
            logger.addHandler(fh);
            } catch(IOException e) {
                System.out.println("Ошибка при создании файла логгера");
            }
        }
        if (isConsole) {
            Handler ch = new ConsoleHandler();
            logger.addHandler(ch);
        }

        return logger;
    }

    // Logger ******************************************************************

    public static void main(String[] args) {

        loggerSetup("com.github.sergey8193.qascooter", true, true, Level.ALL);

        logger.log(Level.WARNING, "Начало теста");

        Launcher launcher = LauncherFactory.create();
        // launcher.registerLauncherDiscoveryListeners();

        // FlightRecordingExecutionListener
        UniqueIdTrackingListener uniqueIdTrackingListener = new UniqueIdTrackingListener();
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        // launcher.registerTestExecutionListeners();
        // launcher.registerTestExecutionListeners(summaryGeneratingListener);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectClass(PageHeaderTest.class))
                .selectors(DiscoverySelectors.selectClass(HomePageTest.class))
                .selectors(DiscoverySelectors.selectClass(NewOrderTest.class))
                .selectors(DiscoverySelectors.selectClass(TrackPageTest.class))
                .listeners()
                .build();

        launcher.execute(request, uniqueIdTrackingListener, summaryGeneratingListener);

        try (var writer = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(writer);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
        String textDateTime = formatter.format(LocalDateTime.now());
        String FileIn = "./" + textDateTime + "-" + "test-results.log";

        try (PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(FileIn)))) {
            summaryGeneratingListener.getSummary().printTo(fileOut);
            uniqueIdTrackingListener.notifyAll();
        } catch (IOException ignored) {

        }

        logger.log(Level.WARNING, "Окончание теста");
    }
}

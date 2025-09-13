package io.jp.app;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.IntStream.range;

public class ConcurrentRunnerUtil {
    public static List<Callable<String>> createTasks(int tasksNumber, Callable<String> action) {
        return range(0, tasksNumber)
                .mapToObj(i -> action)
                .toList();
    }

    public static void runTasks(List<Callable<String>> tasks) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.invokeAll(tasks);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createAndRunTasks(int tasksNumber, Callable<String> action) {
        var tasks = createTasks(tasksNumber, action);
        runTasks(tasks);
    }
}

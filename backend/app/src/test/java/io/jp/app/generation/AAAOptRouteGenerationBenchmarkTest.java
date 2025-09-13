package io.jp.app.generation;

import io.jp.IntegrationTest;
import io.jp.database.init.RouteDataInit;
import io.jp.utils.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;

import static io.jp.app.ConcurrentRunnerUtil.createAndRunTasks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@TestPropertySource(properties = """
        service.implementation.route.generation=opt
        service.implementation.route.optimization=opt
        """)
public class AAAOptRouteGenerationBenchmarkTest extends GenerationTestBase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RouteDataInit routeDataInit;
    private boolean shouldDoWarmUp = true;
    private static final String OUTPUT_PATH = "route_generation_opt_" + Instant.now().getEpochSecond() + ".csv";
    private static final FileUtils FILE_UTILS = new FileUtils();

    @BeforeAll
    static void beforeAll() {
        FILE_UTILS.createFile(OUTPUT_PATH);
        FILE_UTILS.appendToFile(OUTPUT_PATH, "requests,time");
    }

    @BeforeEach
    void setup() throws Exception {
        if (shouldDoWarmUp) {
            routeDataInit.run(null);
            initializeMunicipalities(mockMvc);
            // doing request to not include lazy loading of beans into benchmark
            mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andReturn();
            shouldDoWarmUp = false;
        }
    }

    @Test
    void shouldGenerateRoute() throws Exception {
        // when
        System.out.println("Starting test");
        var before = Instant.now();
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
        var after = Instant.now();

        // then
        var millis = Duration.between(before, after).toMillis();
        System.out.println("Elapsed time: " + millis + "ms");
        FILE_UTILS.appendToFile(OUTPUT_PATH, "%s,%s".formatted(1, millis));
    }


    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 5000, 10000})
    void shouldGenerateManyRoutes(int tasksNumber) {
        // given
        var action = getRequestAction(mockMvc, request, status().isCreated());

        // when
        System.out.println("Starting test");
        var before = Instant.now();
        createAndRunTasks(tasksNumber, action);
        var after = Instant.now();


        // then
        var millis = Duration.between(before, after).toMillis();
        System.out.println("Elapsed time: " + millis + "ms");
        FILE_UTILS.appendToFile(OUTPUT_PATH, "%s,%s".formatted(tasksNumber, millis));
    }
}

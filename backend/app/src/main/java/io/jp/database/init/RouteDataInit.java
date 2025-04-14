package io.jp.database.init;

import io.jp.database.entities.Place;
import io.jp.database.entities.Route;
import io.jp.database.repositories.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteDataInit implements ApplicationRunner {
    private final RouteRepository routeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Route> routes = List.of(
                new Route(1L, "Royal Kraków: Tracing the Kings' Steps",
                        "Follow the footsteps of Polish royalty along Kraków's historic Royal Route, from medieval defense towers to Wawel Castle.",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/20091114_Krakow_Wawel_7770.jpg/2880px-20091114_Krakow_Wawel_7770.jpg",
                        List.of(new Place()), 0.01, 0.01)
        );
        routeRepository.saveAll(routes);
    }
}

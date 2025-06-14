package io.jp.api;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Route;
import io.jp.services.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RoutesAPI {
    private final RouteService routeService;

    @GetMapping("/predefined")
    public ResponseEntity<List<Route>> getPredefinedRoutes() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(routeService.getPredefinedRoutes());
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizedRoute> optimizedRoute(@RequestBody RouteOptimizationRequest routeOptimizationRequest) {
        var optimizedRoute = routeService.optimizeRoute(routeOptimizationRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(optimizedRoute);
    }
}

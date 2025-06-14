package io.jp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.api.dto.UserRouteAssociationRequest;
import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Route;
import io.jp.services.RouteGenerationService;
import io.jp.services.RouteOptimizationService;
import io.jp.services.RouteService;
import io.jp.services.UserRouteAssociationService;
import io.jp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static io.jp.api.WebConstants.MESSAGE_KEY;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RoutesAPI {
    private final RouteService routeService;
    private final RouteOptimizationService routeOptimizationService;
    private final RouteGenerationService routeGenerationService;
    private final ObjectMapper objectMapper;
    private final UserRouteAssociationService userRouteAssociationService;
    private final UserService userService;

    @GetMapping("/predefined")
    public ResponseEntity<List<Route>> getPredefinedRoutes() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(routeService.getPredefinedRoutes());
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizedRoute> optimizedRoute(@RequestBody RouteOptimizationRequest routeOptimizationRequest) {
        log.info("Optimizing route: {}", routeOptimizationRequest);
        var optimizedRoute = routeOptimizationService.optimizeRoute(routeOptimizationRequest);
        log.info("Optimized route: {}", optimizedRoute.route());
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(optimizedRoute);
    }

    @GetMapping("/generate/metadata")
    public ResponseEntity<RouteGenerationMetadata> getRouteGenerationMetadata() {
        log.info("Getting route generation metadata");
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(routeGenerationService.getRouteGenerationMetadata());
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateRoute(@RequestBody RouteGenerationRequest routeGenerationRequest,
                                                Authentication authentication) throws JsonProcessingException {
        log.info("Generating route: {}", routeGenerationRequest);
        var route = routeGenerationService.generateRoute(routeGenerationRequest);
        if (routeGenerationRequest.saveToAccount()) {
            if (authentication == null) {
                log.error("Cannot save generated route to nonexistent account");
                throw new UserService.UserUnauthorizedException();
            }
            routeService.saveRouteToAccount(route, authentication.getName());
        }
        var response = Map.of(MESSAGE_KEY, "Route is successfully generated", "route", route);
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(response));
    }

    @PostMapping("associate-with-user")
    public ResponseEntity<String> associateRouteWithUser(Authentication authentication, @RequestBody UserRouteAssociationRequest userRouteAssociationRequest) throws JsonProcessingException {
        if (authentication == null) {
            log.error("Cannot assign route to nonexistent user");
            throw new UserService.UserUnauthorizedException();
        }
        log.info("Associating route with user: {}", userRouteAssociationRequest);
        var user = userService.findUserByUsername(authentication.getName());
        log.info("Associating route ({}) with user ({})", userRouteAssociationRequest.routeName(), user.getUsername());
        userRouteAssociationService.associateRouteWithUser(user, userRouteAssociationRequest.routeName());
        var response = Map.of(MESSAGE_KEY, "Route is successfully associated with user");
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(response));
    }
}

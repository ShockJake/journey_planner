package io.jp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.api.dto.OptimizedRoutePersistenceRequest;
import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.api.dto.UserRouteAssociationRequest;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.services.route.generation.RouteGenerationService;
import io.jp.services.route.optimization.RouteOptimizationPersister;
import io.jp.services.route.optimization.RouteOptimizationService;
import io.jp.services.route.persistence.RouteService;
import io.jp.services.user.association.UserRouteAssociationService;
import io.jp.services.user.persistence.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import static io.jp.api.WebConstants.OPTIMIZED_ROUTE_ID;
import static io.jp.cache.CachedOptimizedRouteProvider.getOptimizedRouteFromCache;
import static io.jp.cache.CachedOptimizedRouteProvider.putOptimizedRouteToCache;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "no-opt")
public class BoxedRoutesAPI {
    private final RouteService routeService;
    private final RouteOptimizationService<OptimizedRouteBoxed> routeOptimizationService;
    private final RouteGenerationService<RouteBoxed> routeGenerationService;
    private final RouteOptimizationPersister routeOptimizationPersister;
    private final ObjectMapper objectMapper;
    private final UserRouteAssociationService userRouteAssociationService;
    private final UserService userService;

    @GetMapping("/predefined")
    public ResponseEntity<List<RouteBoxed>> getPredefinedRoutes() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(routeService.getPredefinedRoutes());
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizedRouteBoxed> optimizedRoute(@RequestBody RouteOptimizationRequest routeOptimizationRequest) {
        log.info("Optimizing route: {}", routeOptimizationRequest);
        var optimizedRoute = routeOptimizationService.optimizeRoute(routeOptimizationRequest);
        putOptimizedRouteToCache(optimizedRoute);
        log.info("Optimized route ({}) successfully", optimizedRoute.route().name());
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(optimizedRoute);
    }

    @PostMapping("/save-optimized-route")
    public ResponseEntity<?> saveOptimizedRoute(@RequestBody OptimizedRoutePersistenceRequest optimizedRoutePersistenceRequest, Authentication authentication) {
        if (authentication == null) {
            log.error("Cannot save optimized route for nonexistent user");
            throw new UserService.UserUnauthorizedException();
        }
        var user = userService.findUserByUsername(authentication.getName());
        log.info("Saving optimized route '{}' for user {}", optimizedRoutePersistenceRequest.routeName(), user.getUsername());
        var optimizedRoute = getOptimizedRouteFromCache(optimizedRoutePersistenceRequest.optimizationId());
        var result = routeOptimizationPersister.saveOptimizedRoute(optimizedRoute, user);

        var response = Map.of(MESSAGE_KEY, "Optimized route saved successfully", OPTIMIZED_ROUTE_ID, result.getId());
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(response);
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
            routeService.saveRouteBoxedToAccount(route, authentication);
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
        var user = userService.findUserByUsername(authentication.getName());
        log.info("Associating route ({}) with user ({})", userRouteAssociationRequest.routeName(), user.getUsername());
        userRouteAssociationService.associateRouteWithUser(user, userRouteAssociationRequest.routeName());
        var response = Map.of(MESSAGE_KEY, "Route is successfully associated with user");
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(response));
    }
}

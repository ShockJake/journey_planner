package io.jp.api;

import io.jp.api.dto.ChangeUserDataRequest;
import io.jp.api.dto.ChangeUserDataRequestInfoType;
import io.jp.security.GlobalExceptionsHandler.BadDataException;
import io.jp.services.RouteOptimizationService;
import io.jp.services.UserRouteAssociationService;
import io.jp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import static io.jp.api.WebConstants.CREATED_AT_KEY;
import static io.jp.api.WebConstants.MESSAGE_KEY;
import static io.jp.api.WebConstants.OPTIMIZED_ROUTES_KEY;
import static io.jp.api.WebConstants.ROUTES_KEY;
import static io.jp.api.WebConstants.USERNAME_KEY;
import static io.jp.api.dto.ChangeUserDataRequestInfoType.PASSWORD;
import static io.jp.api.dto.ChangeUserDataRequestInfoType.USERNAME;
import static java.time.ZoneId.systemDefault;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRouteAssociationService userRouteAssociationService;
    private final RouteOptimizationService routeOptimizationService;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(systemDefault());

    @PatchMapping
    public ResponseEntity<?> changeUserInfo(Authentication authentication, @RequestBody ChangeUserDataRequest changeUserDataRequest) {
        ChangeUserDataRequestInfoType infoType = changeUserDataRequest.infoType();
        log.debug("Changing used info ({})", infoType.name());
        if (USERNAME.equals(infoType)) {
            userService.changeUsername(authentication.getName(), changeUserDataRequest.value());
            return ResponseEntity.ok(Map.of(MESSAGE_KEY, "Login is changed successfully!"));
        }
        if (PASSWORD.equals(infoType)) {
            userService.changePassword(authentication.getName(), changeUserDataRequest.value());
            return ResponseEntity.ok(Map.of(MESSAGE_KEY, "Password is changed successfully!"));
        }
        throw new BadDataException("Unknown data type for change");
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody String username) {
        log.info("Deleting user {}", username);
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of(MESSAGE_KEY, "User deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        var user = userService.findUserByUsername(authentication.getName());
        log.debug("Getting user info for user {}", user.getUsername());

        var routes = userRouteAssociationService.getRoutesConnectedToUser(user);
        var optimizedRoutes = routeOptimizationService.getOptimizedRoutesByUser(user);

        var response = Map.of(USERNAME_KEY, user.getUsername(),
                CREATED_AT_KEY, dateFormat.format(user.getCreatedAt()),
                OPTIMIZED_ROUTES_KEY, optimizedRoutes,
                ROUTES_KEY, routes);

        log.debug("Returning user info: {}", response.keySet());
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(response);
    }
}

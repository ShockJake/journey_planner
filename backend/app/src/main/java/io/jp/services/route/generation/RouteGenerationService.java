package io.jp.services.route.generation;

import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.core.domain.route.RouteBoxed;

import java.util.Map;

public interface RouteGenerationService<RouteDataType> {
    Map<String, String> placeToImageUrl = Map.of(
            "Kraków", "https://www.adventurouskate.com/wp-content/uploads/2017/01/DSCF9597.jpg",
            "Warszawa", "https://ocdn.eu/pulscms-transforms/1/5VYk9kpTURBXy9jZTNlNGY2MTU3MWJiMDQyZTI2MjA4NjM5OTExNGE2Zi5qcGeTlQMAzJ_NE-zNCzSVAs0EsADDw5MJpjdhNTA0MgbeAAGhMAE/warszawa.jpeg",
            "Wrocław", "https://meteor-turystyka.pl/images/places/0/36.jpg",
            "Gdańsk", "https://www.nordicexperience.com/wp-content/uploads/2018/03/AdobeStock_129942755.jpeg",
            "Katowice", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Katowice.jpg/1200px-Katowice.jpg");

    RouteGenerationMetadata getRouteGenerationMetadata();

    RouteDataType generateRoute(RouteGenerationRequest routeGenerationRequest);
}

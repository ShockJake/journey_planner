package io.jp.core.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record Route(String name, String municipality, String description, String imageUrl, Point center, List<Place> places) {
}

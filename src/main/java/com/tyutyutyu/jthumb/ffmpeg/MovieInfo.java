package com.tyutyutyu.jthumb.ffmpeg;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MovieInfo {

    private final Map<String, Object> rawMovieInfo;

    private Dimension dimension = null;
    private Double duration = null;

    public double getDuration() {

        if (duration == null) {
            duration = initDuration();
        }

        return duration;
    }

    private double initDuration() {

        // noinspection unchecked
        var format = (Map<String, Object>) rawMovieInfo.get("format");

        return Double.parseDouble(
                format
                        .get("duration")
                        .toString()
        );
    }

    public Dimension getDimension() {

        if (dimension == null) {
            dimension = initDimension();
        }

        return dimension;
    }

    private Dimension initDimension() {

        int width, height;
        // noinspection unchecked
        List<Map<String, Object>> streams = (List<Map<String, Object>>) rawMovieInfo.get("streams");
        if (!streams.isEmpty()) {
            Map<String, Object> videoStream = streams
                    .stream()
                    .filter(s -> s.get("codec_type").equals("video"))
                    .findAny()
                    .orElseThrow();
            width = (Integer) videoStream.get("width");
            height = (Integer) videoStream.get("height");

            // noinspection unchecked
            Map<String, Object> tags = (Map<String, Object>) videoStream.get("tags");

            if (tags != null && tags.containsKey("rotate")) {
                int rotate = Integer.parseInt((String) tags.get("rotate"));

                if (rotate / 90 % 2 == 1) {
                    width = (Integer) videoStream.get("height");
                    height = (Integer) videoStream.get("width");
                }

            } else if (videoStream.containsKey("display_aspect_ratio") && !videoStream.get("display_aspect_ratio").equals("0:1")) {
                String[] aspectRatio = ((String) videoStream.get("display_aspect_ratio")).split(":");
                int x = Integer.parseInt(aspectRatio[0]);
                int y = Integer.parseInt(aspectRatio[1]);
                if (x > y && height > width || x < y && height < width) {
                    width = (Integer) videoStream.get("height");
                    height = (Integer) videoStream.get("width");
                }
            }
        } else {
            throw new RuntimeException();
        }

        return new Dimension(height, width);
    }

    public record Dimension(int height, int width) {
    }

}

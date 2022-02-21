package com.tyutyutyu.jthumb.ffmpeg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyutyutyu.common.command.Command;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Ffprobe {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MovieInfo getMovieInfo(Path movieFile) throws IOException, InterruptedException {

        String[] cmd = new String[]{
                "ffprobe",
                "-v", "quiet",
                "-print_format", "json",
                "-show_format", "-show_streams",
                movieFile.toFile().getAbsolutePath()
        };

        var javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        var output = new Command(cmd).execute();
        Map<String, Object> rawMovieInfo = objectMapper.readValue(output, javaType);
        log.trace("getMovieInfo - rawMovieInfo: {}", rawMovieInfo);

        return new MovieInfo(rawMovieInfo);
    }

}

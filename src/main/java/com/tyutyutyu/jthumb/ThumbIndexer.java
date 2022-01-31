package com.tyutyutyu.jthumb;

import com.tyutyutyu.jthumb.ffmpeg.Ffmpeg;
import com.tyutyutyu.jthumb.ffmpeg.Ffprobe;
import com.tyutyutyu.jthumb.ffmpeg.MovieInfo;
import com.tyutyutyu.jthumb.imagemagick.Convert;
import com.tyutyutyu.jthumb.imagemagick.Montage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
record ThumbIndexer(
        Ffmpeg ffmpeg,
        Ffprobe ffprobe,
        Convert convert,
        Montage montage
) {

    void make(Path filePath) throws IOException, InterruptedException {

        MovieInfo movieInfo = ffprobe.getMovieInfo(filePath);

        double duration = movieInfo.getDuration();
        log.trace("main - duration: {}", duration);

        Path subTempDir = Files.createTempDirectory(null);
        subTempDir.toFile().deleteOnExit();

        int step = calculateStepsCount(duration);

        for (int i = 1; i < duration; i = i + step) {

            String timeLabel = formatSeconds(i);
            String fileCounter = String.format("%04d", i);
            createThumb(filePath, subTempDir, timeLabel, fileCounter, movieInfo.getDimension().width(), movieInfo.getDimension().height());
        }

        montage.montage(subTempDir, filePath);
    }

    private int calculateStepsCount(double duration) {

        int step;
        if (duration >= 90) {
            step = 30;
        } else if (duration >= 60) {
            step = 20;
        } else if (duration >= 30) {
            step = 10;
        } else if (duration >= 10) {
            step = 2;
        } else {
            step = 1;
        }
        return step;
    }

    private void createThumb(
            Path filePath,
            Path subTempPath,
            String timeLabel,
            String fileCounter,
            int x,
            int y
    ) {

        log.trace("createThumb - timeLabel: {}, fileCounter: {}",
                timeLabel, fileCounter);

        try {
            log.debug("Create screenshot: {}", timeLabel);
            ffmpeg.createScreenshot(
                    filePath, subTempPath, timeLabel, fileCounter, x, y);

            convert.convert(subTempPath, timeLabel, fileCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatSeconds(int seconds) {

        int secs = seconds % 60;
        int mins = seconds / 60 % 60;
        int hours = seconds / 60 / 60;

        return String.format("%02d:%02d:%02d", hours, mins, secs);
    }

}

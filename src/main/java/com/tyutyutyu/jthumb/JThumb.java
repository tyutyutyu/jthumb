package com.tyutyutyu.jthumb;

import com.tyutyutyu.jthumb.ffmpeg.Ffmpeg;
import com.tyutyutyu.jthumb.ffmpeg.Ffprobe;
import com.tyutyutyu.jthumb.imagemagick.Convert;
import com.tyutyutyu.jthumb.imagemagick.Montage;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Path;

@Log4j2
public class JThumb {

    private final ThumbIndexer thumbIndexer;

    private JThumb() {
        thumbIndexer = new ThumbIndexer(
                new Ffmpeg(),
                new Ffprobe(),
                new Convert(),
                new Montage()
        );
    }

    public static JThumb newInstance() {
        return new JThumb();
    }

    @SneakyThrows
    public void run(Path filePath) {

        log.debug("run - filePath: {}", filePath);

        thumbIndexer.make(filePath);
    }

}

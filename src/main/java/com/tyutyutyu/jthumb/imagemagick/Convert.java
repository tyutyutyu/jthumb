package com.tyutyutyu.jthumb.imagemagick;

import com.tyutyutyu.common.command.Command;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Path;

public class Convert {

    @SneakyThrows
    public void convert(
            Path subTempPath,
            String timeLabel,
            String fileCounter
    ) {
        new Command(
                new String[]{
                        "convert",
//						"-debug", "configure",
                        subTempPath
                                .toFile()
                                + File.separator
                                + "output_"
                                + fileCounter
                                + ".jpg",
                        "-font", "Liberation-Serif",
                        "-undercolor", "black",
                        "-fill", "white",
                        "-gravity", "SouthWest",
                        "-annotate", "+5+5",
                        timeLabel,
                        subTempPath
                                .toFile()
                                + File.separator
                                + "output_2_"
                                + fileCounter
                                + ".jpg"
                }
        )
                .execute();
    }

}

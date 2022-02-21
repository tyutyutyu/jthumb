package com.tyutyutyu.jthumb.imagemagick;

import com.tyutyutyu.common.command.Command;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Path;

public class Montage {

    @SneakyThrows
    public void montage(
            Path subTempDir,
            Path filePath
    ) {
        new Command(new String[]{
                "montage",
                "-tile", "3",
                "-geometry", "+0+0",
                "-background", "black",
                "-compress", "JPEG2000",
                "-quality", "75",
                "-font", "Liberation-Serif",
                subTempDir.toFile()
                        + File.separator
                        + "output_2_*",
                filePath
                        .toFile()
                        .getAbsolutePath()
                        .substring(0, filePath
                                .toFile()
                                .getAbsolutePath()
                                .lastIndexOf('.')) + ".jpg"
        })
                .execute();
    }

}

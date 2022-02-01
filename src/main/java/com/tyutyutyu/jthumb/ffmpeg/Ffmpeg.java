package com.tyutyutyu.jthumb.ffmpeg;

import com.tyutyutyu.common.command.Command;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Log4j2
public class Ffmpeg {

	public void createScreenshot(
			Path filePath,
			Path subTempPath,
			String timeLabel,
			String fileCounter,
			int width,
			int height
	) throws IOException, InterruptedException {

		String scale = width > height
				? "scale=360:" + (height * 360 / width)
				: "scale=" + (width * 480 / height) + ":480";

		new Command(new String[]{
				"ffmpeg",
				"-loglevel", "panic",
				"-ss", timeLabel + "",
				"-i", filePath.toFile().getAbsolutePath(),
				"-codec:v", "mjpeg",
				"-frames:v", "1",
				"-filter:v", scale,
				subTempPath.toFile().getAbsolutePath()
						+ File.separator + "output_"
						+ fileCounter + ".jpg"
		})
				.execute();
	}

}

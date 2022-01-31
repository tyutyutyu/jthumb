package com.tyutyutyu.jthumb;

import lombok.extern.log4j.Log4j2;

import java.nio.file.Paths;

@Log4j2
public class Main {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Wrong parameter count!");
			System.exit(1);
		}

		JThumb jThumb = JThumb.newInstance();
		jThumb.run(Paths.get(args[0]));
	}

}

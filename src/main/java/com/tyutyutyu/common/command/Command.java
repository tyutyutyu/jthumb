package com.tyutyutyu.common.command;

import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Log4j2
public record Command(@Nonnull String[] command) {

    @Nonnull
    public String execute() throws IOException, InterruptedException {

        log.trace("executeCommand - command: {}", (Object) command);
        Process p = Runtime.getRuntime().exec(command);

        p.waitFor(5, TimeUnit.SECONDS);

        String output = new String(p.getInputStream().readAllBytes());
        String error = new String(p.getErrorStream().readAllBytes());

        if (!error.isEmpty()) {
            log.error("executeCommand - error: {}", error);
        }

        log.trace("executeCommand - result: {}", output);
        return output;
    }

}

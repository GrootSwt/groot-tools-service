package com.groot.business.schedule;

import com.groot.business.service.MemorandumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AutoDeleteMemorandumFile {

    private final MemorandumService memorandumService;

    public AutoDeleteMemorandumFile(final MemorandumService memorandumService) {
        this.memorandumService = memorandumService;
    }

    @Scheduled(cron = "0 0 * */2 * *")
    public void autoDeleteOutdatedFileTypeMemorandum() {
        try {
            memorandumService.autoDeleteOutdatedFileTypeMemorandum();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}

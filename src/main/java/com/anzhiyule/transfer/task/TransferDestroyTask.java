package com.anzhiyule.transfer.task;

import com.anzhiyule.transfer.model.Transfer;
import com.anzhiyule.transfer.service.ITransferService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TransferDestroyTask {

    private static int period = 7;

    private final ITransferService transferService;

    public TransferDestroyTask(ITransferService transferService) {
        this.transferService = transferService;
    }

    public static void setPeriod(int period) {
        TransferDestroyTask.period = period;
    }

    public static int getPeriod() {
        return TransferDestroyTask.period;
    }

    @Scheduled(cron = "0 0 1 1/1 * ? ")
    public void task() {
        long now = System.currentTimeMillis();
        long periods = period * 24 * 60 * 60 * 1000;
        Transfer[] transfers = transferService.listTransfers();
        Arrays.stream(transfers)
                .filter(t -> t.getCreateTime() < now - periods)
                .forEach(transferService::deleteTransfer);
    }
}

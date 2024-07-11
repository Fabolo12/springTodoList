package org.example.springprojecttodo.analytic;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class CronService {

    private static final String CRON_EVERY_TEN_SECONDS = "*/10 * * * * *";

    private final AnalyticRepository analyticRepository;

    @Value("${analytic.folder:null}")
    private String folder;

    @Value("${analytic.work:false}")
    private boolean isAnalyticWork;



    CronService(final AnalyticRepository analyticRepository) {
        this.analyticRepository = analyticRepository;
    }

    @Scheduled(cron = CRON_EVERY_TEN_SECONDS)
    public void showAmountOfCompletedTask() {
        if (!isAnalyticWork) {
            return;
        }
        final long completedTasks = analyticRepository.countCompletedTasks();
        log.info("Amount of completed tasks: {}", completedTasks);
    }

    @Scheduled(cron = CRON_EVERY_TEN_SECONDS)
    public void showAmountTasksByCliente() {
        if (!isAnalyticWork) {
            return;
        }
        final SqlRowSet result = analyticRepository.showInfoByClient();
        final StringBuilder content = new StringBuilder();
        final String pattern = "Client: %s, tasks: %d, completed: %d, not completed: %d%n";

        while (result.next()) {
            content.append(
                    String.format(pattern,
                            result.getString("fullName"),
                            result.getInt("taskCount"),
                            result.getInt("taskCompletedCount"),
                            result.getInt("taskNotCompletedCount")
                    )
            );
        }

        write("analytic.txt", content.toString());
    }

    @SneakyThrows
    private void write(
            final String fileName,
            final String content
    ) {
        final File file = new File(folder + fileName);
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            writer.write(formatter.format(LocalDate.now()));
            writer.newLine();
            writer.write(content);
            writer.newLine();
            writer.newLine();
        }
    }
}

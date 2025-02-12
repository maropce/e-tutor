package pl.maropce.etutor.statistics;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticsScheduler {

    private final MonthlyStatisticsService monthlyStatisticsService;

    public StatisticsScheduler(MonthlyStatisticsService monthlyStatisticsService) {
        this.monthlyStatisticsService = monthlyStatisticsService;
    }

    @Scheduled(fixedRate = 1000)
    public void generateStatistics() {
        monthlyStatisticsService.generateMonthlyStatistics();
    }
}

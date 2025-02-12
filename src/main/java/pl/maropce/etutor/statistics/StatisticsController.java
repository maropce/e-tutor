package pl.maropce.etutor.statistics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/stats")
public class StatisticsController {

    private final MonthlyStatisticsService monthlyStatisticsService;

    public StatisticsController(MonthlyStatisticsService monthlyStatisticsService) {
        this.monthlyStatisticsService = monthlyStatisticsService;
    }

    @PostMapping
    public ResponseEntity<MonthlyStatistics> generateMonthlyStatistics() {
        MonthlyStatistics statistics = monthlyStatisticsService.generateMonthlyStatistics();

        return ResponseEntity.ok(statistics);
    }
}

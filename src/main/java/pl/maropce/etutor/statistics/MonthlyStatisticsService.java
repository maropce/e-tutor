package pl.maropce.etutor.statistics;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.lesson.Lesson;
import pl.maropce.etutor.lesson.LessonRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class MonthlyStatisticsService {

    private final LessonRepository lessonRepository;
    private final MonthlyStatisticsRepository monthlyStatisticsRepository;

    public MonthlyStatisticsService(LessonRepository lessonRepository, MonthlyStatisticsRepository monthlyStatisticsRepository) {
        this.lessonRepository = lessonRepository;
        this.monthlyStatisticsRepository = monthlyStatisticsRepository;
    }

    public MonthlyStatistics generateMonthlyStatistics() {

        LocalDateTime startDate = LocalDateTime.now()
                .plusMonths(1)
                .minusMonths(1)
                .withDayOfMonth(1)
                .toLocalDate().atTime(0, 0);

        LocalDateTime endDate = YearMonth.from(startDate)
                .atEndOfMonth()
                .atTime(23, 59);

        System.out.println("START: " + startDate);
        System.out.println("END: " + endDate);


        List<Lesson> lessons = lessonRepository.findAllByStartDateTimeBetween(startDate, endDate);

        double totalHours = lessons.stream()
                .peek(lesson -> {
                    System.out.println("MONTH: " + lesson.getStartDateTime().getMonth() + " | " + lesson.getStartDateTime().getHour() + ":" + lesson.getStartDateTime().getMinute() + " - " + lesson.getEndDateTime().getHour() + ":" + lesson.getEndDateTime().getMinute());
                })
                .mapToDouble(lesson -> (double) Duration.between(lesson.getStartDateTime(), lesson.getEndDateTime()).toHours())
                .sum();

        double totalEarnings = totalHours * 55;


        MonthlyStatistics statistics = MonthlyStatistics.builder()
                .reportMonth(startDate.toLocalDate())
                .totalHours(totalHours)
                .totalEarnings(totalEarnings)
                .build();

        monthlyStatisticsRepository.save(statistics);

        return statistics;

    }
}

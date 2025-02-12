package pl.maropce.etutor.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyStatisticsRepository extends JpaRepository<MonthlyStatistics, Long> {
}

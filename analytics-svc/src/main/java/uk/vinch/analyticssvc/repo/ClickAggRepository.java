package uk.vinch.analyticssvc.repo;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.vinch.analyticssvc.model.ClickAgg;

import java.time.LocalDate;
import java.util.List;

public interface ClickAggRepository extends JpaRepository<ClickAgg, Long> {

    @Modifying @Transactional
    @Query(value = """
        insert into click_agg(slug, day, cnt)
        values (:slug, :day, 1)
        on conflict (slug, day)
        do update set cnt = click_agg.cnt + 1
        """, nativeQuery = true)
    void increment(@Param("slug") String slug, @Param("day") LocalDate day);

    List<ClickAgg> findBySlugAndDayBetweenOrderByDay(String slug,
                                                     LocalDate from,
                                                     LocalDate to);

    List<ClickAgg> findByDayOrderByCntDesc(LocalDate day, Pageable pageable);
}

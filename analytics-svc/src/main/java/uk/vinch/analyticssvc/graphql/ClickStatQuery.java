package uk.vinch.analyticssvc.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import uk.vinch.analyticssvc.model.ClickAgg;
import uk.vinch.analyticssvc.repo.ClickAggRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClickStatQuery {

    private final ClickAggRepository repo;

    @QueryMapping
    public List<ClickAgg> clicks(@Argument("slug") String slug,
                                 @Argument("from") String from,
                                 @Argument("to")   String to) {

        LocalDate fromD = from == null ? LocalDate.now().minusDays(7) : LocalDate.parse(from);
        LocalDate toD   = to   == null ? LocalDate.now()             : LocalDate.parse(to);
        return repo.findBySlugAndDayBetweenOrderByDay(slug, fromD, toD);
    }

    @QueryMapping
    public List<ClickAgg> top(@Argument("n")  int n,
                              @Argument("day") String day) {

        LocalDate d = LocalDate.parse(day);
        return repo.findByDayOrderByCntDesc(d, PageRequest.of(0, n));
    }
}

package uk.vinch.analyticssvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.vinch.analyticssvc.repo.ClickAggRepository;
import uk.vinch.analyticssvc.model.ClickEvent;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ClickConsumer {

    private final ClickAggRepository repo;

    @KafkaListener(topics = "clicks", groupId = "analytics")
    @Transactional
    public void onClick(ClickEvent evt) {
        LocalDate day = evt.ts()
                .atZone(ZoneOffset.UTC)
                .toLocalDate();
        repo.increment(evt.slug(), day);
    }
}

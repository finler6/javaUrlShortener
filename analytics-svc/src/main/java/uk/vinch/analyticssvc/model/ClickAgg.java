    package uk.vinch.analyticssvc.model;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.time.LocalDate;

    @Entity
    @Table(name = "click_agg",
            uniqueConstraints = @UniqueConstraint(columnNames = {"slug", "day"}))
    @Getter @Setter @NoArgsConstructor
    public class ClickAgg {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 16)
        private String slug;

        @Column(nullable = false)
        private LocalDate day;

        @Column(nullable = false)
        private long cnt;
    }

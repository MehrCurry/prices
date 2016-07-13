package com.example.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;

@Embeddable
@Builder
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
public class DateRange {
    @Column(name = "DFROM")
    private LocalDateTime from;
    @Column(name = "DTO")
    private LocalDateTime to;

    public DateRange(LocalDateTime from, LocalDateTime to) {
        checkArgument(to == null || from == null || to.isAfter(from));
        this.from = from;
        this.to = to;
    }

    public boolean isInRange(LocalDateTime other) {
        return (from == null || from.equals(other) || from.isBefore(other) )
                && (to == null || to.isAfter(other));
    }

    public boolean isOverlapping(DateRange validity) {
        return isInRange(validity.getFrom()) || isInRange(validity.getTo());
    }
}

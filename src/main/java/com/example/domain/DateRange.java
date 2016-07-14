package com.example.domain;

import com.example.converter.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Embeddable
@Builder
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
public class DateRange {
    @Column(name = "DFROM")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime from;
    @Column(name = "DTO")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime to;

    public DateRange(LocalDateTime from, LocalDateTime to) {
        checkArgument(to == null || from == null || to.isAfter(from));
        this.from = from!=null ? from : LocalDateTime.MIN;
        this.to = to!=null ? to : LocalDateTime.MAX;
    }

    public boolean isInRange(LocalDateTime other) {
        checkNotNull(other);
        return (from == null || from.equals(other) || from.isBefore(other) )
                && (to == null || to.isAfter(other));
    }

    public boolean isOverlapping(DateRange validity) {
        return from.isBefore(validity.getTo()) && validity.getFrom().isBefore(getTo());
    }

    @JsonIgnore
    public boolean isOpenEnded() {
        return LocalDateTime.MAX.equals(to);
    }
}

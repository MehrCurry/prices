package com.example.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DateRangeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LocalDateTime start;
    private LocalDateTime end;

    @Before
    public void setUp() throws Exception {
        start = LocalDateTime.parse("2007-12-03T10:15:30");
        end = LocalDateTime.parse("2007-12-04T10:15:26");
    }

    @Test
    public void testInclusiveRange() {
        DateRange range = DateRange.builder()
                .from(start)
                .to(end)
                .build();

        assertThat(range.isInRange(start)).isTrue();
        assertThat(range.isInRange(start.minusSeconds(1))).isFalse();
        assertThat(range.isInRange(end)).isFalse();
        assertThat(range.isInRange(end.minusSeconds(1))).isTrue();
    }

    @Test
    public void testOpenEndedRange() {
        DateRange range = DateRange.builder()
                .from(start)
                .to(null)
                .build();

        assertThat(range.isInRange(LocalDateTime.MAX));
        assertThat(range.isInRange(start)).isTrue();
        assertThat(range.isInRange(start.minusSeconds(1))).isFalse();
        assertThat(range.isInRange(end)).isTrue();
        assertThat(range.isInRange(end.minusSeconds(1))).isTrue();
    }

    @Test
    public void testOpenStartRange() {
        DateRange range = DateRange.builder()
                .from(null)
                .to(end)
                .build();

        assertThat(range.isInRange(LocalDateTime.MIN));
        assertThat(range.isInRange(start)).isTrue();
        assertThat(range.isInRange(start.minusSeconds(1))).isTrue();
        assertThat(range.isInRange(end)).isFalse();
        assertThat(range.isInRange(end.minusSeconds(1))).isTrue();
    }

    @Test
    public void testOverlappingRange() {
        thrown.expect(IllegalArgumentException.class);
        DateRange range = DateRange.builder()
                .from(end)
                .to(end)
                .build();
    }
}
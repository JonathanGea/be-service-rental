package com.gea.app.rental.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Request payload for returning a rental.
 */
@Getter
public class RentalReturnRequest {

    @NotNull
    private LocalDate returnDate;

    private String conditionNotes;
}

package com.gea.app.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ValidatedError {
    String field;
    String issue;
}

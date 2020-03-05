package ru.max.interview.spring.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumbersString {

    @Pattern(regexp = "((?:\\d+(\\s|$))+)")
    private String numbers;
}

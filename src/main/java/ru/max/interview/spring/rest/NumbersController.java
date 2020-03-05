package ru.max.interview.spring.rest;

import ru.max.interview.spring.rest.model.NumbersString;

public interface NumbersController {

    NumbersString increaseNumbers(NumbersString numbers);
}

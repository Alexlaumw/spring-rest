package ru.max.interview.spring.service;

import java.util.stream.IntStream;

public interface NumbersService {

    IntStream incrementNumbers(int[] numberAsStrings);
}

package ru.max.interview.spring.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.IntStream;

@Service
public class DefaultNumberService implements NumbersService {

    @Override
    public IntStream incrementNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .map(num -> ++num);
    }
}

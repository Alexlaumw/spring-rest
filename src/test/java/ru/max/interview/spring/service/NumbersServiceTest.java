package ru.max.interview.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.IntStream;

@RunWith(JUnit4.class)
public class NumbersServiceTest {


    private NumbersService numbersService;

    @Before
    public void setUp() {
        numbersService = new DefaultNumberService();
    }

    @Test
    public void shouldIncrementAllNumbersSuccessfully() {

        int[] numbers = {1, 2, 3, 4};

        IntStream incrementedNumbers = numbersService.incrementNumbers(numbers);

        Assert.assertArrayEquals(incrementedNumbers.toArray(), new int[] {2, 3, 4, 5});
    }

}
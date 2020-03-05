package ru.max.interview.spring.rest;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.max.interview.spring.rest.model.NumbersString;
import ru.max.interview.spring.service.NumbersService;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/numbers")
public class NumbersRestController implements NumbersController {

    private final NumbersService numbersService;

    public NumbersRestController(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public NumbersString increaseNumbers(@Validated @RequestBody NumbersString numbers) {

        int[] numbersArray = Arrays.stream(numbers.getNumbers().split("\\s"))
                .mapToInt(Integer::valueOf)
                .toArray();


        String forResponse = numbersService.incrementNumbers(numbersArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        return new NumbersString(forResponse);
    }

}

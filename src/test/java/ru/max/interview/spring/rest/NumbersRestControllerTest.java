package ru.max.interview.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.max.interview.spring.errors.ErrorResponse;
import ru.max.interview.spring.rest.model.NumbersString;
import ru.max.interview.spring.service.NumbersService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(NumbersRestController.class)
public class NumbersRestControllerTest {

    @MockBean
    private NumbersService numbersService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<NumbersString> numbersStringJson;

    private JacksonTester<ErrorResponse> errorResponseJson;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldReturnIncrementedNumbersResponseForCorrectRequest() throws Exception {
        given(numbersService.incrementNumbers(Mockito.eq(new int[]{1, 2, 3})))
                .willReturn(Arrays.stream(new int[]{2, 3, 4}));

        MockHttpServletResponse response = mvc.perform(
                post("/numbers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                numbersStringJson.write(new NumbersString("1 2 3")).getJson()
                        )
        ).andReturn().getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        Assert.assertEquals(
                numbersStringJson.write(new NumbersString("2 3 4")).getJson(),
                response.getContentAsString()
        );
    }

    @Test
    public void shouldReturnBadGatewayIfThereIsNaNInRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/numbers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .content(
                                numbersStringJson.write(new NumbersString("q1 2 3")).getJson()
                        )
        ).andReturn().getResponse();

        ErrorResponse expectedResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_GATEWAY.value())
                .message("There is a NaN in request string")
                .build();

        Assert.assertEquals(expectedResponse.getStatus(),response.getStatus());
        Assert.assertEquals(
                errorResponseJson.write(expectedResponse).getJson(),
                response.getContentAsString()
        );
    }

}
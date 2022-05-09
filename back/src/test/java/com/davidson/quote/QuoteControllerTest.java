package com.davidson.quote;

import com.davidson.controller.QuoteController;
import com.davidson.model.Quote;
import com.davidson.model.Skill;
import com.davidson.model.Trainer;
import com.davidson.service.QuoteService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class QuoteControllerTest {
    private static final Logger log = LoggerFactory.getLogger(QuoteControllerTest.class);

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private QuoteController quoteController;

    private MockMvc mvc;

    private Gson gson = new GsonBuilder().create();

    private final List<String> PUCES = Arrays.asList("sup1", "sup2", "sup3");
    private final Trainer TRAINER_1 = Trainer.builder().id(5L).image("image").description("description").nom("nom").build();
    private final Skill SKILL_1 = Skill.builder().id(3L).title("skill").description("description").puces(PUCES).trainers(Arrays.asList(TRAINER_1)).logo("logo").build();

    private final Quote QUOTE_1 = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_2 = Quote.builder().id(2L).name("test2").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_CREATE = Quote.builder().name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_UPDATE = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("06060606").description("desc").skills(Arrays.asList(SKILL_1)).send(false).build();
    private final Quote QUOTE_VALIDATE = Quote.builder().id(1L).name("test").mail("test@test.fr").tel("00000000").description("description").skills(Arrays.asList(SKILL_1)).send(true).build();


    @Before
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(quoteController).build();
    }

    @Test
    public void shouldGetQuotes() throws Exception{
        List<Quote> quotes = Arrays.asList(QUOTE_1, QUOTE_2);
        when(quoteService.findAll()).thenReturn(quotes);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/quotes")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(new Quote[] {QUOTE_1, QUOTE_2});
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldCreateQuote() throws Exception {
        when(quoteService.createQuote(QUOTE_CREATE)).thenReturn(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/quotes")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(QUOTE_CREATE))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(QUOTE_1);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldUpdateQuote() throws Exception{
        when(quoteService.updateQuote(QUOTE_UPDATE)).thenReturn(QUOTE_1);
        when(quoteService.findById(any())).thenReturn(Optional.of(QUOTE_1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/quotes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(QUOTE_UPDATE))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = gson.toJson(QUOTE_UPDATE);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotUpdateQuoteNotFound() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/quotes/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(QUOTE_UPDATE))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void shouldValidateQuote() throws Exception{
        when(quoteService.findById(1L)).thenReturn(Optional.of(QUOTE_1));
        when(quoteService.validateQuote(QUOTE_1)).thenReturn(QUOTE_VALIDATE);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/quotes/1/validate")
                .accept(APPLICATION_JSON);
        String expected = gson.toJson(QUOTE_VALIDATE);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
    }

    @Test
    public void shouldNotValidateQuoteNotFound() throws Exception{
        String bodyContent = gson.toJson(QUOTE_1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/quotes/2/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}

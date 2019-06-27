package com.payconiq.stocks;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.payconiq.stocks.model.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zalando.jackson.datatype.money.MoneyModule;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksApplicationControllerIntegrationTests {
  private ObjectMapper objectMapper = new ObjectMapper();
  @Autowired protected WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    initializeObjectMapper();
  }

  @Test
  public void getStocksIT() throws Exception {
    mockMvc
        .perform(get("/api/stocks"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$[0].name", is("Apple")))
        .andExpect(jsonPath("$[1].name", is("Facebook")));
  }

  @Test
  public void getOneStockHappyPathIT() throws Exception {
    mockMvc
        .perform(get("/api/stocks/2"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.name", is("Facebook")));
  }

  @Test
  public void getOneStockNotFoundIT() throws Exception {
    mockMvc
        .perform(get("/api/stocks/99"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void postStockIT() throws Exception {
    String GIVEN_JSON_STRING =
        "{"
            + "  \"currentPrice\": {"
            + "      \"amount\": 88,"
            + "      \"currency\": \"EUR\""
            + "    },"
            + "  \"id\": 6,"
            + "  \"lastUpdate\": \"2019-06-24T19:09:42.913\","
            + "  \"name\": \"MICROSOFT\""
            + "}";
    // when
    mockMvc
        .perform(
            post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GIVEN_JSON_STRING))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated());

    // then assert getting created stock
    mockMvc
        .perform(get("/api/stocks/6"))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.name", is("MICROSOFT")))
        .andExpect(jsonPath("$.lastUpdate", is("2019-06-24T19:09:42.913")));
  }

  @Test
  public void postStockAlreadyExistNameIT() throws Exception {
    String GIVEN_JSON_STRING =
        "{"
            + "  \"currentPrice\": {"
            + "      \"amount\": 88,"
            + "      \"currency\": \"EUR\""
            + "    },"
            + "  \"id\": 6,"
            + "  \"lastUpdate\": \"2019-06-24T19:09:42.913\","
            + "  \"name\": \"Apple\""
            + "}";
    // when
    mockMvc
        .perform(
            post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GIVEN_JSON_STRING))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateStockHappyPathIT() throws Exception {
    String newPriceJsonRequest =
        "{\n" + "      \"amount\": 99,\n" + "      \"currency\": \"EUR\"\n" + "    }";

    // get stock before update
    MvcResult result = mockMvc.perform(get("/api/stocks/2")).andExpect(status().isOk()).andReturn();
    Stock old = objectMapper.readValue(result.getResponse().getContentAsString(), Stock.class);
    // sleep to make sure time stamp changes
    Thread.sleep(1000);
    MvcResult result2 =
        mockMvc
            .perform(
                put("/api/stocks/2")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(newPriceJsonRequest))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.name", is("Facebook")))
            .andExpect(jsonPath("$.currentPrice.amount", is(99.0)))
            .andExpect(jsonPath("$.currentPrice.currency", is("EUR")))
            .andReturn();

    Stock updatedStock =
        objectMapper.readValue(result2.getResponse().getContentAsString(), Stock.class);
    // then check that timestamp last update has changed.;
    assertTrue(updatedStock.getLastUpdate().isAfter(old.getLastUpdate()));
  }

  private void initializeObjectMapper() {
    objectMapper
        .registerModule(new MoneyModule().withQuotedDecimalNumbers())
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());
  }

  @Test
  public void updateStockNotFoundIT() throws Exception {
    String newPriceJsonRequest =
        "{\n" + "      \"amount\": 99,\n" + "      \"currency\": \"EUR\"\n" + "    }";
    mockMvc
        .perform(
            put("/api/stocks/88")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(newPriceJsonRequest))
        .andExpect(status().isNotFound())
        .andDo(MockMvcResultHandlers.print());
  }
}

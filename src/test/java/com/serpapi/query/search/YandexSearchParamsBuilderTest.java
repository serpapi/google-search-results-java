package com.serpapi.query.search;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class YandexSearchParamsBuilderTest {

  private YandexSearchParamsBuilder builder;

  @Before
  public void setupTest() {
    builder = new YandexSearchParamsBuilder();
  }

  @Test
  public void testEngineParamIsCorrect() {
    assertEquals(builder.build(), Collections.singletonMap("engine", "yandex"));
  }
}

package com.serpapi;

import com.serpapi.model.ApiKey;
import lombok.Getter;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/15/21
 * Time: 5:21 PM
 */
@Getter
public class SerpApiUnitTest {
    private final ApiKey testApiKey = new ApiKey(System.getenv("SERPAPI_KEY"));
}

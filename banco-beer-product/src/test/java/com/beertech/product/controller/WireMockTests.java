package com.beertech.product.controller;


import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

public class WireMockTests {
    @Rule
    public WireMockRule wm = new WireMockRule(wireMockConfig().port(8090));

    @Test
    public void assertWiremockSetup() throws IOException {
        // Arrange - setup wiremock stubs
        configureStubs();

        // execute request through the http client
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://localhost:8090/beercoins/product")
                .get()
                .build();

        // Act - call the endpoint
        Response response = client.newCall(request).execute();

        // Assert - verify the response
        assertEquals(productList, response.body().string());
        verify(exactly(1),getRequestedFor(urlEqualTo("/beercoins/product")));

    }

    // configure stubs for wiremock
    private void configureStubs() {
        configureFor("localhost", 8090);
        stubFor(get(urlEqualTo("/beercoins/product"))
                .willReturn(aResponse()
                        .withBody(productList).withHeader("Content-Type", "application/json")));
    }

    String productList = "{\n" +
            "  \"content\": [\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"name\": \"Cerveja Antarctica Original 600ml Caixa (12 Unidades)\",\n" +
            "      \"description\": \"Lager\",\n" +
            "      \"price\": 75.48,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/175560-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 6,\n" +
            "      \"name\": \"Cerveja Blondine Hallo 500 ml\",\n" +
            "      \"description\": \"Trigo\",\n" +
            "      \"price\": 12.95,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/174733-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"Cerveja Colorado Cauim 600ml\",\n" +
            "      \"description\": \"Lager\",\n" +
            "      \"price\": 11.19,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/169376-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 8,\n" +
            "      \"name\": \"Cerveja Dádiva Fortune Ink Grisette 473ml\",\n" +
            "      \"description\": \"Lager\",\n" +
            "      \"price\": 16.14,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/175344-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 7,\n" +
            "      \"name\": \"Cerveja Lohn Bier Vintage Red Ale 600ml\",\n" +
            "      \"description\": \"Ale\",\n" +
            "      \"price\": 16.14,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/175693-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 9,\n" +
            "      \"name\": \"Cerveja Patagonia Amber Lager 473ml\",\n" +
            "      \"description\": \"Lager\",\n" +
            "      \"price\": 6.39,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/175507-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"name\": \"Cerveja Patagonia Bohemian Pilsener 473ml\",\n" +
            "      \"description\": \"Pilsen\",\n" +
            "      \"price\": 6.39,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/177921-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"name\": \"Cerveja Wäls Lager 473ml\",\n" +
            "      \"description\": \"Lager\",\n" +
            "      \"price\": 5.18,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/173442-1200-auto.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"name\": \"Kit Original 2 Garrafas 600ml + 2 copos 190ml\",\n" +
            "      \"description\": \"Lager\",\n" +
            "      \"price\": 19.9,\n" +
            "      \"imageName\": \"https://beertech-banco-produto.herokuapp.com/beercoins/product/image/173514-1200-auto.png\"\n" +
            "    }\n" +
            "\t]\n" +
            "\t}";
}

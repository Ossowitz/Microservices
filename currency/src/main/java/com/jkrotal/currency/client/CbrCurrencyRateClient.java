package com.jkrotal.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CbrCurrencyRateClient implements HttpCurrencyDateRateClient {

    public static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public String requestByDate(LocalDate date) {
        String baseUrl = "https://www.cbr.ru/scripts/XML_daily.asp";
        HttpClient client = HttpClient.newHttpClient();
        String url = buildUriRequest(baseUrl, date);
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUriRequest(String baseUrl, LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("date_req", DATE_TIME_FORMATTER.format(date))
                .build().toUriString();
    }
}

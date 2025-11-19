package org.example;

import org.example.DTO.api.LocationApiDto;
import org.example.config.props.OpenWeatherConfig;
import org.example.service.open_weather.OpenWeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherServiceTest {

    @Mock
    private OpenWeatherConfig config;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenWeatherService openWeatherService;

    @Before
    public void setup() {
    }

    @Test
    public void findCities_returnsExpectedLocations() {
        // Подготовим мок-ответ
        LocationApiDto loc = new LocationApiDto();
        loc.setName("Moscow");
        loc.setLatitude(new BigDecimal("55.7558"));
        loc.setLongitude(new BigDecimal("37.6173"));
        loc.setCountry("RU");
        LocationApiDto[] mockResponse = new LocationApiDto[]{loc};

        // Мокаем вызов RestTemplate
        when(restTemplate.getForObject(anyString(), eq(LocationApiDto[].class)))
                .thenReturn(mockResponse);

        // Вызываем сервис
        LocationApiDto[] result = openWeatherService.findCities("Moscow");

        // Проверяем результат
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals("Moscow", result[0].getName());
        assertEquals(new BigDecimal("55.7558"), result[0].getLatitude());
        assertEquals(new BigDecimal("37.6173"), result[0].getLongitude());
        assertEquals("RU", result[0].getCountry());
    }
}





package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

class LocalizationServiceImplTest {

    LocalizationServiceImpl localizationService;

    @BeforeEach
    public void setUp(){
        localizationService = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getSource")
    void locale(Country country, String message) {
        Assertions.assertEquals(message, localizationService.locale(country));
    }

    @Test
    void byNullLocale(){
        Assertions.assertThrows(NullPointerException.class, () -> localizationService.locale(null));
    }

    private static Stream<Arguments> getSource(){
        return Stream.of(
            Arguments.of(Country.RUSSIA, "Добро пожаловать"),
            Arguments.of(Country.USA, "Welcome"),
            Arguments.of(Country.BRAZIL, "Welcome")
        );
    }
}
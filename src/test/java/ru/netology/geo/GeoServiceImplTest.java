package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import java.util.stream.Stream;

class GeoServiceImplTest {
    private GeoServiceImpl geoService;


    @BeforeEach
    public void setUp(){
        geoService = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getSource")
    void byIp(String ipAddress, Location locationExpected) {
        Location locationActual = geoService.byIp(ipAddress);
        Assertions.assertEquals(locationExpected.getCity(), locationActual.getCity());
        Assertions.assertEquals(locationExpected.getCountry(), locationActual.getCountry());
        Assertions.assertEquals(locationExpected.getStreet(), locationActual.getStreet());
        Assertions.assertEquals(locationExpected.getBuiling(), locationActual.getBuiling());
    }

    @ParameterizedTest
    @ValueSource(strings = {"87.87.87.87", ""})
    void byEmptyIp(String ipAddress){
        Assertions.assertNull(geoService.byIp(ipAddress));
    }

    @Test
    void byNullIp(){
        Assertions.assertThrows(NullPointerException.class, () -> geoService.byIp(null));
    }

    @Test
    void byCoordinates() {
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(28.4, 105.1));
    }

    private static Stream<Arguments> getSource() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("96.12.145.1", new Location("New York", Country.USA, null, 0)),
                Arguments.of("172.15.123.14", new Location("Moscow", Country.RUSSIA, null, 0))
        );
    }
}
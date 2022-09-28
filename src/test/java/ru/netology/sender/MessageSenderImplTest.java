package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MessageSenderImplTest {

    private static final String IP_RUSSIA = "172.0.32.11";
    private static final String IP_NOT_RUSSIA = "96.44.183.149";
    private static final Location LOCATION_RUSSIA = new Location(null, Country.RUSSIA, null, 0);
    private static final Location LOCATION_NOT_RUSSIA = new Location(null, Country.USA, null, 0);

    @Mock
    private GeoServiceImpl geoService;

    @Mock
    private LocalizationServiceImpl localizationService;

    @BeforeEach
    public void setUp(){
        System.out.println("Начало тестирования");
    }

    @AfterEach
    public void finish(){
        System.out.println("Конец тестирования");
    }

    @DisplayName("Отправка русского сообщения")
    @Test
    public void sendRussiaTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, IP_RUSSIA);

        Mockito.when(geoService.byIp(IP_RUSSIA)).thenReturn(LOCATION_RUSSIA);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String actualResult = messageSender.send(headers);
        System.out.println();

        Assertions.assertEquals("Добро пожаловать", actualResult);

    }

    @DisplayName("Отправка НЕрусского сообщения")
    @Test
    public void sendNotRussiaTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, IP_NOT_RUSSIA);

        Mockito.when(geoService.byIp(Mockito.eq(IP_NOT_RUSSIA))).thenReturn(LOCATION_NOT_RUSSIA);
        Mockito.when(localizationService.locale(LOCATION_NOT_RUSSIA.getCountry())).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String actualResult = messageSender.send(headers);
        System.out.println();

        Assertions.assertEquals("Welcome", actualResult);
    }

    @DisplayName("Отправка сообщения при пустом адресе")
    @Test
    public void sendEmptyIpTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "");

        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String actualResult = messageSender.send(headers);
        System.out.println();

        Assertions.assertEquals("Welcome", actualResult);
    }

}
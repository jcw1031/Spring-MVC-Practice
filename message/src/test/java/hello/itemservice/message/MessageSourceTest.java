package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() throws Exception {
        String message = messageSource.getMessage("hello", null, null);
        assertThat(message).isEqualTo("hello");
    }

    @Test
    void notFoundMessageCode() {
        assertThrows(NoSuchMessageException.class, () ->
                messageSource.getMessage("no_code", null, null));
    }

    @Test
    void defaultMessage() {
        String message = messageSource.getMessage("no_code", null, "Default message", null);
        assertThat(message).isEqualTo("Default message");
    }

    @Test
    void argumentMessage() {
        String message = messageSource.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("hello Spring");
    }

    @Test
    void defaultLang() {
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("hello");
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }
}

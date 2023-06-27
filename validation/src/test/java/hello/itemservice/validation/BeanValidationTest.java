package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Test
    void beanValidation() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Item item = new Item();
        item.setItemName(" "); // 공백
        item.setPrice(0);
        item.setQuantity(10_000);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        for (ConstraintViolation<Item> violation : violations) {
            log.info("violation = {}", violation);
            log.info("violation.getMessage() = {}", violation.getMessage());
        }
    }
}

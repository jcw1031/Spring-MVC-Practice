package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ItemValidator implements Validator {

    private static final int MIN_PRICE = 1_000;
    private static final int MAX_PRICE = 1_000_000;
    private static final int MAX_QUANTITY = 9_999;
    private static final int MIN_TOTAL_PRICE = 10_000;

    @Override
    public boolean supports(Class<?> clazz) {
        // item == clazz
        // item == subItem
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        // 검증 로직
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");
        /*if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }*/
        if (item.getPrice() == null || item.getPrice() < MIN_PRICE || item.getPrice() > MAX_PRICE) {
            log.error("item.getPrice() = {}", item.getPrice());
            errors.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= MAX_QUANTITY) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < MIN_TOTAL_PRICE) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}

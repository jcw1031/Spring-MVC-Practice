package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class Item {

    //    @NotNull(groups = UpdateCheck.class)
    private Long id;

    //    @NotBlank(message = "공백 X {0}", groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    //    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //    @Range(min = 1_000, max = 1_000_000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    //    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //    @Max(value = 9_999, groups = {SaveCheck.class})
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

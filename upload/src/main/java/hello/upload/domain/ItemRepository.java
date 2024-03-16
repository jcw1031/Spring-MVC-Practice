package hello.upload.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {

    private final Map<Long, Item> store = new HashMap<>();

    private long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        return store.put(item.getId(), item);
    }

    public Item findById(Long id) {
        return store.get(id);
    }
}

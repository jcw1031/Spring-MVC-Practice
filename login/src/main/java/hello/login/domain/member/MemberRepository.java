package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Repository
public class MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        log.info("save: {}", member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return store.values()
                .stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findAny();
    }

    public List<Member> findAll() {
        return store.values()
                .stream()
                .collect(toUnmodifiableList());
    }

    public void clearStore() {
        store.clear();
    }
}

package gift;

import gift.entity.Member;
import gift.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member("test@example.com", "pw123", "USER");
        Member saved = memberRepository.save(member);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("test@example.com");
        assertThat(saved.getRole()).isEqualTo("USER");
    }

    @Test
    void findByEmail() {
        Member member = new Member("find@example.com", "pw123", "USER");
        memberRepository.save(member);

        Member found = memberRepository.findByEmail("find@example.com")
                .orElseThrow();
        assertThat(found.getEmail()).isEqualTo("find@example.com");
        assertThat(found.getRole()).isEqualTo("USER");
    }
}

package gift.repository;

import gift.entity.Member;
import gift.entity.Wish;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.awt.print.Pageable;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Page<Wish> findAllByMember(Member member, Pageable pageable);
}


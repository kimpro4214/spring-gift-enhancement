package gift.service;

import gift.dto.WishResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository,
                       MemberRepository memberRepository,
                       ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addWish(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + productId));

        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getWishes(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));

        List<Wish> wishes = wishRepository.findAllByMember(member);
        return wishes.stream()
                .map(WishResponseDto::new)
                .collect(Collectors.toList());
    }
}

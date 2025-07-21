package gift.controller;

import gift.dto.ProductOptionResponseDto;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductOptionController {

    private final ProductRepository productRepository;

    public ProductOptionController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{productId}/options")
    public List<ProductOptionResponseDto> getProductOptions(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return product.options().stream()
                .map(ProductOptionResponseDto::from)
                .toList();
    }
}

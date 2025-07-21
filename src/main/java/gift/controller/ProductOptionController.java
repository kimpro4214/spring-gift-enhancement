package gift.controller;

import gift.dto.ProductOptionResponseDto;
import gift.service.ProductOptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    public ProductOptionController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @GetMapping("/{productId}/options")
    public List<ProductOptionResponseDto> getProductOptions(@PathVariable Long productId) {
        return productOptionService.getOptionsByProductId(productId);
    }
}

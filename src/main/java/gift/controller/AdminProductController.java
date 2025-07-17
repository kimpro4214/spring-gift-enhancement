package gift.controller;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "id,desc") String[] sort,
                       Model model) {

        Sort sortObj = Sort.by(
                Arrays.stream(sort)
                        .map(s -> {
                            String[] parts = s.split(",");
                            return new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]);
                        })
                        .toList()
        );

        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<ProductResponseDto> productPage = productService.getProductList(pageable);

        model.addAttribute("productPage", productPage);
        return "admin/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequestDto("", 1, ""));
        return "admin/new";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute ProductRequestDto requestDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productRequestDto", requestDto);
            return "admin/new";
        }
        productService.addProduct(requestDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute ProductRequestDto requestDto) {
        productService.updateProduct(id, requestDto);
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}

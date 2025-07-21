package gift.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    protected Product() {
    }

    public Product(String name, String imageUrl, Integer price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static Product from(gift.dto.ProductRequestDto dto) {
        return new Product(dto.name(), dto.imageUrl(), dto.price());
    }

    public void update(String name, String imageUrl, Integer price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public void addOption(ProductOption option) {
        ensureUniqueOptionName(option.name());
        this.options.add(option);
        option.assignTo(this);
    }

    private void ensureUniqueOptionName(String newOptionName) {
        boolean hasDuplicate = this.options.stream()
                .anyMatch(option -> option.name().equals(newOptionName));

        if (hasDuplicate) {
            throw new IllegalArgumentException("동일한 옵션 이름이 이미 존재합니다: " + newOptionName);
        }
    }

    public void validateHasAtLeastOneOption() {
        if (options == null || options.isEmpty()) {
            throw new IllegalStateException("상품에는 하나 이상의 옵션이 있어야 합니다.");
        }
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String imageUrl() {
        return imageUrl;
    }

    public Integer price() {
        return price;
    }

    public List<ProductOption> options() {
        return List.copyOf(options);
    }
}

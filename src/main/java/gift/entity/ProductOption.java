package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_option")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    protected ProductOption() {
    }

    public ProductOption(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * 옵션을 특정 상품에 귀속시킨다.
     */
    public void assignTo(Product product) {
        this.product = product;
    }

    /**
     * 옵션의 재고 수량을 차감한다.
     * @param amount 차감할 수량
     */
    public void subtract(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("차감 수량은 1 이상이어야 합니다.");
        }
        if (quantity < amount) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.quantity -= amount;
    }

    public void validate() {
        validateQuantity();
        validateName();
    }

    private void validateName() {
        if (name.length() > 50) {
            throw new IllegalArgumentException("옵션 이름은 최대 50자까지 가능합니다.");
        }
        if (!name.matches("^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/_]+$")) {
            throw new IllegalArgumentException("허용되지 않은 특수 문자가 포함되어 있습니다.");
        }
    }

    private void validateQuantity() {
        if (quantity < 1 || quantity >= 100_000_000) {
            throw new IllegalArgumentException("옵션 수량은 1 이상 1억 미만이어야 합니다.");
        }
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int quantity() {
        return quantity;
    }

    public Product product() {
        return product;
    }
}

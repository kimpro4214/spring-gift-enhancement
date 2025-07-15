package gift.dto;

import gift.entity.Wish;

public class WishResponseDto {

    private final Long wishId;
    private final Long productId;
    private final String productName;

    public WishResponseDto(Wish wish) {
        this.wishId = wish.getId();
        this.productId = wish.getProduct().getId();
        this.productName = wish.getProduct().getName();
    }

    public Long getWishId() {
        return wishId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}

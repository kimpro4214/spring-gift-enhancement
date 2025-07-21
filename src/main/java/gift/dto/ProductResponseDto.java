package gift.dto;

import gift.entity.Product;

public class ProductResponseDto {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductResponseDto(Product product) {
        this.id = product.id();
        this.name = product.name();
        this.price = product.price();
        this.imageUrl = product.imageUrl();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

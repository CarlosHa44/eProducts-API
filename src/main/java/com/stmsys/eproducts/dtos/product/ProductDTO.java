package com.stmsys.eproducts.dtos.product;

import com.stmsys.eproducts.dtos.category.CategoryDTO;
import com.stmsys.eproducts.models.entities.Product;

import java.math.BigDecimal;

public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private Boolean active;
    private CategoryDTO category;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, BigDecimal unitPrice, String imageUrl, Boolean active, CategoryDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.imageUrl = imageUrl;
        this.active = active;
        this.category = category;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public CategoryDTO getCategory() { return category; }
    public void setCategory(CategoryDTO category) { this.category = category; }

    /**
     * Mappea Product -> ProductDTO incluyendo una CategoryDTO "shallow" (sin products) para evitar ciclos.
     */
    public static ProductDTO fromEntity(Product product) {
        if (product == null) return null;

        CategoryDTO categoryDTO = null;
        if (product.getCategory() != null && Boolean.TRUE.equals(product.getCategory().getActive())) {
            categoryDTO = CategoryDTO.shallowFromEntity(product.getCategory());
        }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getImageUrl(),
                product.getActive(),
                categoryDTO
        );
    }

    /**
     * Variante que no incluye la categoría (útil para listas dentro de CategoryDTO).
     */
    public static ProductDTO fromEntityWithoutCategory(Product product) {
        if (product == null) return null;
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getImageUrl(),
                product.getActive(),
                null
        );
    }
}
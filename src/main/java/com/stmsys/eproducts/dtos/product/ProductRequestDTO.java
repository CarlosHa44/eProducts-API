package com.stmsys.eproducts.dtos.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String name;

    private String description;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Debe especificar la categoría")
    private Long categoryId;

    @Size(max = 500, message = "La URL de la imagen no puede exceder los 500 caracteres")
    private String imageUrl;

    private Boolean active;

    public ProductRequestDTO() {}

    public ProductRequestDTO(String name, String description, BigDecimal unitPrice, Long categoryId, String imageUrl, Boolean active) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.active = active;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}

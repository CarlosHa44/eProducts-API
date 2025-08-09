package com.stmsys.eproducts.dtos.category;

import com.stmsys.eproducts.dtos.product.ProductDTO;
import com.stmsys.eproducts.models.entities.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private List<ProductDTO> products;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, String description, Boolean active, List<ProductDTO> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.products = products;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public List<ProductDTO> getProducts() { return products; }
    public void setProducts(List<ProductDTO> products) { this.products = products; }

    /**
     * Mappea Category -> CategoryDTO incluyendo productos activos (sin incluir la categoría de cada producto,
     * para evitar ciclos).
     */
    public static CategoryDTO fromEntity(Category category) {
        if (category == null) return null;

        List<ProductDTO> products = null;
        if (category.getProducts() != null) {
            products = category.getProducts().stream()
                    .filter(p -> Boolean.TRUE.equals(p.getActive()))
                    .map(ProductDTO::fromEntityWithoutCategory) // evita ciclo
                    .collect(Collectors.toList());
        }

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive(),
                products
        );
    }

    /**
     * Variante "ligera" que mapea sólo los campos de Category sin products (útil para incluir dentro de ProductDTO).
     */
    public static CategoryDTO shallowFromEntity(Category category) {
        if (category == null) return null;
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive(),
                null
        );
    }
}
package hr.tis.academy.common.dto;

import java.math.BigDecimal;

public class ProductDto {

    private String name;
    private BigDecimal price;
    private String unit;
    private Integer grade;

    public ProductDto(String name, BigDecimal price, Integer grade, String unit) {
        this.name = name;
        this.price = price;
        this.grade = grade;
        this.unit = unit;
    }

    public ProductDto() {
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public String getUnit() { return unit; }

    public void setUnit(String unit) { this.unit = unit; }

    public Integer getGrade() { return grade; }

    public void setGrade(Integer grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Product{naziv='" + name + "', cijena=" + price + ", mjernaJedinica='" + unit + "', ocjena=" + grade + "}";
    }
}

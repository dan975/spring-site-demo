package product.service.model.product.grouping;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@ToString
@Entity
@Table(name = "product_subcategory_names")
public class Subcategory {
    @Id
    @GeneratedValue
    @Column(name = "subcategory_id")
    private Long subcategoryId;

    @Column(name = "category_id")
    private Long categoryId;

    private String title;
}

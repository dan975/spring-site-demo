package product.service.model.product.grouping;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@ToString
@Entity
@Table(name="product_categories")
public class Grouping {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_key")
    private Long productKey;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
}

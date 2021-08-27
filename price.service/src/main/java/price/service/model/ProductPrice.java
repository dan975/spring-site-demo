package price.service.model;

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
@Table(name = "product_prices")
public class ProductPrice {

    @Id
    @GeneratedValue
    @Column(name = "product_key")
    private Long productKey;

    private Double price;
}

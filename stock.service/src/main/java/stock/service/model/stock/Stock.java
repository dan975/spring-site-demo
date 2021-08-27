package stock.service.model.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "product_key")
    private Long productKey;

    private String serialNumber;

    @OneToOne
    @JoinColumn(name = "size_id")
    private SizeMapping size;

    @OneToOne
    @JoinColumn(name = "color_id")
    private ColorMapping color;
}

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
@Table(name = "product_colors")
public class Color {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_key")
    private Long productKey;

    @OneToOne
    @JoinColumn(name = "color_id")
    private ColorMapping color;
}

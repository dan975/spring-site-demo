package stock.service.model.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "color_mappings")
public class ColorMapping {
    @Id
    @GeneratedValue
    private int colorId;
    private String name;
    private String rgb;
}

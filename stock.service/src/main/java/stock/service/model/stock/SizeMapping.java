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
@Table(name = "size_mappings")
public class SizeMapping {
    @Id
    @GeneratedValue
    private int sizeId;
    private String name;
}

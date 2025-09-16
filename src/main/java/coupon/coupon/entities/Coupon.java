package coupon.coupon.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coupon")
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="coupSeq")
    @SequenceGenerator(name="coupSeq", sequenceName="coup_seq", allocationSize=1)
    private Long id;

    private String name;
    private Integer maxOfUseCount;
    private Integer useCount;
    private LocalDateTime createDate;
    private String country;

    @Override
    public String toString() {
        return "Coupon: name: " + name + ", maxOfUse: " + maxOfUseCount + ", useCount: " + useCount + ", createDate: " 
        + createDate.format(DateTimeFormatter.ISO_DATE_TIME) + ", country: " + country + "; ";
    }
}

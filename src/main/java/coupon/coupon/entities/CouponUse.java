package coupon.coupon.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "coupon_use")
@AllArgsConstructor
@NoArgsConstructor
public class CouponUse {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="coupUseSeq")
    @SequenceGenerator(name="coupUseSeq", sequenceName="coup_use_seq", allocationSize=1)
    private Long id;

    private String coupon;
    private String userId;
    private LocalDateTime createDate;
}

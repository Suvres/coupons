package coupon.coupon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.coupon.entities.CouponUse;

@Repository
public interface CouponUseRepository extends JpaRepository<CouponUse, Long> {

}

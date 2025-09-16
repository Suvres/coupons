package coupon.coupon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.coupon.entities.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    public boolean existsByName(String name);
}

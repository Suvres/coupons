package coupon.coupon.helper;

import java.util.List;

import coupon.coupon.entities.Coupon;
import coupon.coupon.entities.CouponUse;
import jakarta.persistence.EntityManager;

public class DbCleaner {
    
    public final static List<String> CLASS_LIST = List.of(
        Coupon.class.getSimpleName(), 
        CouponUse.class.getSimpleName()
    );
    
    public static void cleanDb(EntityManager em) {
        DbCleaner.CLASS_LIST.stream()
            .map(c -> String.format("DELETE FROM %s WHERE 1 = 1", c))
            .forEach(a -> em.createQuery(a).executeUpdate());
    }
}

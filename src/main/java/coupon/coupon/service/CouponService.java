package coupon.coupon.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Service;

import coupon.coupon.entities.Coupon;
import coupon.coupon.entities.CouponUse;
import coupon.coupon.models.NewCouponRequestDto;
import coupon.coupon.models.ResponseCouponDto;
import coupon.coupon.models.ResponseMsg;
import coupon.coupon.models.UseCouponRequestDto;
import coupon.coupon.repositories.CouponRepository;
import coupon.coupon.repositories.CouponUseRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponUseRepository couponUseRepository;

    public ResponseCouponDto addNewCoupon(NewCouponRequestDto newCoupon) {
        if(newCoupon == null || newCoupon.country() == null || newCoupon.name() == null || newCoupon.maxUse() == null || newCoupon.maxUse() < 0) {
            return ResponseCouponDto.badRequest(ResponseMsg.EMPTY_DATA.getDescription());
        }

        if(couponRepository.existsByName(newCoupon.getUpperName())) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.COUPON_EXIST.getDescription(), newCoupon.name()));
        }

        List<String> countries = Arrays.asList(Locale.getISOCountries()); 

        if(!countries.contains(newCoupon.country().trim())) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.INVALID_COUNTRY.getDescription(), newCoupon.country()));
        }
        
        Coupon coupon = mapCoupon(newCoupon);
        couponRepository.save(coupon);
        log.info("==> New Coupon create: {}", coupon);
        return ResponseCouponDto.ok();
    }

    @Transactional
    public ResponseCouponDto useCoupon(String couponName, UseCouponRequestDto entity, HttpServletRequest request) {
        if(couponName == null || entity == null || entity.userId() == null) {
            return ResponseCouponDto.badRequest(ResponseMsg.INVALID_USE.getDescription());
        }

        if(couponUseRepository.existsByCouponAndUserId(couponName.toUpperCase(), entity.userId().toUpperCase())) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.USER_USE_COUPON.getDescription(), couponName));
        }

        Optional<Coupon> couOptional = couponRepository.findByNameAndCountry(couponName.toUpperCase(), request.getLocale().getLanguage());

        if (couOptional.isEmpty()) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.COUPON_IN_COUNTRY_NOT_EXIST.getDescription(), couponName));
        }
        
        Coupon coupon = couOptional.get();

        if(coupon.getMaxOfUseCount() <= coupon.getUseCount()) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.MAX_USE.getDescription(), couponName));
        }

        coupon.incrementUseCount();
        couponUseRepository.save(createCouponUse(couponName, entity.userId()));
        log.info("==> Coupon is used: {}", coupon);
        return ResponseCouponDto.ok();
    }

    private Coupon mapCoupon(NewCouponRequestDto dto) {
        return Coupon.builder()
            .country(LocaleUtils.toLocale(dto.getLowerCountry()).getLanguage())
            .name(dto.getUpperName())
            .maxOfUseCount(dto.maxUse())
            .createDate(LocalDateTime.now())
            .useCount(0)
            .build();
    }

    private CouponUse createCouponUse(String couponName, String userId) {
        return CouponUse.builder()
            .userId(userId.toUpperCase())
            .createDate(LocalDateTime.now())
            .coupon(couponName.toUpperCase())
            .build();
    }
}

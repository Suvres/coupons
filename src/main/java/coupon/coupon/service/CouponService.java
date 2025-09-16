package coupon.coupon.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Service;

import coupon.coupon.entities.Coupon;
import coupon.coupon.models.NewCouponRequestDto;
import coupon.coupon.models.ResponseCouponDto;
import coupon.coupon.models.ResponseMsg;
import coupon.coupon.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;

    public ResponseCouponDto addNewCoupon(NewCouponRequestDto newCoupon) {
        if(newCoupon == null || newCoupon.country() == null || newCoupon.name() == null || newCoupon.maxUse() == null || newCoupon.maxUse() < 0) {
            return ResponseCouponDto.badRequest(ResponseMsg.EMPTY_DATA.getDescription());
        }

        if(couponRepository.existsByName(newCoupon.getUpperName())) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.COUPON_EXIST.getDescription(), newCoupon.name()));
        }

        String[] countries = Locale.getISOCountries(); 

        if(!Arrays.asList(countries).contains(newCoupon.country().toUpperCase().trim())) {
            return ResponseCouponDto.badRequest(String.format(ResponseMsg.INVALID_COUNTRY.getDescription(), newCoupon.country()));
        }
        
        Coupon coupon = mapCoupon(newCoupon);
        couponRepository.save(coupon);
        log.info("==> New Coupon create: {}", coupon);
        return ResponseCouponDto.ok();
    }

    private Coupon mapCoupon(NewCouponRequestDto dto) {
        return Coupon.builder()
            .country(LocaleUtils.toLocale(dto.getLowerCountry()).getCountry())
            .name(dto.getUpperName())
            .maxOfUseCount(dto.maxUse())
            .createDate(LocalDateTime.now())
            .useCount(0)
            .build();
    }
}

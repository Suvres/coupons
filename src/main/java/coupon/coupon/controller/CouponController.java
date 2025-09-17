package coupon.coupon.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coupon.coupon.models.NewCouponRequestDto;
import coupon.coupon.models.ResponseCouponDto;
import coupon.coupon.models.UseCouponRequestDto;
import coupon.coupon.service.CouponService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


@RestController()
@RequiredArgsConstructor
public class CouponController {
    
    private final CouponService couponService;

    /**
     * Method for endpoint: /coupon/new
     * 
     * This method is used for create new Coupon
     * @param entity request body in request
     * @return results of coupon add
     */
    @PostMapping("/coupon/new")
    public ResponseEntity<ResponseCouponDto> addNewCoupon(@RequestBody NewCouponRequestDto entity) {        
        ResponseCouponDto response = couponService.addNewCoupon(entity);
        return Objects.equals(response.status(), ResponseCouponDto.OK_STATUS) ? 
            ResponseEntity.ok(response)
            : ResponseEntity.badRequest().body(response);
    }

    /**
     * Method for entpoint: /coupon/use/{coupon}
     * 
     * This method is used for using a coupon by user
     * @param coupon coupon id for using it
     * @param entity request body with userId
     * @param request request parameters
     * @return results of coupon using
     */
    @PutMapping("/coupon/use/{coupon}")
    public ResponseEntity<ResponseCouponDto> useCoupon(@PathVariable String coupon, @RequestBody UseCouponRequestDto entity, HttpServletRequest request) {        
        ResponseCouponDto response = couponService.useCoupon(coupon, entity, request);
        
        return Objects.equals(response.status(), ResponseCouponDto.OK_STATUS) ? 
            ResponseEntity.ok(response)
            : ResponseEntity.badRequest().body(response);
    }

}

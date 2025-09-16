package coupon.coupon.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/coupon/new")
    public ResponseEntity<ResponseCouponDto> addNewCoupon(@RequestBody NewCouponRequestDto entity) {        
        ResponseCouponDto response = couponService.addNewCoupon(entity);
        return Objects.equals(response.status(), HttpStatus.OK.name()) ? 
            ResponseEntity.ok(response)
            : ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/coupon/use/{coupon}")
    public ResponseEntity<String> useCoupon(@PathVariable String coupon, @RequestBody UseCouponRequestDto entity, HttpServletRequest request) {        
        return ResponseEntity.ok(coupon + " " + entity.userId() + " " + request.getRemoteAddr() + " " + request.getLocalAddr() + " " + request.getLocale().getCountry());
    }


    @GetMapping("/")
    public String getMethodName() {
        return "test";
    }
    

}

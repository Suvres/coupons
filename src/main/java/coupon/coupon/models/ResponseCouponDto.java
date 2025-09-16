package coupon.coupon.models;

import org.springframework.http.HttpStatus;

public record ResponseCouponDto(String status, String error) {

    public static ResponseCouponDto ok() {
        return new ResponseCouponDto(HttpStatus.OK.name(), null);
    }

    public static ResponseCouponDto badRequest(String error) {
        return new ResponseCouponDto(HttpStatus.BAD_REQUEST.name(), error);
    }
}

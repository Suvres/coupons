package coupon.coupon.models;

public record ResponseCouponDto(String status, String errorMsg) {

    public static final String OK_STATUS = "OK";
    public static final String ERROR_STATUS = "ERROR";

    public static ResponseCouponDto ok() {
        return new ResponseCouponDto(OK_STATUS, null);
    }

    public static ResponseCouponDto badRequest(String error) {
        return new ResponseCouponDto(ERROR_STATUS, error);
    }

}

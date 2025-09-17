package coupon.coupon.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseMsg {
    COUPON_EXIST("Coupon with this name: %s exist"),
    COUPON_IN_COUNTRY_NOT_EXIST("Coupon with this name: %s not exist"),
    MAX_USE("Coupon with this name: %s was used"),
    INVALID_COUNTRY("Invalid country code: %s"),
    EMPTY_DATA("Invalid schema of data. Valid is {name, country}"),
    INVALID_USE("Invalid schema of data. Valid is {userId}"),
    USER_USE_COUPON("User used coupon with name: %s");

    private final String description;
}

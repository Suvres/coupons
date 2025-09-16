package coupon.coupon.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseMsg {
    COUPON_EXIST("Coupon with this name (%s) exist"),
    INVALID_COUNTRY("Invalid country code: %s"),
    EMPTY_DATA("Invalid schema of data. Valid is {name, country}");

    private final String description;
}

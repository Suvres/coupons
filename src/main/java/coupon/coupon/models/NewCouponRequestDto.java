package coupon.coupon.models;

public record NewCouponRequestDto(String name, Integer maxUse, String country) {

    public String getUpperName() {
        return name.toUpperCase();
    }

    public String getLowerCountry() {
        return country.toLowerCase();
    }
}

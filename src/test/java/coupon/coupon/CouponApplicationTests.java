package coupon.coupon;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import coupon.coupon.helper.DbCleaner;
import coupon.coupon.models.NewCouponRequestDto;
import coupon.coupon.models.ResponseCouponDto;
import coupon.coupon.models.UseCouponRequestDto;
import coupon.coupon.service.CouponService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CouponApplicationTests {

	private static final String COUPON = "WIOSNA"; 
	private static final String COUPON_2 = "WIoSNA"; 
	private static final String USER_ID = "user-123";
	private static final Integer MAX_USE = 2;
	private static final String COUNTRY = "PL";

	private final EntityManager em;
	private final CouponService couponService;
	private final PlatformTransactionManager transactionManager;
	private TransactionTemplate transactionTemplate;

	@PostConstruct
	public void postConstruct() {
		transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@AfterEach
	@BeforeEach
	public void afterEach() {
		useTransaction(() -> {
			DbCleaner.cleanDb(em);
		});
	}


	@Test
	void testAddAndUseCoupon() {
		useTransaction(() -> {
			ResponseCouponDto resp = couponService.addNewCoupon(new NewCouponRequestDto(COUPON, MAX_USE, COUNTRY));
			Assertions.assertEquals("OK", resp.status());
		});

		useTransaction(() -> {
			ResponseCouponDto resp = couponService.useCoupon(COUPON, new UseCouponRequestDto(USER_ID), createServletRequest("pl"));
			Assertions.assertEquals("OK", resp.status());
		});
	}

	@Test
	void testAddAndUseCouponFailure() {
		useTransaction(() -> {
			ResponseCouponDto resp = couponService.addNewCoupon(new NewCouponRequestDto(COUPON, 1, COUNTRY));
			Assertions.assertEquals("OK", resp.status());
		});

		useTransaction(() -> {
			ResponseCouponDto resp = couponService.useCoupon(COUPON, new UseCouponRequestDto(USER_ID), createServletRequest("en"));
			Assertions.assertNotEquals("OK", resp.status());
		});

		useTransaction(() -> {
			ResponseCouponDto resp = couponService.useCoupon(COUPON_2, new UseCouponRequestDto(USER_ID), createServletRequest("pl"));
			Assertions.assertEquals("OK", resp.status());
		});

		useTransaction(() -> {
			ResponseCouponDto resp = couponService.useCoupon(COUPON_2, new UseCouponRequestDto(USER_ID), createServletRequest("pl"));
			Assertions.assertNotEquals("OK", resp.status());
		});
	}

	void useTransaction(Runnable run) {
		transactionTemplate.execute(status -> {
			run.run();
			status.flush();
			return null;
		});
	}

	private HttpServletRequest createServletRequest(String lang) {
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addPreferredLocale(Locale.of(lang));

		return req;
	}


}

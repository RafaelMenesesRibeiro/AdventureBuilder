package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {
	private static final String NIFHotel = "123456788";
	private static final String IBANHotel = "IBAC";
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private final double SINGLE_PRICE = 200;
	private final double DOUBLE_PRICE = 300;

	@Test
	public void success() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);

		Assert.assertEquals(HOTEL_NAME, hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}

	@Test(expected = HotelException.class)
	public void nullCode() {
		new Hotel(null, HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void blankCode() {
		new Hotel("      ", HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void emptyCode() {
		new Hotel("", HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void nullName() {
		new Hotel(HOTEL_CODE, null, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void blankName() {
		new Hotel(HOTEL_CODE, "  ", NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void emptyName() {
		new Hotel(HOTEL_CODE, "", NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void codeSizeLess() {
		new Hotel("123456", HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void codeSizeMore() {
		new Hotel("12345678", HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void codeNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
		new Hotel(HOTEL_CODE, HOTEL_NAME + " City", NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void singleRoomPriceInvalid() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIFHotel, IBANHotel, 0, DOUBLE_PRICE);
	}

	@Test(expected = HotelException.class)
	public void doubleRoomPriceInvalid() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIFHotel, IBANHotel, SINGLE_PRICE, 0);
	}

	@Test(expected = HotelException.class)
	public void bothRoomPricesInvalid() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIFHotel, IBANHotel, 0, 0);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}

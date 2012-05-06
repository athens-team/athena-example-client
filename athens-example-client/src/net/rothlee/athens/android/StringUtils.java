package net.rothlee.athens.android;

public class StringUtils {

	public static final String EMPTY_STRING = "";

	private StringUtils() {
	}

	public static boolean isEmptyOrNull(String str) {
		return str == null || !(str.length() > 0);
	}

	public static String nvl(String str) {
		if (isEmptyOrNull(str)) { return EMPTY_STRING; }
		return str;
	}
}

package org.cisco.ciscoutil.cu.util;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class NumberUtil {
	private static Set<String> ALLOWED_NUM_STRINGS = Sets.newHashSet("zero", "one", "two", "three", "four", "five",
			"six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
			"seventeen", "eighteen", "nineteen", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety", "hundred", "thousand", "million", "billion", "trillion");

	private static Map<String, Long> ALLOWED_TUPLE_STRINGS = Maps.newHashMap();

	static {
		ALLOWED_TUPLE_STRINGS.put("single", 1L);
		ALLOWED_TUPLE_STRINGS.put("double", 2L);
		ALLOWED_TUPLE_STRINGS.put("triple", 3L);
		ALLOWED_TUPLE_STRINGS.put("quadruple", 4L);
		ALLOWED_TUPLE_STRINGS.put("quintuple", 5L);
		ALLOWED_TUPLE_STRINGS.put("sextuple", 6L);
		ALLOWED_TUPLE_STRINGS.put("septuple", 7L);
		ALLOWED_TUPLE_STRINGS.put("octuple", 8L);
		ALLOWED_TUPLE_STRINGS.put("nonuple", 9L);
		ALLOWED_TUPLE_STRINGS.put("decuple", 10L);
	}

	public static Long strToNum(String number) {
		Long finalResult = 0L;
		if (StringUtils.isNotEmpty(number)) {
			try {
				finalResult = Long.parseLong(number);
				if (finalResult != null) {
					return finalResult;
				}
			} catch (NumberFormatException e) {
			}

			Long result = 0L;
			finalResult = 0L;
			number = number.replaceAll("-", " ");
			number = number.toLowerCase().replaceAll(" and", " ");
			String[] splitParts = number.trim().split("\\s+");

			if (isValidStringNumber(splitParts)) {
				for (String str : splitParts) {
					if (str.equalsIgnoreCase("zero")) {
						result += 0;
					} else if (str.equalsIgnoreCase("one")) {
						result += 1;
					} else if (str.equalsIgnoreCase("two")) {
						result += 2;
					} else if (str.equalsIgnoreCase("three")) {
						result += 3;
					} else if (str.equalsIgnoreCase("four")) {
						result += 4;
					} else if (str.equalsIgnoreCase("five")) {
						result += 5;
					} else if (str.equalsIgnoreCase("six")) {
						result += 6;
					} else if (str.equalsIgnoreCase("seven")) {
						result += 7;
					} else if (str.equalsIgnoreCase("eight")) {
						result += 8;
					} else if (str.equalsIgnoreCase("nine")) {
						result += 9;
					} else if (str.equalsIgnoreCase("ten")) {
						result += 10;
					} else if (str.equalsIgnoreCase("eleven")) {
						result += 11;
					} else if (str.equalsIgnoreCase("twelve")) {
						result += 12;
					} else if (str.equalsIgnoreCase("thirteen")) {
						result += 13;
					} else if (str.equalsIgnoreCase("fourteen")) {
						result += 14;
					} else if (str.equalsIgnoreCase("fifteen")) {
						result += 15;
					} else if (str.equalsIgnoreCase("sixteen")) {
						result += 16;
					} else if (str.equalsIgnoreCase("seventeen")) {
						result += 17;
					} else if (str.equalsIgnoreCase("eighteen")) {
						result += 18;
					} else if (str.equalsIgnoreCase("nineteen")) {
						result += 19;
					} else if (str.equalsIgnoreCase("twenty")) {
						result += 20;
					} else if (str.equalsIgnoreCase("thirty")) {
						result += 30;
					} else if (str.equalsIgnoreCase("forty")) {
						result += 40;
					} else if (str.equalsIgnoreCase("fifty")) {
						result += 50;
					} else if (str.equalsIgnoreCase("sixty")) {
						result += 60;
					} else if (str.equalsIgnoreCase("seventy")) {
						result += 70;
					} else if (str.equalsIgnoreCase("eighty")) {
						result += 80;
					} else if (str.equalsIgnoreCase("ninety")) {
						result += 90;
					} else if (str.equalsIgnoreCase("hundred")) {
						result = result == 0L ? 100 : result * 100;
					} else if (str.equalsIgnoreCase("thousand")) {
						result = result == 0L ? 1000 : result * 1000;
						finalResult += result;
						result = 0L;
					} else if (str.equalsIgnoreCase("million")) {
						result = result == 0L ? 1000000 : result * 1000000;
						finalResult += result;
						result = 0L;
					} else if (str.equalsIgnoreCase("billion")) {
						result = result == 0L ? 1000000000 : result * 1000000000;
						finalResult += result;
						result = 0L;
					} else if (str.equalsIgnoreCase("trillion")) {
						result = result == 0L ? 1000000000000L : result * 1000000000000L;
						finalResult += result;
						result = 0L;
					}
				}

				finalResult += result;
				result = 0L;
				return finalResult;
			}
		}
		return null;
	}

	public static String tupleNameToNum(String token) {
		String finalStr = StringUtils.EMPTY;

		if (StringUtils.isNotEmpty(token)) {
			String[] splitParts = token.split("\\s+");
			for (String split : splitParts) {
				finalStr = (finalStr.isEmpty() ? finalStr : finalStr + " ");

				if (ALLOWED_TUPLE_STRINGS.containsKey(split)) {
					finalStr += String.valueOf(ALLOWED_TUPLE_STRINGS.get(split));
				} else {
					finalStr += split;
				}
			}
		}
		return finalStr;
	}

	private static boolean isValidStringNumber(String[] splitParts) {
		boolean isValidInput = true;
		for (String str : splitParts) {
			if (!ALLOWED_NUM_STRINGS.contains(str)) {
				isValidInput = false;
				break;
			}
		}
		return isValidInput;
	}
	
	public static <T extends Number> boolean isValid(T number) {
		if (number == null || number.doubleValue() == 0) {
			return false;
		}
		return true;
	}

	public static boolean isNull(Long number) {
		return (number == null);
	}

	public static void isNull(Long number, String errorCode) {
		if (number == null) {
			throw new IllegalArgumentException(errorCode);
		}
	}

	public static boolean isNull(Integer number) {
		return (number == null);
	}

	public static void isNull(Integer number, String errorCode) {
		if (number == null) {
			throw new IllegalArgumentException(errorCode);
		}
	}

	public static boolean isNull(Double number) {
		return (number == null);
	}

	public static void isNull(Double number, String errorCode) {
		if (number == null) {
			throw new IllegalArgumentException(errorCode);
		}
	}

	public static boolean isAnyNull(List<Long> numbers) {
		for (Long number : numbers) {
			if (isNull(number)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAnyNotNull(List<Long> numbers) {
		for (Long number : numbers) {
			if (!isNull(number)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAnyNull(List<Long> numbers, String errorCode) {
		for (Long number : numbers) {
			if (isNull(number)) {
				throw new IllegalArgumentException(errorCode);
			}
		}
		return false;
	}
	
	public static boolean isAllNull(List<Long> numbers, String errorCode) {
		for (Long number : numbers) {
			if (!isNull(number)) {
				return false;
			}
		}
		return true;
	}
	
	public static void validateAny(List<Long> numbers, String errorCode) {
		for (Long number : numbers) {
			if (!isNull(number)) {
				return;
			}
		}
		throw new IllegalArgumentException(errorCode);
	}
	
	public static void validate(Long number, String errorCode) {
		if (isValid(number)) {
			return;
		}
		throw new IllegalArgumentException(errorCode);
	}
	
	public static <T extends Number> T getValue(T value, T defaultValue) {
		return isValid(value) ? value : defaultValue;
	}
	
//	public static void main(String[] args) {
//		Long a = 121323456543343L;
//		System.out.println(NumberUtil.isValid(a));
//		Double b = 123.23;
//		System.out.println(NumberUtil.isValid(b));
//		Integer c= 12324;
//		System.out.println(NumberUtil.isValid(c));
//		Float j = 1.12F;
//		System.out.println(NumberUtil.isValid(j));
//		short d = 2;
//		System.out.println(NumberUtil.isValid(d));
//		long e = 123476432135421L;
//		System.out.println(NumberUtil.isValid(e));
//		double f = 12345.23;
//		System.out.println(NumberUtil.isValid(f));
//		int g= 12344321;
//		System.out.println(NumberUtil.isValid(g));
//		byte h = 1;
//		System.out.println(NumberUtil.isValid(h));
//		float i = 123453.12F;
//		System.out.println(NumberUtil.isValid(i));
//	}
}
package util;

public class TestUtils {

    public static String genNumStr() {
        int number = (int) (Math.random() * 100_000);
        return Integer.toString(number);
    }

}

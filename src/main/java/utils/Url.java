package utils;

public class Url {

    private Url() {
    }

    public static String get(final String urlId) {
        return PropertyLoader.load(PropertyLoader.Properties.URLS, urlId);
    }

}

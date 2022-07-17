package utils;

import org.openqa.selenium.By;

public class AppLocator {

    private static class IOS extends AppLocator {
        private IOS(final By by) {
            super(by);
        }
    }

    public static AppLocator buildIOS(final By by) {
        return new AppLocator.IOS(by);
    }

    private final By byIOS;

    private AppLocator(final By byIOS) {
        this.byIOS = byIOS;
    }

    public By getByIOS() {
        return byIOS;
    }

    public By getBy() {
        try {
            return getByIOS();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

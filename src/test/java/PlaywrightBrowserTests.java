import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class PlaywrightBrowserTests {

    static final String artifactPath = "target/testArtifacts/";
    static final String screenshotPath = artifactPath + "screenshots/";
    static final String videoPath = artifactPath + "videos/";

    @Test
    void playwrightChromeTest() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get(videoPath))
                .setColorScheme(ColorScheme.DARK)
        );

        // Open new page
        Page page = context.newPage();
        page.navigate("https://realpython.github.io/fake-jobs/");
        assertEquals("Fake Python", page.title());

        // Click text=Energy engineer Vasquez-Davidson Christopherville, AA 2021-04-08 Learn Apply >> :nth-match(a, 2)
        Page page1 = page.waitForPopup(() -> {
            page.click("text=Energy engineer Vasquez-Davidson Christopherville, AA 2021-04-08 Learn Apply >> :nth-match(a, 2)");
        });
        assertTrue(page1.isVisible("text=Posted: 2021-04-08"));
        // Close page
        page1.close();

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath + "Chrome" + LocalDateTime.now() + ".png")));

        log.debug("Video Path is: {}", page.video().path());
        assertEquals("Fake Python", page.title());

        // Make sure to close, so that videos are saved.
        context.close();
        browser.close();
    }


    @Test
    void playwrightFirefoxTest() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get(videoPath))
                .setColorScheme(ColorScheme.DARK)
        );

        // Open new page
        Page page = context.newPage();
        page.navigate("https://realpython.github.io/fake-jobs/");
        assertEquals("Fake Python", page.title());

        // Click text=Energy engineer Vasquez-Davidson Christopherville, AA 2021-04-08 Learn Apply >> :nth-match(a, 2)
        Page page1 = page.waitForPopup(() -> {
            page.click("text=Energy engineer Vasquez-Davidson Christopherville, AA 2021-04-08 Learn Apply >> :nth-match(a, 2)");
        });
        assertTrue(page1.isVisible("text=Posted: 2021-04-08"));
        // Close page
        page1.close();

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath + "Chrome" + LocalDateTime.now() + ".png")));

        log.debug("Video Path is: {}", page.video().path());
        assertEquals("Fake Python", page.title());

        // Make sure to close, so that videos are saved.
        context.close();
        browser.close();
    }


    @Test
    void wikipediaChromeTest() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        BrowserContext context = browser.newContext();
        // Open new page
        Page page = context.newPage();
        // Go to https://www.wikipedia.org/
        page.navigate("https://www.wikipedia.org/");
        // Click input[name="search"]
        page.click("input[name=\"search\"]");
        // Fill input[name="search"]
        page.fill("input[name=\"search\"]", "cheese");
        // Press Enter
        page.press("input[name=\"search\"]", "Enter");
        assert page.url().equals("https://en.wikipedia.org/wiki/Cheese");
    }
}

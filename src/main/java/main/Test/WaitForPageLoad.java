package main.Test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitForPageLoad {
    public static void waitForPageLoad(WebDriver driver) {

        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(driver1 -> {
            System.out.println("Current Window State       : "
                    + String.valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState")));
            return String
                    .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                    .equals("complete");
        });
    }
}

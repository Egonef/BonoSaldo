package autordoba;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

        System.setProperty("webdriver.gecko.driver", "/home/emilio/Documentos/Proyectos/bonobusCheck/geckodriver");

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true); // activa el modo headless

        WebDriver driver = new FirefoxDriver(options);

        try {
            driver.get("https://aucorsa.es/");

            WebElement numeroTarjeta = driver.findElement(By.name("ncard_selected"));
            numeroTarjeta.sendKeys("2314159175");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            int yPosition = numeroTarjeta.getLocation().getY();
            js.executeScript("window.scrollTo(0, " + (yPosition-300) + ");");

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement cajaTick = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("privacy_content")));
            cajaTick.click();

            WebElement button = driver.findElement(By.cssSelector("button[type='submit'].bricks-button.submit-bricks-button.g-recaptcha"));
            button.click();

            try {
                System.out.println("Buscando información de tu tarjeta...");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement balanceElement = driver.findElement(By.cssSelector(".card-real-balance"));
            String balance = balanceElement.getText();
            System.out.println("Tu saldo es: " + balance);

        } finally {
            driver.quit();
        }
    }
}
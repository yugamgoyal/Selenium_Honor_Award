import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]) throws InterruptedException, FileNotFoundException {
        System.setProperty(
                "webdriver.chrome.driver",
                "/Users/yugamgoyal/Downloads/chromedriver_mac64/chromedriver");
        // Instantiate a ChromeDriver class.
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(ops);

        driver.manage().window().maximize();

        driver.get("https://directory.utexas.edu/advanced.php");


        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);


        WebElement element_enter = driver.findElement(By.xpath("//*[@id=\"eid\"]/label"));
        element_enter.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("EID");

        element_enter = driver.findElement(By.xpath("//*[@id=\"pw\"]/label"));
        element_enter.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("PASSWORD");

        WebElement m=driver.findElement(By.xpath("//*[@id=\"login-button\"]/input"));
        m.click();

        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

        m=driver.findElement(By.xpath("//*[@id=\"trust-browser-button\"]"));
        m.click();

        String newWindow = driver.getWindowHandle();
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL+"t");
        driver.manage().timeouts().implicitlyWait(8000, TimeUnit.SECONDS);
        Thread.sleep(8000);


        File file = new File("/Users/yugamgoyal/Desktop/SeleniumProjectCS/src/main/java/org/example/names.txt");
        Scanner myReader = new Scanner(file);
        int counter = 1;
        int babe_ruth = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] work = data.split(" ");
            data = work.length > 1 ? work[0] + " " + work[work.length - 1] : work[0];
//            data = data.replaceAll(" ", "+");
            String link = "https://directory.utexas.edu/advanced.php";
            String tem= driver.getWindowHandle();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open();");
            ArrayList<String> tabs = new ArrayList (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(counter));
            counter++;
            driver.get(link);
            element_enter = driver.findElement(By.xpath("//*[@id=\"moreinfo\"]/form[1]/label[1]"));
            element_enter.findElement(By.xpath("//*[@id=\"aq_Name\"]")).sendKeys(data);

            element_enter = driver.findElement(By.xpath("//*[@id=\"moreinfo\"]/form[1]/label[2]"));
            element_enter.findElement(By.xpath("//*[@id=\"aq_College_Department\"]")).sendKeys("College of Natural Sciences");

            m=driver.findElement(By.xpath("//*[@id=\"scope\"]"));
            m.click();


            m=driver.findElement(By.xpath("//*[@id=\"scope\"]/option[2]"));
            m.click();


            m=driver.findElement(By.xpath("//*[@id=\"moreinfo\"]/form[1]/div/input"));
            m.click();

            Thread.sleep(100);

            WebElement webElement = driver.findElement(By.xpath("//*[@id=\"moreinfo\"]/h2"));
            String text_l = (webElement.getText());
            String[] babe = text_l.split(" ");
            try {
                int num = Integer.parseInt(babe[2]);
                System.out.println(data + " UNSOUP! ");
            } catch (NumberFormatException ex) {
                webElement = driver.findElement(By.xpath("//*[@id=\"moreinfo\"]/table/tbody/tr[4]/td[2]"));
                String text = (webElement.getText());

                webElement = driver.findElement(By.xpath("//*[@id=\"moreinfo\"]/table/tbody/tr[3]/td[2]"));
                text += " " + (webElement.getText());
                if(text.contains("Computer")) {
                    babe_ruth++;
                    text = text.replace("\n", " ");
                    System.out.println((counter-2) + " " + data + " " + text);
                }
            }

//            driver.close();
//            js.executeScript("window.close();");
            Thread.sleep(400);

            //*[@id="moreinfo"]/table/tbody/tr[3]/td[2]
        }

        System.out.println("Counter: " + babe_ruth);

    }
}

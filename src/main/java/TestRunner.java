import com.github.javafaker.Faker;
import com.opencsv.CSVWriter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TestRunner {
    private static ChromeDriver driver;
    JavascriptExecutor js = (JavascriptExecutor) driver;


    @BeforeAll
    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "//Applications/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://app.yaware.com.ua");
    }

    @Test
    public void writeDataLineByLine() throws IOException {
        String filePath = ("/Users/maria/IdeaProjects/TechTaskMariaOrchynska/src/main/java/employees.csv");
        File file = new File(filePath);
        CSVWriter writer = new CSVWriter(new FileWriter(file), ',',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER);
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[]{"firstname", "lastname", "email", "group_name"});
        for (int i = 0; i < 10; i++) {
            Locale locale = new Locale("en-GB");
            Faker faker = new Faker(locale);
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String group_name = faker.team().name();
            String email = faker.internet().emailAddress();
            data.add(new String[]{firstName, lastName, email, group_name});
        }
        writer.writeAll(data);
        writer.close();
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() != 0);
    }

    @Test
    public void logIn() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement email = driver.findElement(By.id(Locators.emailField));
        WebElement password = driver.findElement(By.id(Locators.passwordField));
        WebElement submitButt = driver.findElement(By.id(Locators.submitButton));
        email.click();
        email.sendKeys(Credentials.emailToLOgIn);
        password.click();
        password.sendKeys(Credentials.passwordToLOgIn);
        submitButt.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.employeesButton)));
        WebElement employeeButt = driver.findElement(By.xpath(Locators.employeesButton));
        employeeButt.click();
        WebElement importEmployees = driver.findElement(By.id(Locators.importEmployees));
        importEmployees.click();
        WebElement uploadFiles = driver.findElement(By.id(Locators.uploadFiles));
        uploadFiles.sendKeys("/Users/maria/IdeaProjects/TechTaskMariaOrchynska/src/main/java/employees.csv");
        WebElement importButt = driver.findElement(By.id(Locators.importButt));
        importButt.click();
        Tools.waits();
        js.executeScript("window.scrollBy(0,5000)");
        WebElement finalImport = driver.findElement(By.id(Locators.finalImport));
        finalImport.click();
        Tools.waits();
        WebElement addEmployee = driver.findElement(By.xpath(Locators.addEmployee));
        addEmployee.click();
        Tools.waits();
        js.executeScript("window.scrollBy(0,5000)");
        WebElement copyLink = driver.findElement(By.xpath(Locators.copyLink));
        copyLink.click();
        WebElement macOsCopy = driver.findElement(By.id(Locators.macOSCopy));
        macOsCopy.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.sendRequest)));
        WebElement sendRequest = driver.findElement(By.xpath(Locators.sendRequest));
        sendRequest.click();
        Tools.waits();
        WebElement deleteRequest = driver.findElement(By.xpath(Locators.deleteRequest));
        deleteRequest.click();
        WebElement delete = driver.findElement(By.id(Locators.deleteButt));
        delete.click();
    }

//    @AfterAll
//    public static void closeDriver() {
//        driver.close();
//    }

}


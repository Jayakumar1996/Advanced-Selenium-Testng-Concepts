package org.ts.Demo;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import baseclass.BaseClass;

public class AppTest extends BaseClass {

 
    @Test(enabled = false)
    public void testFileUpload() {
    	Setup("https://ps.uci.edu/~franklin/doc/file_upload.html");

        By fileInputLocator = By.name("userfile");
        String filePath = constructProjectFilePath("UploadFiles", "Transdetail.pdf");

        // Use the uploadFile method from BaseClass
        uploadFile(fileInputLocator, filePath);
        
        // Verify that the file is uploaded
        String uploadedFile = driver.findElement(fileInputLocator).getAttribute("value");
        Assert.assertTrue(uploadedFile.contains("Transdetail.pdf"), "File upload failed or file not found!");
    }

    @Test(enabled = false)
    public void testTabSwitching() throws Exception {
        Setup("https://dev1.gokeywe.com/");

        driver.findElement(By.xpath("//a[normalize-space()='Login']")).click();
        driver.findElement(By.name("email")).sendKeys("keywetestagent6@gmail.com");
        driver.findElement(By.name("password")).sendKeys("Test@123");
        driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='form1']")));
        inputField.click();
        inputField.clear();
        inputField.sendKeys("Los Gatos, CA, USA");

        WebElement losGatosElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@value='Los Gatos, CA, USA']")));
        losGatosElement.click();

        takeScreenshot("screenshot.png");

        WebElement favoriteIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"sellerProp-module--favorite--283fa\"]//img")));
        favoriteIcon.click();

        switchToNewTab();

        // Print the title of the new tab
        System.out.println("Title of new tab: " + driver.getTitle());

        // Switch back to the original tab
        switchToMainTab();
    }

    @Test(enabled = false)
    public void seleniumPractise() throws InterruptedException {

        browserLaunch("chrome");
        urlLaunch("https://www.selenium.dev/selenium/web/web-form.html");
        deleteAllCookies();
        maximize();
        impWait(60);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Faker faker = new Faker();
        String randomName = faker.name().fullName();

        // Text field
        WebElement textField = driver.findElement(By.id("my-text-id"));
        textField.sendKeys(randomName);

        // Password field
        WebElement passwordField = driver.findElement(By.name("my-password"));
        passwordField.sendKeys(randomName);

        // Textarea field
        WebElement textAreaField = driver.findElement(By.name("my-textarea"));
        textAreaField.sendKeys(randomName);

        // Disabled field
        WebElement disabledField = driver.findElement(By.name("my-disabled"));
        boolean isDisabled = !disabledField.isEnabled();
        System.out.println(isDisabled ? "The field is disabled." : "The field is not disabled.");

        // Read-Only Input
        WebElement readOnlyField = driver.findElement(By.name("my-readonly"));
        String readOnlyAttribute = readOnlyField.getAttribute("readonly");
        boolean isReadOnly = readOnlyAttribute != null && readOnlyAttribute.equals("true");
        System.out.println(isReadOnly ? "The field is read-only." : "The field is not read-only.");

        // Dropdown (select)
        WebElement dropdown = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdown);
        select.selectByVisibleText("One");
        wait.until(ExpectedConditions.textToBePresentInElement(dropdown, "One"));
        select.selectByVisibleText("Two");
        wait.until(ExpectedConditions.textToBePresentInElement(dropdown, "Two"));
        select.selectByVisibleText("Three");

        // Dropdown (Datalist)
        WebElement searchField = driver.findElement(By.name("my-datalist"));
        String[] cities = {"San Francisco", "New York", "Seattle", "Los Angeles", "Chicago"};
        for (String city : cities) {
            searchField.clear();
            searchField.sendKeys(city);
            Thread.sleep(1000);
        }

        // File Input
        String filePath = "C:\\Users\\jayakumart\\Documents\\KeyWe\\Agent images\\Sample.jpg";
        WebElement uploadButton = driver.findElement(By.xpath("//input[@type='file']"));
        uploadButton.sendKeys(filePath);

        // Color Picker
        WebElement colorPicker = driver.findElement(By.name("my-colors"));
        String randomColor = generateRandomColor();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", colorPicker, randomColor);
        Thread.sleep(2000);

        // Date picker
        WebElement datePickerInput = driver.findElement(By.name("my-date"));
        datePickerInput.clear();
        datePickerInput.sendKeys("12/25/2024");

        // Checkbox
        WebElement defCheckbox = driver.findElement(By.id("my-check-2"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", defCheckbox);
        defCheckbox.click();

        // Radio Button
        WebElement defRadio = driver.findElement(By.id("my-radio-2"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", defRadio);
        defRadio.click();
        Thread.sleep(2000);

        // Example range slider
        WebElement rangeSlider = driver.findElement(By.name("my-range"));
        int sliderWidth = rangeSlider.getSize().getWidth();
        Actions actions = new Actions(driver);

        Random random1 = new Random();
        int xOffset = random1.nextInt(sliderWidth) - sliderWidth / 2; // Keep offset within bounds
        actions.clickAndHold(rangeSlider).moveByOffset(xOffset, 0).release().perform();
        Thread.sleep(2000);

        // Submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        driver.quit();
    }
    
    @Test(retryAnalyzer = utils.RetryAnalyzer.class)
    public void datePicker() throws Exception {
        browserLaunch("chrome");
        urlLaunch("https://demoqa.com/automation-practice-form");
        deleteAllCookies();
        maximize();
        impWait(60);
        
        // Scroll the date picker element into view
        WebElement datePick = driver.findElement(By.xpath("//input[@id='dateOfBirthInput']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", datePick);

        // Use explicit wait for date picker to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(datePick));
        
        // Open the date picker
        datePick.click();

        // Select month and year
        WebElement monthSelect = driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']"));
        monthSelect.click();
        monthSelect.sendKeys("September");  // Set the month

        WebElement yearSelect = driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']"));
        yearSelect.click();
        yearSelect.sendKeys("2025");  // Set the year

        // Wait until the calendar is fully updated (month and year changed)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Choose Saturday, September 20th, 2025']")));
        
        // Select a specific day (e.g., 20th September 2025)
        WebElement dateSelect = driver.findElement(By.xpath("//div[@aria-label='Choose Saturday, September 20th, 2025']"));
        dateSelect.click();
    }
  
}


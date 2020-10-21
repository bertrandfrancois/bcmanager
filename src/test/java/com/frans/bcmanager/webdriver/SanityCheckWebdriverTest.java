package com.frans.bcmanager.webdriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SanityCheckWebdriverTest {

    @LocalServerPort
    private int port;
    private WebDriver driver;

    @BeforeEach
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        String base = "http://localhost:" + port;
        driver.get(base);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void sanityCheck() {
        createClient();
        editClient();
        createEstimate();
        addEstimateLines();
        editEstimateLines();
        editEstimate();
        deleteEstimate();
        createServiceInvoice();
        addServiceInvoiceLines();
        editServiceInvoiceLines();
        editServiceInvoice();
        deleteServiceInvoice();
        createProject();
        editProject();
        createProjectInvoice();
        addProjectInvoiceLines();
        editProjectInvoiceLines();
        editProjectInvoice();
        deleteProjectInvoice();
        deleteProject();

    }

    private void createClient() {
        driver.findElement(By.id("clients_link")).click();
        driver.findElement(By.id("addClient")).click();
        driver.findElement(By.id("createClient")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[3]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[4]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[3]/span")).getText());
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Darth");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Vader");
        driver.findElement(By.id("street")).clear();
        driver.findElement(By.id("street")).sendKeys("skyway");
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("123456");
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("Death Star");
        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("darth");
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys("0123456789");
        driver.findElement(By.id("taxNumber")).clear();
        driver.findElement(By.id("taxNumber")).sendKeys("12345");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("size must be between 0 and 5", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[4]/span")).getText());
        assertEquals("Addresse mail incorrecte", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[2]/span")).getText());
        assertEquals("Numéro TVA invalide", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[4]/span")).getText());
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("12345");
        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("darth@darkside.com");
        driver.findElement(By.id("taxNumber")).clear();
        driver.findElement(By.id("taxNumber")).sendKeys("0600834826");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Darth", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("Vader", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("skyway", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("12345", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("Death Star", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("darth@darkside.com", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("0123456789", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[7]/td")).getText());
        assertEquals("0600834826", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[8]/td")).getText());

    }

    private void editClient() {
        driver.findElement(By.id("editClientAction")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("");
        driver.findElement(By.id("street")).clear();
        driver.findElement(By.id("street")).sendKeys("");
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("");
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("");
        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("");
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys("");
        driver.findElement(By.id("taxNumber")).clear();
        driver.findElement(By.id("taxNumber")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[3]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[4]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[3]/span")).getText());
        driver.findElement(By.xpath("//body[@id='page-top']/div/div/div/div[2]/form/div/div/div")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Skywalker");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Luke");
        driver.findElement(By.id("street")).clear();
        driver.findElement(By.id("street")).sendKeys("xwing");
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("123456");
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("tatooine");
        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("luke");
        driver.findElement(By.id("phoneNumber")).clear();
        driver.findElement(By.id("phoneNumber")).sendKeys("123456789");
        driver.findElement(By.id("taxNumber")).clear();
        driver.findElement(By.id("taxNumber")).sendKeys("123456789");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("size must be between 0 and 5", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[4]/span")).getText());
        assertEquals("Addresse mail incorrecte", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[2]/span")).getText());
        assertEquals("Numéro TVA invalide", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div[4]/span")).getText());
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("12345");
        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("luke@skywalker.com");
        driver.findElement(By.id("taxNumber")).clear();
        driver.findElement(By.id("taxNumber")).sendKeys("0600834826");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Skywalker", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("Luke", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("xwing", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("12345", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("tatooine", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("luke@skywalker.com", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("123456789", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[7]/td")).getText());
        assertEquals("0600834826", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[8]/td")).getText());

    }

    private void createEstimate() {
        driver.findElement(By.id("createEstimateAction")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Le numéro de document doit être composé de 7 chiffres (ex: 2020001)", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[3]/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("2020001");
        new Select(driver.findElement(By.id("taxRate"))).selectByVisibleText("6%");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("2020-01-01");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("2020001", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("01/01/2020", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("6%", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[6]/td")).getText());

    }

    private void addEstimateLines(){
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[1]/td/div/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[3]/td/div/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[4]/td/div/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description1");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("m");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("10");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("50");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description1", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[1]")).getText());
        assertEquals("10.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[2]")).getText());
        assertEquals("m", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[3]")).getText());
        assertEquals("$50.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[4]")).getText());
        assertEquals("$500.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[5]")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description2");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("m³");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("5");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("5");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description2", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[1]")).getText());
        assertEquals("5.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[2]")).getText());
        assertEquals("m³", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[3]")).getText());
        assertEquals("$5.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[4]")).getText());
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[5]")).getText());
        assertEquals("$525.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$31.50", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$556.50", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[6]/td")).getText());


    }
    private void editEstimateLines(){
        driver.findElement(By.id("editDocumentLineAction")).click();
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description3");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("ff");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("1");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("125");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description3", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[1]")).getText());
        assertEquals("1.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[2]")).getText());
        assertEquals("ff", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[3]")).getText());
        assertEquals("$125.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[4]")).getText());
        assertEquals("$125.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[5]")).getText());
        assertEquals("$150.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$9.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$159.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[6]/td")).getText());
        driver.findElement(By.id("deleteDocumentLineAction")).click();
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$1.50", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$26.50", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[6]/td")).getText());


    }
    private void editEstimate(){
        driver.findElement(By.id("editEstimateAction")).click();
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Le numéro de document doit être composé de 7 chiffres (ex: 2020001)", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[3]/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("2020002");
        new Select(driver.findElement(By.id("taxRate"))).selectByVisibleText("21%");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("2020-02-01");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("2020002", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("01/02/2020", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("21%", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$5.25", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$30.25", driver.findElement(By.xpath("//*[@id=\"estimateDetail\"]/tbody/tr[6]/td")).getText());


    }
    private void deleteEstimate(){
        driver.findElement(By.id("deleteEstimateAction")).click();
        driver.findElement(By.id("deleteDocumentButton")).click();

    }
    private void createServiceInvoice(){
        driver.findElement(By.id("createServiceAction")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Le numéro de document doit être composé de 7 chiffres (ex: 2020001)", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[3]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[4]/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("2020002");
        new Select(driver.findElement(By.id("taxRate"))).selectByVisibleText("6%");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("2020-01-01");
        driver.findElement(By.id("paymentDate")).clear();
        driver.findElement(By.id("paymentDate")).sendKeys("2020-01-15");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("2020002", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("01/01/2020", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("15/01/2020", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("6%", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[7]/td")).getText());


    }
    private void addServiceInvoiceLines(){
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[1]/td/div/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[3]/td/div/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[4]/td/div/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description1");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("m");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("10");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("50");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description1", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[1]")).getText());
        assertEquals("10.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[2]")).getText());
        assertEquals("m", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[3]")).getText());
        assertEquals("$50.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[4]")).getText());
        assertEquals("$500.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[5]")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description2");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("m³");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("5");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("5");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description2", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[1]")).getText());
        assertEquals("5.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[2]")).getText());
        assertEquals("m³", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[3]")).getText());
        assertEquals("$5.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[4]")).getText());
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[5]")).getText());
        assertEquals("$525.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$31.50", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$556.50", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[7]/td")).getText());


    }
    private void editServiceInvoiceLines(){
        driver.findElement(By.id("editDocumentLineAction")).click();
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description3");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("ff");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("1");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("125");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description3", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[1]")).getText());
        assertEquals("1.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[2]")).getText());
        assertEquals("ff", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[3]")).getText());
        assertEquals("$125.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[4]")).getText());
        assertEquals("$125.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[5]")).getText());
        assertEquals("$150.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$9.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$159.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[7]/td")).getText());
        driver.findElement(By.id("deleteDocumentLineAction")).click();
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$1.50", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$26.50", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[7]/td")).getText());


    }
    private void editServiceInvoice(){
        driver.findElement(By.id("editServiceAction")).click();
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Le numéro de document doit être composé de 7 chiffres (ex: 2020001)", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[3]/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("2020002");
        new Select(driver.findElement(By.id("taxRate"))).selectByVisibleText("21%");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("2020-02-01");
        driver.findElement(By.id("paymentDate")).clear();
        driver.findElement(By.id("paymentDate")).sendKeys("2020-02-10");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("2020002", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("01/02/2020", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("10/02/2020", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("21%", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$5.25", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$30.25", driver.findElement(By.xpath("//*[@id=\"serviceDetail\"]/tbody/tr[7]/td")).getText());


    }
    private void deleteServiceInvoice(){
        driver.findElement(By.id("deleteServiceAction")).click();
        driver.findElement(By.id("deleteDocumentButton")).click();

    }
    private void createProject(){
        driver.findElement(By.id("createProjectAction")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[2]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[3]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div/span")).getText());
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("project description");
        driver.findElement(By.id("street")).clear();
        driver.findElement(By.id("street")).sendKeys("street");
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("123456");
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("city");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("size must be between 0 and 5",
        driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[3]/span")).getText());
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("12345");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("project description", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("street", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("12345", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("city", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[4]/td")).getText());

    }
    private void editProject(){
        driver.findElement(By.id("editProjectAction")).click();
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("");
        driver.findElement(By.id("street")).clear();
        driver.findElement(By.id("street")).sendKeys("");
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("");
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[2]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[3]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[2]/div/span")).getText());
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("other description");
        driver.findElement(By.id("street")).clear();
        driver.findElement(By.id("street")).sendKeys("other street");
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("234567");
        driver.findElement(By.id("city")).clear();
        driver.findElement(By.id("city")).sendKeys("other city");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("size must be between 0 and 5", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div[1]/div[3]/span")).getText());
        driver.findElement(By.id("postCode")).clear();
        driver.findElement(By.id("postCode")).sendKeys("23456");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("other description", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("other street", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("23456", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("other city", driver.findElement(By.xpath("//*[@id=\"projectDetail\"]/tbody/tr[4]/td")).getText());


    }
    private void createProjectInvoice(){
        driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div[2]/div[2]/div/div[1]/a")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Le numéro de document doit être composé de 7 chiffres (ex: 2020001)", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[3]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[4]/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("2020002");
        new Select(driver.findElement(By.id("taxRate"))).selectByVisibleText("6%");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("2020-01-01");
        driver.findElement(By.id("paymentDate")).clear();
        driver.findElement(By.id("paymentDate")).sendKeys("2020-01-15");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("2020002", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("01/01/2020", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("15/01/2020", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("6%", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$0.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[7]/td")).getText());
    }
    private void addProjectInvoiceLines(){
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[1]/td/div/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[3]/td/div/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"clientDetail\"]/tbody/tr[4]/td/div/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description1");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("m");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("10");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("50");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description1", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[1]")).getText());
        assertEquals("10.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[2]")).getText());
        assertEquals("m", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[3]")).getText());
        assertEquals("$50.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[4]")).getText());
        assertEquals("$500.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[5]")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description2");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("m³");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("5");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("5");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description2", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[1]")).getText());
        assertEquals("5.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[2]")).getText());
        assertEquals("m³", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[3]")).getText());
        assertEquals("$5.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[4]")).getText());
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[2]/td[5]")).getText());
        assertEquals("$525.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$31.50", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$556.50", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[7]/td")).getText());


    }

    private void editProjectInvoiceLines(){
        driver.findElement(By.id("editDocumentLineAction")).click();
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("description3");
        new Select(driver.findElement(By.id("unit"))).selectByVisibleText("ff");
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("1");
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("125");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("description3", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[1]")).getText());
        assertEquals("1.000", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[2]")).getText());
        assertEquals("ff", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[3]")).getText());
        assertEquals("$125.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[4]")).getText());
        assertEquals("$125.00", driver.findElement(By.xpath("//*[@id=\"documentLineDetail\"]/tbody/tr[1]/td[5]")).getText());
        assertEquals("$150.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$9.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$159.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[7]/td")).getText());
        driver.findElement(By.id("deleteDocumentLineAction")).click();
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$1.50", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$26.50", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[7]/td")).getText());
    }
    private void editProjectInvoice(){
        driver.findElement(By.id("editProjectInvoiceAction")).click();
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("Le numéro de document doit être composé de 7 chiffres (ex: 2020001)", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[1]/span")).getText());
        assertEquals("champ obligatoire", driver.findElement(By.xpath("//*[@id=\"page-top\"]/div/div[1]/div/div[2]/form/div[1]/div/div[3]/span")).getText());
        driver.findElement(By.id("code")).clear();
        driver.findElement(By.id("code")).sendKeys("2020002");
        new Select(driver.findElement(By.id("taxRate"))).selectByVisibleText("21%");
        driver.findElement(By.id("creationDate")).clear();
        driver.findElement(By.id("creationDate")).sendKeys("2020-02-01");
        driver.findElement(By.id("paymentDate")).clear();
        driver.findElement(By.id("paymentDate")).sendKeys("2020-02-10");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("2020002", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[1]/td")).getText());
        assertEquals("01/02/2020", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[2]/td")).getText());
        assertEquals("10/02/2020", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[3]/td")).getText());
        assertEquals("21%", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[4]/td")).getText());
        assertEquals("$25.00", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[5]/td")).getText());
        assertEquals("$5.25", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[6]/td")).getText());
        assertEquals("$30.25", driver.findElement(By.xpath("//*[@id=\"projectInvoiceDetail\"]/tbody/tr[7]/td")).getText());


    }
    private void deleteProjectInvoice(){
        driver.findElement(By.id("deleteProjectInvoiceAction")).click();
        driver.findElement(By.id("deleteDocumentButton")).click();

    }

    private void deleteProject(){
        driver.findElement(By.id("deleteProjectAction")).click();
        driver.findElement(By.id("deleteProjectButton")).click();
    }
}

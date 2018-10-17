package Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.setConexion;

public class Comunidad extends TestBase {
	
	private WebDriver driver;
	
	//Befores & Afters
	@BeforeClass(alwaysRun=true)
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		loginCommunity(driver);
		driver.switchTo().defaultContent();
		sleep(3000);
		
	}
	
	//@AfterMethod(alwaysRun=true)
	public void backToTheInicio() throws Exception {
		driver.findElement(By.className("slds-container_fluid")).click();
		sleep(10000);
	}

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	@Test (groups = {"Communities","E2E"})
	public void CRM_PRE_Community_Desktop_Pagina_Servicios_Assets(){
		List <WebElement> gest = driver.findElements(By.cssSelector(".via-slds.ta-community-services"));
			boolean aa = false;
			for(WebElement g : gest){
				if(g.getText().toLowerCase().equals("mis gestiones")){
					g.isDisplayed();
					aa = true;
				}
			}
	}
	
}	

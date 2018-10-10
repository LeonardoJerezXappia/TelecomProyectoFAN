package Tests;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SalesBase;
import Pages.setConexion;

public class TestsXappia extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private SalesBase sb;
	
	@BeforeClass (groups = "UAT")
	public void loginUAT() {
		driver = setConexion.setupEze();
		driver.get("https://telecomcrm--uat.cs53.my.salesforce.com");
		sleep(2000);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(2000);
		driver.findElement(By.name("Ecom_User_ID")).sendKeys("uat579805");
 		driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
 		driver.findElement(By.id("loginButton2")).click();
 		sleep(5000);
	}
	
	@BeforeClass (groups = "SIT")
	public void loginSIT() {
		driver = setConexion.setupEze();
		driver.get("https://crm--sit.cs14.my.salesforce.com/");
		sleep(2000);
		driver.findElement(By.id("idp_section_buttons")).click();
		sleep(2000);
		driver.findElement(By.name("Ecom_User_ID")).sendKeys("UAT195528");
 		driver.findElement(By.name("Ecom_Password")).sendKeys("Testa10k");
 		driver.findElement(By.id("loginButton2")).click();
 		sleep(5000);
	}
	
	private void irAConsolaFAN() {
		try {
			cc.cajonDeAplicaciones("Consola FAN");
		} catch (Exception e) {
			driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
			sleep(8000);
		}
	}
	
	public void carrito() {
		List <WebElement> boton = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement x : boton) {
			if(x.getText().toLowerCase().equals("catalogo")) {
				x.click();
				break;
			}
		}
	}
	
	private void irAGestionDeClientes() {
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for (WebElement frame : frames) {
			try {
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText();
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed();
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch (NoSuchElementException e) {
				index++;
				driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if (enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {}
		buscarYClick(driver.findElements(By.tagName("button")), "equals", "gesti\u00f3n de clientes");
	}
	
	
	@BeforeMethod (alwaysRun = true)
	public void before() {
		driver.get("https://telecomcrm--uat.cs53.my.salesforce.com/home/home.jsp");
		cc = new CustomerCare(driver);
		sb = new SalesBase(driver);
	}
	
	//@AfterMethod (alwaysRun = true)
	public void quit() {
		driver.quit();
	}
	
	
	@Test (groups = "UAT")
	public void Gestiones_Del_Panel_Izquierdo_En_Consola_FAN_En_Ambiente_UAT() {
		irAConsolaFAN();
		driver.switchTo().frame(cambioFrame(driver, By.className("slds-spinner_container")));
		WebElement gestiones = driver.findElement(By.className("slds-spinner_container"));
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0," + gestiones.getLocation().y + ")");
		Assert.assertTrue(!gestiones.getText().isEmpty());
	}
	
	@Test (groups = "SIT")
	public void SmokeTest_Tiempo_De_Carga_De_Consola_FAN_En_Ambiente_SIT() {
		Date start = new Date();
		irAConsolaFAN();
		Date end = new Date();
		long startTime = start.getTime();
		long endTime = end.getTime();
		long tiempoTotal = endTime - startTime;
		tiempoTotal = tiempoTotal / 1000;
		Assert.assertTrue(tiempoTotal < 55);
	}
	
	@Test (groups = "UAT")
	public void superposicion() {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22222000");
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		WebElement btnComprarInternet = null;
		List<WebElement> btn = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		for (int i=0; i<btn.size(); i++) {
			if (btn.get(i).getText().toLowerCase().contains("comprar internet"))
				btnComprarInternet = btn.get(i);
		}
		btnComprarInternet.click();
		sleep(25000);
		driver.switchTo().defaultContent();
		if (driver.findElements(By.cssSelector(".x-layout-mini.x-layout-mini-east.x-layout-mini-custom-logo")).size() == 0)
			driver.findElement(By.cssSelector(".x-layout-mini.x-layout-mini-east.x-layout-mini-custom-logo")).click();
		WebElement planConTarjeta = null;
		driver.switchTo().frame(cambioFrame(driver, By.className("cpq-product-cart-order")));
		List<WebElement> plan = driver.findElements(By.className("cpq-product-name"));
		for (int i=0; i<plan.size(); i++) {
			if (plan.get(i).getText().toLowerCase().contains("plan con tarjeta repro"))
				planConTarjeta = plan.get(i);
		}
		Assert.assertTrue(planConTarjeta.isEnabled());
	}
	@Test (groups = "UAT")
	public void Gestion_De_Verificacion_De_Dos_Idiomas_En_El_Carrito() {
		SalesBase sb = new SalesBase(driver);
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22222001");
		carrito();
		sleep(2000);
		WebElement texto = driver.findElement(By.id("cpq-product-items"));
		texto.getText();
		System.out.println(texto);
		boolean text = false;
		for (WebElement x : driver.findElements(By.id("cpq-product-items"))) {
			if(x.getText().toLowerCase().equals("Producto")) {
				text = true;
			}
		}
		Assert.assertTrue(text);	
	}
	
	@Test (groups = {"UAT"}, dataProvider="NumerosAmigos")
	public void TX0001_UAT_FF_No_Acepta_Numeros_De_Personal(String sDNI, String sLinea, String sNumeroVOZ, String sNumeroSMS) {
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("N\u00fameros Gratis");
		
		sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-col--padded.slds-size--1-of-2")));
		List<WebElement> wNumerosAmigos = driver.findElements(By.cssSelector(".slds-col--padded.slds-size--1-of-2"));
		Marketing mMarketing = new Marketing(driver);
		int iIndice = mMarketing.numerosAmigos(sNumeroVOZ, sNumeroSMS);
		switch (iIndice) {
			case 0:
				wNumerosAmigos.get(0).findElement(By.tagName("input")).sendKeys(sNumeroVOZ);
				break;
			case 1:
				wNumerosAmigos.get(1).findElement(By.tagName("input")).sendKeys(sNumeroSMS);
				break;
			default:
				Assert.assertTrue(false);
		}
		sleep(5000);
		WebElement wBox = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-tel.ng-scope.ng-dirty.ng-valid-mask.ng-valid.ng-valid-parse.ng-valid-required.ng-valid-minlength.ng-valid-maxlength")).findElement(By.className("error"));
		Assert.assertFalse(wBox.getText().equalsIgnoreCase("la linea no pertenece a Telecom, verifica el n\u00famero."));
	}
	@Test (groups = "UAT")
	public void Gestion_De_Verificacion_De_Dos_Idiomas_En_El_Carrito() {
		SalesBase sb = new SalesBase(driver);
		loginUAT();
		irAConsolaFAN();
		sb.cerrarPestaniaGestion(driver);
		irAGestionDeClientes();
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", "22222001");
		carrito();
		sleep(2000);
		boolean text = false;
		List <WebElement> texto = driver.findElements(By.id("cpq-product-items"));
		for (WebElement x : texto) {
			if(x.getText().toLowerCase().equals("productos")) {
				text = true;
			}
			Assert.assertTrue(text);
		}
	}
}
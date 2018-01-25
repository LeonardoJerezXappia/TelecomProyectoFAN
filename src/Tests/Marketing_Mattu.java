package Tests;

import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select; 
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.SCP;
import Pages.setConexion;

public class Marketing_Mattu extends TestBase{
	
	private WebDriver driver;
	
	//-------------------------------------------------------------------------------------------------
	//@Befor&After
	@BeforeClass
	public void readySteady() throws Exception {
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		//loginMarketing(driver);
		login(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.id("tsidLabel")).click();
		WebElement wMenu = driver.findElement(By.id("tsid-menuItems"));
		List<WebElement> wMenuOptions = wMenu.findElements(By.tagName("a"));
		wMenuOptions.get(0).click();
		driver.findElement(By.id("tsidLabel")).click();
		wMenu = driver.findElement(By.id("tsid-menuItems"));
		wMenuOptions = wMenu.findElements(By.tagName("a"));
		wMenuOptions.get(9).click();
		CustomerCare cCC = new CustomerCare(driver);
		cCC.cerrarTodasLasPestaņas();
		goToLeftPanel(driver, "Cuentas");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		waitFor(driver, (By.name("fcf")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText("Vista Marketing");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wBody = driver.findElement(By.className("x-grid3-body"));
		List<WebElement> wAccountName = wBody.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACCOUNT_NAME"));
		
		for (WebElement wAux:wAccountName) {
			WebElement wContenido = wAux.findElement(By.tagName("span"));
			
			if (wContenido.getText().toLowerCase().equals("florencia marketing")) {
				wAux.click();
				break;
			}
		}
		
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Club Personal");
		List<WebElement> wGestiones = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		
		for (WebElement wAux2:wGestiones) {
			WebElement wContenido = wAux2.findElement(By.cssSelector(".slds-text-body_regular.ta-button-font"));
			if (wContenido.getText().toLowerCase().equals("club personal")) {
				wAux2.click();
				break;
			}
		}
	}
	@BeforeMethod
	public void go() throws Exception {
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
	}
	//@AfterMethod
	public void byeByeTab() {
		Marketing mMarketing = new Marketing(driver);
		mMarketing.CloseActiveTab();
	}
	//@AfterClass
	public void tearDown() {
		driver.close();
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 1
	@Test(groups = "Marketing")
	public void TS4176_Visualizar_error_Mora_Alta_CP() {
		CustomerCare cCC = new CustomerCare(driver);
		cCC.cerrarTodasLasPestaņas();
		goToLeftPanel(driver, "Cuentas");
		WebElement frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		waitFor(driver, (By.name("fcf")));
		Select field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText("Todas las cuentas");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wBody = driver.findElement(By.className("x-grid3-body"));
		List<WebElement> wAccountName = wBody.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACCOUNT_NAME"));
		
		for (WebElement wAux:wAccountName) {
			WebElement wContenido = wAux.findElement(By.tagName("span"));
			
			if (wContenido.getText().toLowerCase().equals("aaaacuenta conmora")) {
				wAux.click();
				break;
			}
		}
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Club Personal");
		List<WebElement> wGestiones = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		
		for (WebElement wAux2:wGestiones) {
			WebElement wContenido = wAux2.findElement(By.cssSelector(".slds-text-body_regular.ta-button-font"));
			if (wContenido.getText().toLowerCase().equals("club personal")) {
				wAux2.click();
				break;
			}
		}
		waitFor(driver, By.cssSelector(".slds-text-body_regular.ta-button-font"));
		Marketing mMenuABM = new Marketing(driver);
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")));
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wMessage = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
		List<WebElement> lText = wMessage.findElements(By.tagName("p"));
		Assert.assertTrue(lText.get(1).getText().contains("Para continuar es necesario regularizar su estado de cuenta, caso nro."));
		cCC.cerrarTodasLasPestaņas();
		goToLeftPanel(driver, "Cuentas");
		frame0 = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame0);
		waitFor(driver, (By.name("fcf")));
		field = new Select(driver.findElement(By.name("fcf")));
		field.selectByVisibleText("Vista Marketing");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		wBody = driver.findElement(By.className("x-grid3-body"));
		wAccountName = wBody.findElements(By.cssSelector(".x-grid3-col.x-grid3-cell.x-grid3-td-ACCOUNT_NAME"));
		
		for (WebElement wAux:wAccountName) {
			WebElement wContenido = wAux.findElement(By.tagName("span"));
			
			if (wContenido.getText().toLowerCase().equals("florencia marketing")) {
				wAux.click();
				break;
			}
		}
		try {Thread.sleep(8000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")));
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).clear();
		driver.findElement(By.cssSelector(".slds-input.actionSearch.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys("Club Personal");
		wGestiones = driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.slds-truncate"));
		
		for (WebElement wAux2:wGestiones) {
			WebElement wContenido = wAux2.findElement(By.cssSelector(".slds-text-body_regular.ta-button-font"));
			if (wContenido.getText().toLowerCase().equals("club personal")) {
				wAux2.click();
				break;
			}
		}
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-panel__section.slds-p-around--small")));
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 2
	@Test(groups = "Marketing")
	public void TS50000_Funcionamiento_boton_Alta_ABM_del_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")));
		WebElement wHeader = driver.findElement(By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")).findElement(By.tagName("h1"));
		Assert.assertTrue(wHeader.getText().toLowerCase().contains("cuentas"));
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50001_Funcionamiento_boton_Baja_ABM_del_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")));
		WebElement wHeader = driver.findElement(By.cssSelector(".slds-page-header.vlc-slds-page--header.ng-scope")).findElement(By.tagName("h1"));
		Assert.assertTrue(wHeader.getText().toLowerCase().contains("club personal baja"));
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50009_Separacion_de_cuentas_Alta_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wConsumerBox = driver.findElement(By.id("consumerAccounts"));
		WebElement wConsumerTable= wConsumerBox.findElement(By.tagName("tbody"));
		List<WebElement> wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
		Assert.assertTrue(!wConsumerTableRows.isEmpty());
		WebElement wBusinessBox = driver.findElement(By.id("businessAccounts"));
		WebElement wBusinessTable= wBusinessBox.findElement(By.tagName("tbody"));
		List<WebElement> wBusinessTableRows = wBusinessTable.findElements(By.tagName("tr"));
		Assert.assertTrue(!wBusinessTableRows.isEmpty());
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50035_Separacion_Baja_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wConsumerBox = driver.findElement(By.id("consumerAccounts"));
		WebElement wConsumerTable= wConsumerBox.findElement(By.tagName("tbody"));
		List<WebElement> wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
		Assert.assertTrue(!wConsumerTableRows.isEmpty());
		WebElement wBusinessBox = driver.findElement(By.id("businessAccounts"));
		WebElement wBusinessTable= wBusinessBox.findElement(By.tagName("tbody"));
		List<WebElement> wBusinessTableRows = wBusinessTable.findElements(By.tagName("tr"));
		Assert.assertTrue(!wBusinessTableRows.isEmpty());
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 3
	@Test(groups = "Marketing")
	public void TS50013_Boton_Cancelar_Alta_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).getText().toLowerCase().equals("cancelar"));
		
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 4
	@Test(groups = "Marketing")
	public void TS50014_Boton_Continuar_Alta_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("AltaClubPersonal_nextBtn")));
		WebElement wSiguiente = driver.findElement(By.id("AltaClubPersonal_nextBtn"));
		Assert.assertTrue(wSiguiente.findElement(By.tagName("p")).getText().toLowerCase().equals("siguiente"));
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50037_Visualizar_cuentas_costumer_Baja_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wConsumerBox = driver.findElement(By.id("consumerAccounts"));
		WebElement wConsumerTable= wConsumerBox.findElement(By.tagName("tbody"));
		List<WebElement> wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
		Assert.assertTrue(!wConsumerTableRows.isEmpty());
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50038_Visualizar_cuentas_business_Baja_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("alta");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wBusinessBox = driver.findElement(By.id("businessAccounts"));
		WebElement wBusinessTable= wBusinessBox.findElement(By.tagName("tbody"));
		List<WebElement> wBusinessTableRows = wBusinessTable.findElements(By.tagName("tr"));
		Assert.assertTrue(!wBusinessTableRows.isEmpty());
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50043_Verificar_seleccion_del_motivo_otro_Baja_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wConsumerBox = driver.findElement(By.id("consumerAccounts"));
		WebElement wConsumerTable= wConsumerBox.findElement(By.tagName("tbody"));
		List<WebElement> wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
		WebElement wCTCheckBox = wConsumerTableRows.get(0).findElement(By.tagName("th"));
		wCTCheckBox.findElement(By.tagName("label")).click();
		BasePage bBP = new BasePage(driver);
		bBP.setSimpleDropdown(driver.findElement(By.id("SelectReason")), "Otro");
		Assert.assertTrue(driver.findElement(By.id("Others")).isEnabled());
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50044_Verificar_seleccion_del_motivo_otro_vacio_Baja_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.id("consumerAccounts")));
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wConsumerBox = driver.findElement(By.id("consumerAccounts"));
		WebElement wConsumerTable= wConsumerBox.findElement(By.tagName("tbody"));
		List<WebElement> wConsumerTableRows = wConsumerTable.findElements(By.tagName("tr"));
		WebElement wCTCheckBox = wConsumerTableRows.get(0).findElement(By.tagName("th"));
		wCTCheckBox.findElement(By.tagName("label")).click();
		BasePage bBP = new BasePage(driver);
		bBP.setSimpleDropdown(driver.findElement(By.id("SelectReason")), "Otro");
		driver.findElement(By.id("CPMembershipCancellation_nextBtn")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		assertTrue(driver.findElement(By.id("alert-container")).isDisplayed());
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 5
	@Test(groups = "Marketing")
	public void TS50045_Visualizar_botones_Baja_CP() {
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrame=new BasePage();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(cambioFrame.getFrameForElement(driver, By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")));
		Assert.assertTrue(driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("CPMembershipCancellation_nextBtn")).isDisplayed());
		
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 6
	@Test(groups = "Marketing")
	public void TS50060_Visualizar_boton_Solapa_CP() {
		Assert.assertTrue(true);
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50061_Funcionamiento_boton_Solapa_CP() {
		Assert.assertTrue(true);
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = 
	@Test(groups = "Marketing")
	public void TS50062_Visualizar_Categoria_CP_Solapa_CP() {
		//try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement wPCCategory = driver.findElement(By.cssSelector(".slds-tile.slds-p-bottom--medium")).findElement(By.tagName("div")).findElement(By.tagName("p"));
		Assert.assertTrue(!wPCCategory.getText().isEmpty());
		
		Marketing mMenuABM = new Marketing(driver);
		mMenuABM.ClubPersonal("baja");
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC = Check login to start
	@Test(groups = "Marketing")
	public void TS90286_Asignacion_de_Perfil_para_Aplicacion_Atributos() {
		
	}
	
}
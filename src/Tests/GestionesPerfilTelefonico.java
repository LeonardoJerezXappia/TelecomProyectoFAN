package Tests;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;

public class GestionesPerfilTelefonico extends TestBase{

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	List <String> datosOrden =new ArrayList<String>();
	String imagen;
	String detalles;
	
	
	@BeforeClass(alwaysRun=true)
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginTelefonico(driver);
		sleep(22000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(18000);
		driver.switchTo().defaultContent();
		sleep(3000);		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		sleep(10000);
		goToLeftPanel2(driver, "Inicio");
		sleep(15000);
		try {
			sb.cerrarPestaniaGestion(driver);
		} catch (Exception ex1) {}
		Accounts accountPage = new Accounts(driver);
		driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				System.out.println("aca");
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			}catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(accountPage.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
				driver.switchTo().frame(frames.get(index));
		}catch(ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame 2.");			
		}
		List<WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		
		sleep(25000);
	}

	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		datosOrden.add(detalles);
		guardarListaTxt(datosOrden);
		datosOrden.clear();
		tomarCaptura(driver,imagen);
	}

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E"}, dataProvider = "RecargaTC")  //Error despues de ingresar la tarjeta
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String cDNI, String cMonto, String cLinea, String cBanco, String cTarjeta, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular, String cPromo, String cCuotas) throws AWTException {
		imagen= "TS134332";
		detalles = null;
		detalles = imagen+"-Recarga-DNI:"+cDNI;
		if(cMonto.length() >= 4) {
			cMonto = cMonto.substring(0, cMonto.length()-1);
		}
		if(cVenceMes.length() >= 2) {
			cVenceMes = cVenceMes.substring(0, cVenceMes.length()-1);
		}
		if(cVenceAno.length() >= 5) {
			cVenceAno = cVenceAno.substring(0, cVenceAno.length()-1);
		}
		if(cCodSeg.length() >= 5) {
			cCodSeg = cCodSeg.substring(0, cCodSeg.length()-1);
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(cMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(20000);
		String sOrden = cc.obtenerOrden3(driver);
		detalles +="-Orden:"+sOrden;
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		sleep(1000);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);				
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		buscarYClick(driver.findElements(By.id("InvoicePreview_nextBtn")), "equals", "siguiente");
		List <WebElement> exis = driver.findElements(By.id("GeneralMessageDesing"));
		boolean a = false;
		for(WebElement x : exis) {
			if(x.getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
			Assert.assertTrue(a);
		}
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+="-Monto:"+orden.split("-")[2]+"-Prefactura:"+orden.split("-")[1];
		//datosOrden.add("Recargas" + orden + " de cuenta "+accid+" con DNI: " + cDNI);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.PagoEnCaja("1003", accid, "2001", orden.split("-")[2], orden.split("-")[1],driver);
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		/*cc.obtenerOrdenMontoyTN(driver, "Recarga");
		sleep(10000);*/
		driver.switchTo().frame(cambioFrame(driver, By.id("Status_ilecell")));
		Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("activada"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacioDeCuota","E2E"}, dataProvider="RenovacionCuotaSinSaldo")
	public void TS130067_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Sin_Credito(String sDNI, String sLinea) {
		imagen = "TS130067";
		detalles = null;
		detalles = imagen+"-Renovacion de cuota-DNI:"+sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
			driver.findElement(By.id("combosMegas")).findElements(By.className("slds-checkbox")).get(2).click();
		}
		catch (Exception ex) {
			//Allways Empty
		}
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElement(By.tagName("p")).getText().equalsIgnoreCase("saldo insuficiente"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota","E2E"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sDNI, String sLinea) {
		imagen = "TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito";
		detalles = null;
		detalles = imagen+"-DNI:"+sDNI;
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(5000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		driver.findElement(By.id("combosMegas")).findElements(By.className("slds-checkbox")).get(0).click();
		sleep(2000);
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("saldo")){
				UnP.click();
				break;
			}
		}		
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().equalsIgnoreCase("la compra se realiz\u00f3 exitosamente");
		cCC.obligarclick(driver.findElement(By.id("AltaHuawei_nextBtn")));
		sleep(12000);
		/*driver.navigate().refresh();
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("story-container")));
			driver.findElement(By.className("story-container")).findElement(By.cssSelector(".slds-text-body.regular.story-title"));
		} catch(Exception ex1) {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".x-layout-mini.x-layout-mini-west")));
			driver.findElement(By.cssSelector(".x-layout-mini.x-layout-mini-west")).click();
			sleep(4000);*/
		String sOrder = cCC.obtenerOrden(driver, "Reseteo de Cuota");
		System.out.println("Orden"+sOrder);
		datosOrden.add("Operacion: Renovacion Cuota, Orden: "+sOrder+", DNI: "+sDNI+", Linea: "+sLinea);			
		System.out.println("Order: " + sOrder + " Fin");
		//Assert.assertTrue(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).getText().contains("La orden se realiz\u00f3 con \u00e9xito!"));
	}
	
	// no existe Pack, se probo el caso con otro
	@Test (groups= {"GestionesPerfilTelefonico","E2E"},priority=1, dataProvider="VentaPacks")
	public void TS123314_CRM_Movil_REPRO_Venta_de_Pack_40_Pesos_Exclusivo_Para_Vos_Descuento_De_Saldo_Telefonico(String sDNI, String sCuenta, String sNumeroDeCuenta, String sLinea, String sVentaPack){
	imagen = "TS123314";
	detalles = null;
	detalles = imagen+"-Venta de pack-DNI:"+sDNI;
	SalesBase sale = new SalesBase(driver);
	BasePage cambioFrameByID=new BasePage();
	CustomerCare cCC = new CustomerCare(driver);
	PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
	sleep(8000);
	sale.BuscarCuenta("DNI", sDNI);
	String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
	System.out.println("id "+accid);
	detalles+="-Cuenta:"+accid;
	pagePTelefo.buscarAssert();
	cCC.seleccionarCardPornumeroLinea(sLinea, driver);
	pagePTelefo.comprarPack();
	pagePTelefo.agregarPack(sVentaPack);
	pagePTelefo.tipoDePago("descuento de saldo");
	String orden = cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
	System.out.println("orden = "+orden);
	datosOrden.add("Recargas" + orden + " de cuenta "+accid+" con DNI: " + sDNI);
	sleep(5000);
	driver.navigate().refresh();
	sleep(10000);
	cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
	sleep(10000);
	driver.switchTo().frame(cambioFrame(driver, By.id("Status_ilecell")));
	Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("activada"));
	String sOrder = cCC.obtenerOrden(driver,"Compra de Pack");
	System.out.println("Orden: "+sOrder);
	datosOrden.add("Operacion: Compra de Pack, Orden: "+sOrder+", Cuenta: "+sCuenta+", DNI: "+sDNI+", Linea: "+sLinea);	
	System.out.println("Order: " + sOrder + " Fin");
	}
	
	@Test(groups = { "GestionesPerfilTelefonico", "E2E" }, priority = 1, dataProvider = "CambioSimCardTelef")
	public void TSCambioSimCardTelef(String sDNI, String sLinea,String cEntrega, String cProvincia, String cLocalidad, String cPuntodeVenta, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI,String cDNITarjeta, String cTitular) throws AWTException {
		imagen = "TSCambioSimCard";
		detalles = null;
		detalles = imagen+"-Telef-DNI:"+sDNI;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID = new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(8000);
		sale.BuscarCuenta("DNI", sDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles+="-Cuenta:"+accid;
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(25000);
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		sleep(3000);
		cCC.irAGestionEnCard("Cambio SimCard");
		pagePTelefo.mododeEntrega(driver, cEntrega, cProvincia, cLocalidad, cPuntodeVenta);
		sleep(12000);
		pagePTelefo.getResumenOrdenCompra().click();
		String sOrden = cCC.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		
		
	
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals","tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
		
		cCC.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
		sleep(15000);
		try {
			driver.findElement(By.id("Step_Error_Huawei_S029_nextBtn")).click();
			System.out.println("Error en prefactura huawei");
		}catch(Exception ex1) {}
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		String invoice = cCC.obtenerMontoyTNparaAlta(driver, sOrden);
		System.out.println(invoice);
		sleep(10000);
		detalles+="Monto:"+invoice.split("-")[1]+"-Prefactura:"+invoice.split("-")[0];
		//datosOrden.add("Cambio sim card Agente- Cuenta: "+accid+"Invoice: "+invoice.split("-")[0]);
		CBS_Mattu invoSer = new CBS_Mattu();
		Assert.assertTrue(invoSer.PagoEnCaja("1003", accid, "2001", invoice.split("-")[1], invoice.split("-")[0],driver));
		driver.navigate().refresh();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Status_ilecell")));
		Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("activada"));
	}
	
	
	@Test (groups= {"GestionesPerfilTelefonico","E2E"},priority=1, dataProvider="ventaPack")
	public void TS123157_CRM_Movil_REPRO_Venta_De_Pack_50_Min_Y_50_SMS_X_7_Dias_Factura_De_Venta_TC_Telefonico(String sDNI, String sLinea, String sventaPack, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) throws InterruptedException, AWTException{
	imagen = "TS123157";
	detalles = null;
	detalles = imagen+"-Venta de pack-DNI:"+sDNI;
	SalesBase sale = new SalesBase(driver);
	BasePage cambioFrameByID=new BasePage();
	CustomerCare cCC = new CustomerCare(driver);
	PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
	sleep(8000);
	sale.BuscarCuenta("DNI", sDNI);
	String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
	System.out.println("id "+accid);
	detalles +="-Cuenta:"+accid;
	pagePTelefo.buscarAssert();
	cCC.seleccionarCardPornumeroLinea(sLinea, driver);
	pagePTelefo.comprarPack();
	pagePTelefo.PackCombinado(sventaPack);
	pagePTelefo.tipoDePago("en factura de venta");
	pagePTelefo.getTipodepago().click();
	sleep(12000);
	String sOrden = cc.obtenerOrden2(driver);
	detalles+="-Orden:"+sOrden;
	pagePTelefo.getSimulaciondeFactura().click();
	sleep(12000);
	buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
	sleep(8000);
	selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
	selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
	selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
	selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
	driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
	selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
	selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
	driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
	selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
	driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
	driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
	pagePTelefo.getMediodePago().click();
	sleep(45000);
	pagePTelefo.getOrdenSeRealizoConExito().click();// No se puede procesr (Ups, hay problemas para procesar su pago.)
	sleep(10000);
	String orden = cCC.obtenerTNyMonto2(driver, sOrden);
	detalles+="-Monto:"+orden.split("-")[1]+"-Prefactura:"+orden.split("-")[0];
	CBS_Mattu invoSer = new CBS_Mattu();
	Assert.assertTrue(invoSer.PagoEnCaja("1003", accid, "2001", orden.split("-")[1], orden.split("-")[0],driver));
	
	//cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
	sleep(10000);
	driver.switchTo().frame(cambioFrame(driver, By.id("Status_ilecell")));
	Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("iniciada"));
	String sOrder = cCC.obtenerOrden(driver,"Compra de Pack");
	datosOrden.add("Operacion: Compra de Pack, Orden: "+sOrder);	
	System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrder + "Cuenta: "+ accid + "Fin");
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "Ajustes", "E2E"},dataProvider = "CuentaAjustesPRE")  //Rompe porque no sale el mensaje de gestion exitosa, sale el perfil no configurado correctamente
	public void TS121333_CRM_Movil_PRE_Ajuste_total_de_comprobantes_FAN_Front_Telefonico(String cDNI) {
		imagen = "TS121333";
		detalles = null;
		detalles = imagen+"-Ajuste-DNI:"+cDNI;
		boolean gest = false;
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		cc.irAGestion("inconvenientes");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step-TipodeAjuste_nextBtn")));
		selectByText(driver.findElement(By.id("CboConcepto")), "CREDITO POSPAGO");
		selectByText(driver.findElement(By.id("CboItem")), "Minutos/SMS");
		selectByText(driver.findElement(By.id("CboMotivo")), "Error/omisi\u00f3n/demora gesti\u00f3n");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("Step-TipodeAjuste_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "cuenta:");
		driver.findElement(By.id("Step1-SelectBillingAccount_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si, ajustar");
		driver.findElement(By.id("Step-VerifyPreviousAdjustments_nextBtn")).click();
		sleep(7000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "nota de cr\u00e9dito");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		sleep(3000);
		driver.findElements(By.className("slds-cell-shrink")).get(0).click();
		driver.findElement(By.id("Step-AjusteNivelCuenta_nextBtn")).click();
		sleep(7000);
		try {
			driver.findElement(By.id("Step-Summary_nextBtn")).click();
			sleep(7000);
			List <WebElement> element = driver.findElements(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope"));
			for (WebElement x : element) {
				if (x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
					gest = true;
				}
			}
			Assert.assertTrue(gest);
		}catch(Exception ex1) {
			String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
			orden = orden.substring(orden.length()-8);
			detalles+="-Orden:"+orden;
			Assert.assertTrue(false);
		}
	}
	
	@Test (groups= {"GestionesPerfilTelefonico", "ModificacionDeDatos", "E2E"},  dataProvider = "CuentaModificacionDeDatos")
	public void TS134835_CRM_Movil_PRE_Modificacion_de_datos_Actualizar_los_datos_del_cliente_completos_FAN_Front_Telefonico(String cDNI) {
		imagen = "TS134835";
		detalles = null;
		detalles = imagen+"-Modificacion de datos-DNI:"+cDNI;
		String nuevoNombre = "Otro";
		String nuevoApellido = "Apellido";
		String nuevoNacimiento = "10/10/1982";
		String nuevoMail = "maildetest@gmail.com";
		String nuevoPhone = "3574409239";
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		String nombre = driver.findElement(By.id("FirstName")).getAttribute("value");
		String apellido = driver.findElement(By.id("LastName")).getAttribute("value");
		String fechaNacimiento = driver.findElement(By.id("Birthdate")).getAttribute("value");
		String mail = driver.findElement(By.id("Email")).getAttribute("value");
		String phone = driver.findElement(By.id("MobilePhone")).getAttribute("value");
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nuevoNombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(nuevoApellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(nuevoNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(nuevoMail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(nuevoPhone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(5000);
		Marketing mk = new Marketing(driver);
		mk.closeActiveTab();
		driver.switchTo().frame(cambioFrame(driver, By.className("profile-box")));
		driver.findElements(By.className("profile-edit")).get(0).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DocumentNumber")));
		Assert.assertTrue(driver.findElement(By.id("FirstName")).getAttribute("value").equals(nuevoNombre));
		Assert.assertTrue(driver.findElement(By.id("LastName")).getAttribute("value").equals(nuevoApellido));
		Assert.assertTrue(driver.findElement(By.id("Birthdate")).getAttribute("value").equals(nuevoNacimiento));
		Assert.assertTrue(driver.findElement(By.id("Email")).getAttribute("value").equals(nuevoMail));
		Assert.assertTrue(driver.findElement(By.id("MobilePhone")).getAttribute("value").equals(nuevoPhone));
		Assert.assertTrue(driver.findElement(By.id("DocumentType")).getAttribute("disabled").equals("true"));
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys(nombre);
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys(apellido);
		driver.findElement(By.id("Birthdate")).clear();
		driver.findElement(By.id("Birthdate")).sendKeys(fechaNacimiento);
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys(mail);
		driver.findElement(By.id("MobilePhone")).clear();
		driver.findElement(By.id("MobilePhone")).sendKeys(phone);
		driver.findElement(By.id("ClientInformation_nextBtn")).click();
		sleep(8000);
		Assert.assertTrue(driver.findElement(By.className("ta-care-omniscript-done")).findElement(By.className("ng-binding")).getText().equalsIgnoreCase("Se realizaron correctamente las modificaciones."));
		String orden = driver.findElement(By.cssSelector(".vlc-slds-inline-control__label.ng-binding")).getText();
		orden = orden.substring(orden.length()-9, orden.length()-1);
		detalles +="-Orden:"+orden;
		
	}
}
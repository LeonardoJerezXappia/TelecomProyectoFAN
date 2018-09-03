package Tests;

import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CBS;
import Pages.setConexion;

public class CBS_Mattu extends TestBase {
	
	private WebDriver driver;
	
	//-------------------------------------------------------------------------------------------------
	//@Befor&After
	//@BeforeClass(alwaysRun=true)
	public void readySteady() throws Exception {
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginCBS(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC =
	@Test
	public void openPage() {
		String sEndPoint = "Pago en Caja";
		String sPaymentChannelID = "1003";
		String sAccountKey = "9900000326610001";
		String sPaymentMethod = "2001";
		String sAmount = "123005600";
		String sInvoiceno = "20180827000000056800";
		String sPaymentSerialNo = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_Request_ServicioWeb_Validador(sSCS.callSoapWebService(cCBS.sRequest(sPaymentSerialNo, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sInvoiceno), sEndPoint));
		System.out.println("sResponse: " + sResponse);
	}
	
	public boolean PagoEnCaja(String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sInvoiceno) {
		String sEndPoint = "Pago en Caja";
		String sPaymentSerialNo = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_Request_ServicioWeb_Validador(sSCS.callSoapWebService(cCBS.sRequest(sPaymentSerialNo, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sInvoiceno), sEndPoint));
		System.out.println("sResponse: " + sResponse);
		return(cCBS.sCBS_Request_Validador(sResponse));
	}
	
	@Test
	public void openPage2(String sOrder) {
		String sEndPoint = "Pago Simulado";
		//String sOrder = "00079437";
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = sSCS.callSoapWebService(cCBS.sRequestByOrder(sOrder), sEndPoint);
		System.out.println("sResponse: " + sResponse);
	}
	
	@Test
	public void openPage3() {
		String sEndPoint = "Alta de Linea";
		String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		String sImsi = "";
		String sICCD = "";
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_Request_Validador_Alta_Linea(sSCS.callSoapWebService(cCBS.sRequestByLinea(sLinea, sMessageSeq), sEndPoint), sLinea, sImsi, sICCD);
		System.out.println("Respuesta: " + sResponse);
	}
	
	@Test
	public void openPage4() {
		String sEndPoint = "";
		String sPaymentChannelID = "";
		String sAccountKey = "";
		String sPaymentMethod = "";
		String sAmount = "";
		String sInvoiceno = "";
		String sMessageSeq = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		String sAccountNumber = "";
		String sAccountName = "";
		String sExpirationDate = "";
		String sCVV = "";
		String sCardHolderName = "";
		String sCardHolderNumber = "";
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_TC_Request_Validador(sSCS.callSoapWebService(cCBS.sRequestByTC(sMessageSeq, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sAccountNumber, sAccountName, sExpirationDate, sCVV, sInvoiceno, sCardHolderName, sCardHolderNumber), sEndPoint));
		cCBS.sCBS_TC_Request_Validador(sResponse);
	}
}
package Tests;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.SCP;
import Pages.setConexion;

public class SCP_Joaquin extends TestBase{
	private WebDriver driver;
	
	@BeforeClass(groups = "SCP")
	  public void Init() throws Exception
	  {
	    this.driver = setConexion.setupEze();
	    loginSCPAdmin(driver);
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	  }
	
	@BeforeMethod(groups = "SCP")
	  public void setUp() throws Exception {
	    try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	    SCP prueba= new SCP(driver);
	    prueba.goToMenu("SCP");
	    prueba.clickOnTabByName("cuentas");
	    prueba.clickOnFirstAccRe();
	    try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}	
	}
	
	@AfterClass(groups = "SCP")
	public void teardown() {
		driver.quit();
		sleep(3000);
	}
	
	private boolean isFileDownloaded_Ext(String dirPath, String ext){
		boolean flag=false;
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        flag = false;
	    }
	    
	    for (int i = 1; i < files.length; i++) {
	    	if(files[i].getName().contains(ext)) {
	    		flag=true;
	    	}
	    }
	    return flag;
	}
	
	@Test(groups = "SCP")
	public void TS112619_Cronograma_de_cuenta_Filtros_Otros() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("cuartoTitulo", 1);
		driver.findElements(By.className("checkboxFiltroTimeLine")).get(7).click();
		driver.findElements(By.cssSelector(".btn.btn.btn-default.btn-xs")).get(1).click();
		sleep(3000);
		List<WebElement> elementos = driver.findElements(By.cssSelector(".tl-timemarker-content-container-small"));
		
		System.out.println(elementos.get(0).getText());
		for (WebElement e : elementos) {
			Assert.assertTrue(e.getAttribute("class").contains("Otros"));
		}
	}
	
	@Test(groups = "SCP")
	public void TS112729_Negocio_del_cliente_Contexto_Estrat�gico_del_Cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(1);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Contexto Estrat�gico del Cliente"));
	}
	
	@Test(groups = "SCP")
	public void TS112732_Negocio_del_cliente_Desafios_Iniciativas_de_negocio_del_cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(2);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Desaf�os | Iniciativas de Negocio del Cliente"));
	}
	
	@Test(groups = "SCP")
	public void TS112735_Negocio_del_cliente_Descripci�n_del_Cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(0);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Descripci�n del Cliente"));
	}
	
	@Test(groups = "SCP")
	public void TS112739_Negocio_del_cliente_Evoluci�n_de_la_satisfacci�n_del_cliente() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		WebElement label = driver.findElements(By.cssSelector(".botones")).get(4);
		label.click();
		sleep(2000);
		
		Assert.assertTrue(label.getText().contains("Evoluci�n de la Satisfacci�n del Cliente"));
	}
	
	@Test(groups = "SCP")
	public void TS112742_Negocio_del_cliente_Exportar_a_Excel() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		driver.findElements(By.cssSelector(".botones")).get(4).click();
		sleep(2000);
		driver.findElements(By.xpath("//button[@class='btn btn-default btn-sm']")).get(1).click();
		sleep(3000);
		
		Assert.assertTrue(isFileDownloaded_Ext("C:\\Users\\Joaquin\\Downloads\\", ".xls"));
	}
	
	@Test(groups = "SCP")
	public void TS112743_Negocio_del_cliente_Guardar() {
		SCP prueba= new SCP(driver);
		prueba.moveToElementOnAccAndClick("primerTitulo", 2);
		driver.findElements(By.cssSelector(".botones")).get(4).click();
		sleep(2000);
		int i = 0;
		List<WebElement> tablas = driver.findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
		for (WebElement t : tablas) {
			if (t.isDisplayed()) {
				WebElement tbody = t.findElement(By.tagName("tbody"));
				WebElement celda = tbody.findElement(By.tagName("span")).findElement(By.tagName("span"));
				Actions builder = new Actions(driver);
				builder.moveToElement(celda).doubleClick().build().perform();
				celda.findElement(By.tagName("input")).sendKeys("test");
				driver.findElement(By.xpath("//button[@class='btn btn-default btn-sm']")).click();
				sleep(2000);
				celda.findElement(By.tagName("input")).submit();
				break;
			}
			i++;
		}
		driver.findElements(By.cssSelector(".botones")).get(4).click();
		sleep(2000);
		List<WebElement> tablas2 = driver.findElements(By.cssSelector(".table.table-striped.table-bordered.table-condensed"));
		WebElement tbody = tablas2.get(i).findElement(By.tagName("tbody"));
		WebElement celda = tbody.findElement(By.tagName("span")).findElement(By.tagName("span"));
		
		Assert.assertTrue(celda.getText().equalsIgnoreCase("test"));
	}
}
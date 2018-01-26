package Tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;

public class CustomerCareFase4 extends TestBase{

	CustomerCare page;

	
	@BeforeClass (groups = {"CustomerCare", "Vista360Layout", "DetalleDeConsumos", "ActualizarDatos", "ProblemasConRecargas", "DebitoAutomatico"})
	public void init() {
		inicializarDriver();
		page = new CustomerCare(driver);
		login();
		IrA.CajonDeAplicaciones.ConsolaFAN();
	}
	
	@AfterClass (groups = {"CustomerCare", "Vista360Layout", "DetalleDeConsumos", "ActualizarDatos", "ProblemasConRecargas", "DebitoAutomatico"})
	public void quit() {
		page.cerrarTodasLasPesta�as();
		IrA.CajonDeAplicaciones.Ventas();
		cerrarTodo();
	}
	
	@BeforeMethod (groups = {"CustomerCare", "Vista360Layout", "DetalleDeConsumos", "ActualizarDatos", "ProblemasConRecargas", "DebitoAutomatico"})
	public void after() {
		page.cerrarTodasLasPesta�as();
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS15955_360_View_Ver_Equipo_Creador_en_Case_Caso_Creado_Cerrar_Caso_Campo_Equipo_del_Creador_no_cambia_valor() {
		WebElement selector = driver.findElement(By.cssSelector(".x-btn-small.x-btn-icon-small-left"));
		WebElement btnSplit = selector.findElement(By.className("x-btn-split"));
		Actions builder = new Actions(driver);   
		builder.moveToElement(btnSplit, 245, 20).click().build().perform();
		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("piped")));
		driver.findElement(By.name("newCase")).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("bottomButtonRow")));
		List <WebElement> dc = driver.findElements(By.name("save"));
		for (WebElement x : dc) {
			if (x.getAttribute("value").contains("�Desea continuar?")) {
				x.click();
				break;
			}
		}
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.xpath("//*[@id=\"cas3\"]")));
		driver.findElement(By.xpath("//*[@id=\"cas3\"]")).sendKeys("Fernandoasd Careeeeee");
		driver.findElement(By.xpath("//*[@id=\"cas7\"]")).click();
		driver.findElement(By.xpath("//*[text() = 'Nuevo']")).click();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("topButtonRow")));
		List <WebElement> save = driver.findElements(By.name("save"));
		for (WebElement x : save) {
			if (x.getAttribute("value").contains("Guardar")) {
				x.click();
				break;
			}
		}
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement cc = driver.findElement(By.cssSelector(".preamblecontainer.displayblock"));
		Assert.assertTrue(cc.getText().toLowerCase().contains("cre� este caso"));
		List <WebElement> ec = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite"));
		String equipoCreador = ec.get(11).getText();
		List <WebElement> elim = driver.findElements(By.name("close"));
		for (WebElement x : elim) {
			if (x.getAttribute("value").contains("Cerrar caso")) {
				x.click();
				break;
			}
		}
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id=\"cas7\"]")).click();
		driver.findElement(By.xpath("//*[text() = 'Cerrado']")).click();
		driver.findElement(By.xpath("//*[@id=\"cas6\"]")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[text() = 'Complex functionality']")).click();
		List <WebElement> cerrar = driver.findElements(By.className("btn"));
		for (WebElement x : cerrar) {
			if (x.getAttribute("value").contains("Guardar")) {
				x.click();
				break;
			}
		}
		List <WebElement> casoCerrado = driver.findElements(By.cssSelector(".dataCol.col02.inlineEditWrite"));
		Assert.assertTrue(casoCerrado.get(11).getText().equals(equipoCreador));
	}
	
	@Test (groups = {"CustomerCare", "DebitoAutomatico"})
	public void TS37230_Automatic_Debit_Subscriptions_Sesi�n_guiada_D�bito_Autom�tico_Inicial_Paso_2_Adhesi�n_Cuenta_con_Mora() {
		page.elegirCuenta("aaaaCuenta ConMora");
		page.irAGestion("d�bito auto");
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("borderOverlay")));
		List <WebElement> element = driver.findElements(By.className("borderOverlay"));
		element.get(0).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.className("slds-checkbox--faux")).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		WebElement error = driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope"));
		Assert.assertTrue(error.getText().toLowerCase().contains("la cuenta de facturaci�n aaaacuenta conmora est� suspendida por mora"));
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS37469_360_View_Vista_360_de_facturaci�n_clientes_individuos_Persistencia_Visualizar_Convenios_de_Pago() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAFacturacion();
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".icon.icon-v-modify-contract")));
		List <WebElement> element = driver.findElements(By.className("slds-text-body_regular"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Convenios de pago")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas"})
	public void TS69021_Problems_with_Refills_Problemas_con_Recargas_Base_de_Conocimiento_Tarjeta_Prepaga_OS_Verificar_articulo_en_Base_de_conocimiento() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAProblemasConRecargas();
		driver.switchTo().defaultContent();
		List <WebElement> know = driver.findElements(By.className("sd_widget_btn_text"));
		know.get(0).click();
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("blocksettings")));
		Assert.assertTrue(driver.findElement(By.className("blocksettings")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.className("articleListItem")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69033_360_View_360_View_Historiales_Formulario_Historiales_Ver_detalle_Historial_de_Recargas_Verificar_nombre_Historico() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAHistoriales();		
		List <WebElement> hist = driver.findElements(By.cssSelector(".slds-p-around--large.slds-text-body--regular.labelFont"));
		for (WebElement x : hist) {
			if (x.getText().toLowerCase().contains("historial de recargas")) {
				x.click();
				break;
			}
		}
		WebElement element = page.obtenerPesta�aActiva();
		Assert.assertTrue(element.getText().equals("Historiales"));
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69034_360_View_360_View_Historiales_Formulario_Historiales_Ver_detalle_Historial_de_Packs_Verificar_nombre_Historico() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAHistoriales();		
		List <WebElement> hist = driver.findElements(By.cssSelector(".slds-p-around--large.slds-text-body--regular.labelFont"));
		for (WebElement x : hist) {
			if (x.getText().toLowerCase().contains("historial de packs")) {
				x.click();
				break;
			}
		}
		WebElement element = page.obtenerPesta�aActiva();
		Assert.assertTrue(element.getText().equals("Historiales"));
	}
	
	@Test (groups = {"CustomerCare", "ActualizarDatos"})
	public void TS69038_Profile_Changes_Perfil_del_cliente_Modificacion_DNI_CUIL_Verificar_Imposibilidad_de_modificar_DNI_y_CUIL_al_mismo_tiempo(){
		page.elegirCuenta("aaaaFernando Care");
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("profile-edit")));
		List <WebElement> act = driver.findElements(By.className("profile-edit"));
		act.get(0).click();
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("LastName")));
		WebElement dni = driver.findElement(By.id("DocumentType"));
		WebElement cuil = driver.findElement(By.id("Cuil"));
		Assert.assertTrue(dni.getAttribute("disabled").equals("true"));
		Assert.assertTrue(cuil.getAttribute("required").equals("true"));
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69098_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_encabezado() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List <WebElement> title = driver.findElements(By.cssSelector(".slds-size--11-of-12.slds-medium-size--11-of-12.slds-large-size--11-of-12"));
		boolean a = false;
		for (WebElement x : title) {
			if (x.getText().equals("Detalle de Consumos")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69099_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_Servicio() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Servicio")) {
				a = true;
			}
		}
		List <WebElement> cons = driver.findElements(By.id("text-input-01"));
		boolean b = false;
		for (WebElement x : cons) {
			if (x.getAttribute("placeholder").isEmpty()) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69100_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_menu_desplegable_del_filtro_Servicio() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List <WebElement> filtro = driver.findElements(By.id("text-input-01"));
		filtro.get(0).click();
		Assert.assertTrue(filtro.get(0).getAttribute("placeholder").isEmpty());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69101_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_Periodo() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Periodo")) {
				a = true;
			}
		}
		List <WebElement> cons = driver.findElements(By.id("text-input-02"));
		boolean b = false;
		for (WebElement x : cons) {
			if (x.getAttribute("placeholder").equals("Los �ltimos 15 d�as")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69102_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_menu_desplegable_del_filtro_Periodo() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List <WebElement> filtro = driver.findElements(By.id("text-input-02"));
		filtro.get(0).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Los �ltimos 15 d�as']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Una factura']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69104_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_calendario_en_filtro_Inicio() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		driver.findElement(By.id("text-input-id-1")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69106_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_calendario_en_filtro_Fin() {
		TestBase TB = new TestBase();
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();
		TB.waitFor(driver, By.id("text-input-id-2"));
		driver.findElement(By.id("text-input-id-2")).click();
		TB.waitFor(driver, By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"));
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69107_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_boton_Consultar() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69108_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_desplegable_Filtros_Avanzados() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Filtros avanzados")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69110_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Numero_de_origen_o_destino() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("N�mero de origen o destino")) {
				a = true;
			}
		}
		List <WebElement> search = driver.findElements(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-valid.ng-empty"));
		boolean b = false;
		if (search.get(1).getAttribute("placeholder").equals("Buscar")) {
			b = true;			
		}		
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69111_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Tipo_de_consumo() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Tipo de consumo")) {
				a = true;
			}
		}
		List <WebElement> cons = driver.findElements(By.id("text-input-02"));
		boolean b = false;
		for (WebElement x : cons) {
			if (x.getAttribute("placeholder").equals("Todos los consumos")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69112_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Con_o_sin_cargo() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Con o sin cargo")) {
				a = true;
			}
		}
		List <WebElement> checkbox = driver.findElements(By.id("text-input-01"));
		boolean b = false;
		for (WebElement x : checkbox) {
			if (x.getAttribute("placeholder").equals("Con y sin cargo")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69113_Consumption_Details_Detalle_de_Consumo_Modificacion_de_Tabla_Nuevas_Columnas_Visualizar_filtro_avanzado_Producto() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Producto")) {
				a = true;
			}
		}		
		Assert.assertTrue(a);
		Assert.assertTrue(driver.findElement(By.id("text-input-03")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69138_Consumption_Details_Detalle_de_Consumo_Filtro_x_Producto_Visualizar_filtro_Producto_desplegado() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		List<WebElement> filtro = driver.findElements(By.className("slds-text-heading--x-small"));
		filtro.get(0).click();
		List <WebElement> element = driver.findElements(By.className("slds-text-heading--small"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("N�mero de origen o destino")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "DetalleDeConsumos"})
	public void TS69149_Consumption_Details_Criterios_de_Filtro_Temporal_Visualizar_filtro_Fin() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("detalle de consu");
		driver.findElement(By.id("text-input-02")).click();
		driver.findElement(By.xpath("//*[text() = 'Un rango personalizado']")).click();		
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-picklist.slds-dropdown-trigger.slds-dropdown-trigger--click"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().equals("Fin")) {
				a = true;
			}
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69164_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Visualizar_todas_las_ordenes() {
		page.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		waitFor(driver, By.cssSelector(".icon.icon-v-troubleshoot-line"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".icon.icon-v-troubleshoot-line"));
		gest.get(1).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("text-input-id-1")));
		Assert.assertTrue(driver.findElement(By.className("slds-m-around--medium")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69165_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Visualizar_todos_los_casos() {
		page.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		waitFor(driver, By.cssSelector(".icon.icon-v-troubleshoot-line"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".icon.icon-v-troubleshoot-line"));
		gest.get(1).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("text-input-id-1")));
		List <WebElement> element = driver.findElements(By.cssSelector(".slds-truncate.slds-p-vertical--medium.clickablerow"));
		boolean a = false;
		boolean b = false;
		for (WebElement x : element) {
			if (x.getText().equals("Case")) {
				a = true;
			}
			if (x.getText().startsWith("000")) {
				b = true;
			}
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69168_360_View_360_View_Visualizacion_de_gestiones_desde_el_asset_Fitlro_Fecha_Verificar_Calendario() {
		page.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		waitFor(driver, By.cssSelector(".icon.icon-v-troubleshoot-line"));
		List <WebElement> gest = driver.findElements(By.cssSelector(".icon.icon-v-troubleshoot-line"));
		gest.get(1).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		BasePage cambioFrameByID = new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("text-input-id-1")));
		Assert.assertTrue(driver.findElement(By.id("text-input-id-1")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("text-input-id-2")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "Vista360Layout"})
	public void TS69251_360_View_Validacion_de_nominacion_en_Vista_360_Asset_no_nominado_Visualiza_todas_sus_acciones_disponibles() {
		page.elegirCuenta("aaaaFernando Care");
		driver.findElement(By.cssSelector(".console-card.active")).click();
		List <WebElement> gestiones = driver.findElements(By.className("slds-text-body_regular"));
		boolean a = false;
		boolean b = false;
		boolean c = false;
		boolean d = false;
		for (WebElement x : gestiones) {
			if (x.getText().equals("Detalle de Consumos")) {
				a = true;
			}
			if (x.getText().equals("Historiales")) {
				b = true;
			}
			if (x.getText().equals("Ahorr�")) {
				c = true;
			}
			if (x.getText().equals("Mis servicios")) {
				d = true;
			}
		}
		Assert.assertTrue(a && b && c && d);
		Assert.assertTrue(driver.findElement(By.cssSelector(".console-flyout.active.flyout")).isDisplayed());
	}
	
	@Test (groups = {"CustomerCare", "ProblemasConRecargas"})
	public void TS68984_Problems_with_Refills_UX_Tarjeta_de_Recarga_Pre_paga_Verificacion_Visualizar_Boton_Consultar() {
		page.elegirCuenta("aaaaFernando Care");
		page.irAGestion("estado de tarjeta");
		WebElement boton = driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-form-group.vlc-slds-remote-action--button.ng-pristine.ng-valid.ng-scope"));
		Assert.assertTrue(boton.getText().equals("Consultar"));
	}
}
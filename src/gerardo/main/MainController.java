package gerardo.main;

import java.io.IOException;

import org.hibernate.SessionFactory;


import gerardo.addNewLibro.AddController;
import gerardo.conexion.ConnectionStatusThread;
import gerardo.visualizar.VisualizarController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainController {
	
	private BooleanProperty connStatus;
	private  App app;
	private VisualizarController visualizarcontroller;
	private ConnectionStatusThread statusThread;
	private SessionFactory sesionFactory;
	private Stage conectandoStage;
	private AnchorPane view;
	private FadeTransition outtr;

	
	
	public MainController(App app) {
		connStatus=new SimpleBooleanProperty(this,"connStatus");
		this.app = app;
		this.visualizarcontroller = new VisualizarController(connStatus);
		statusThread=new ConnectionStatusThread(this,1);
		statusThread.start();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConectandoView.fxml"));
			loader.setController(this);
			view = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		conectandoStage= new Stage();
		Scene scene= new Scene(view);
		scene.setFill(null);
		conectandoStage.setScene(scene);
		conectandoStage.setResizable(false);
		conectandoStage.initModality(Modality.APPLICATION_MODAL);
		conectandoStage.initStyle(StageStyle.TRANSPARENT);
		conectandoStage.initOwner(app.getPrimaryStage());
		Platform.runLater(()-> conectandoStage.show());
		
		
		connStatus.addListener((obs,oldValue,newValue)->onConnStatusChange(oldValue,newValue));
		
	}
	
	

	private void onConnStatusChange(Boolean oldValue, Boolean newValue) {
		
	
		outtr = new FadeTransition(Duration.seconds(2),conectandoStage.getScene().getRoot());
		outtr.setAutoReverse(true);
		outtr.setFromValue(1);
		outtr.setToValue(0);
		outtr.setOnFinished(e->{
			conectandoStage.hide();
		});
		
		FadeTransition intr = new FadeTransition(Duration.seconds(1),conectandoStage.getScene().getRoot());
		intr.setAutoReverse(true);
		intr.setFromValue(0);
		intr.setToValue(1);
		
		
		if (!oldValue && newValue) {
			System.out.println("connectado");
			outtr.play();
		}else if(!newValue){
			Platform.runLater(()->conectandoStage.show());
			intr.play();
				;
				
			
		}
	}



	public void statusOn(SessionFactory sesionFactory) {

		this.sesionFactory=sesionFactory;
		visualizarcontroller.setSesionFactory(sesionFactory);
		connStatus.set(true);
	}
	
	public void statusOff() {
		connStatus.set(false);
		if (sesionFactory!=null) {
			sesionFactory.close();
		}
	}
	
	public VisualizarController getVisualizarcontroller() {
		return visualizarcontroller;
	}

	public BooleanProperty connStatusProperty() {
		return this.connStatus;
	}
	

	public boolean isConnStatus() {
		return this.connStatusProperty().get();
	}
	

	public void setConnStatus(final boolean connStatus) {
		this.connStatusProperty().set(connStatus);
	}
	
	public void close() {
		statusThread.terminar=true;
		sesionFactory.close();
	}
	
	
}

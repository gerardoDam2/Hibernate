package gerardo.main;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;


import gerardo.conexion.HibernateUtil;
import gerardo.tablas.Libro;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private Stage primaryStage;
	private MainController maincontroller;

	

	@Override
	public void stop() throws Exception {
		maincontroller.close();
		super.stop();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		

		 this.primaryStage=primaryStage;
		
		 maincontroller=new MainController(this);
		 Scene scene = new
		 Scene(maincontroller.getVisualizarcontroller().getView(),1000,800);
		 primaryStage.getIcons().addAll(
				 new Image(getClass().getResourceAsStream("/res/Bear-32.png")),
				 new Image(getClass().getResourceAsStream("/res/Bear-32.png"))
				 );
		 scene.getStylesheets().add(getClass().getResource("/res/style.css").toExternalForm());
		 primaryStage.setTitle("Hibernate    Gerardo Mederos");
		 primaryStage.setScene(scene);
		 primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}



	public Stage getPrimaryStage() {
		return primaryStage;
	}

}

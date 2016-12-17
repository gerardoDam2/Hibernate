package gerardo.addNewLibro;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import gerardo.conexion.HibernateUtil;
import gerardo.tablas.Autor;
import gerardo.tablas.DepositoLegal;
import gerardo.tablas.Libro;
import gerardo.visualizar.VisualizarController;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddController implements Initializable {
	
    private BorderPane view;
    
	private Stage addStage;

	private ListProperty<Autor> unselectedList;
	private ListProperty<Autor> selectedList;
	private Libro libro;
	
	@FXML
	private TextField nombreTextField;
	
	@FXML
	private TextField isbnTextField;

	@FXML
	private DatePicker fechaDatePicker;

	@FXML
	private TextField depositoLegalTextField;

	@FXML
	private ListView<Autor> unselectedAutoresListView;

	@FXML
	private Button removeAutorButton;

	@FXML
	private Button addAutorButton;

	@FXML
	private ListView<Autor> selectedAutoresListView;

	@FXML
	private Button cancelarButton;

	@FXML
	private Button guardarButton;

	private SessionFactory sesionFactory;

	private VisualizarController visualizarController;
	
	private BooleanProperty checkNombre;
	private BooleanProperty checkIsbn;
	private BooleanProperty checkDepositoLegal;

	private int puntero;
	
	

	public AddController(VisualizarController visualizarController) {
		
		checkNombre = new SimpleBooleanProperty(this,"checkNombre");
		checkIsbn= new SimpleBooleanProperty(this,"checkIsbn");
		checkDepositoLegal= new SimpleBooleanProperty(this,"checkDepositoLegal");
		
		
		this.visualizarController=visualizarController;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("addView.fxml"));
			loader.setController(this);
			view=loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		addStage= new Stage();
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("/res/style.css").toExternalForm());
		addStage.setScene(scene);
		addStage.initModality(Modality.APPLICATION_MODAL);
		addStage.initStyle(StageStyle.UTILITY);
		addStage.setOnCloseRequest(e->onCancelarAction(null));
		
	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedList= new SimpleListProperty<>(this,"selectedList",FXCollections.observableArrayList());
		unselectedList= new SimpleListProperty<>(this,"unselectedList",FXCollections.observableArrayList());
		selectedAutoresListView.itemsProperty().bind(selectedList);
		unselectedAutoresListView.itemsProperty().bind(unselectedList);
		fechaDatePicker.valueProperty().addListener((obs,oldValue,newValue)->checkearFecha(newValue));
		
		nombreTextField.textProperty().addListener((obs,oldValue,newValue)->comprobarNombre(newValue));
		isbnTextField.textProperty().addListener((obs,oldValue,newValue)->comprobarIsbn(newValue));
		depositoLegalTextField.textProperty().addListener((obs,oldValue,newValue)->comprobarDepositoLegal(newValue));
		
		guardarButton.disableProperty().bind(
				new When(checkIsbn.and
						(checkNombre).and
						(checkDepositoLegal))
						.then(false)
						.otherwise(true)
				);
		
		nombreTextField.idProperty().bind(new When(checkNombre).then("correcto").otherwise("error"));
		isbnTextField.idProperty().bind(new When(checkIsbn).then("correcto").otherwise("error"));
		depositoLegalTextField.idProperty().bind(new When(checkDepositoLegal).then("correcto").otherwise("error"));
		
	}
	


	private void comprobarDepositoLegal(String newValue) {
		if (newValue.length()<=20) 
			checkDepositoLegal.set(false);
		else
			checkDepositoLegal.set(true);
		System.out.println(checkDepositoLegal.get()+""+checkIsbn.get()+checkNombre.get()+"");
	}

	private void comprobarIsbn(String newValue) {
		String expresion = "^[0-9]{2}-[0-9]{3}-[0-9]{4}-[a-z]{1}$";
		Pattern check = Pattern.compile(expresion,Pattern.CASE_INSENSITIVE);
		checkIsbn.set(check.matcher(newValue).matches());
	}

	private void comprobarNombre(String newValue) {
		System.out.println(newValue);
		if (newValue.trim().length()==0) 
			checkNombre.set(false);
		else if (newValue.length()<=40)
			checkNombre.set(true);
	
	}

	private void checkearFecha(LocalDate newValue) {
		if (newValue==null || newValue.compareTo(LocalDate.now())>0) {
			fechaDatePicker.setValue(LocalDate.now());
		}
	}


	@FXML
	void onCancelarAction(ActionEvent event) {
		libro=null;
		nombreTextField.clear();
		isbnTextField.clear();
		fechaDatePicker.setValue(null);
		depositoLegalTextField.clear();
		selectedList.clear();
		unselectedList.clear();
		addStage.close();
		visualizarController.onUpdateAction(null);
	}

	@FXML
	void onGuardarAction(ActionEvent event) {
		libro.setNombreLibro(nombreTextField.getText());
		libro.setISBN(isbnTextField.getText());
		libro.setFechaintro(Date.valueOf(fechaDatePicker.getValue()));
		if (!(depositoLegalTextField.getText().trim().length()==0)) {
			DepositoLegal depositoLegal = new DepositoLegal();
			depositoLegal.setLibro(libro);
			depositoLegal.setDepositolegal(depositoLegalTextField.getText());
			libro.setDepositoLegal(depositoLegal);
		}
	
		libro.getAutores().clear();
		libro.getAutores().addAll(selectedList);
		Session session = sesionFactory.openSession();
		session.getTransaction().begin();
		session.saveOrUpdate(libro);
		session.getTransaction().commit();
		onCancelarAction(event);
		if (puntero!=-1) {
			visualizarController.target(puntero);
			puntero=-1;
		}

		
	}

	@FXML
	void onRemoveAutorAction(ActionEvent event) {
		Autor autor = selectedAutoresListView.getSelectionModel().getSelectedItem();
		if (autor!=null) {
			selectedList.remove(autor);
			unselectedList.add(autor);
		}
		
	}
	

	@FXML
	void onAddAutorAction(ActionEvent event) {
		Autor autor = unselectedAutoresListView.getSelectionModel().getSelectedItem();
		if (autor!=null) {
			unselectedList.remove(autor);
			selectedList.add(autor);
		}
	}
	
	public void mostrarAddScene(){
		fechaDatePicker.setValue(LocalDate.now());
		Session session = sesionFactory.openSession();
		unselectedList.addAll(session.createQuery("from Autor").getResultList());
		addStage.show();
		libro=new Libro();
		session.close();
	}
	
	public void mostrarModScene(Libro libro, int index){
		Session session = sesionFactory.openSession();
		puntero=index;
		this.libro=libro;
		
		nombreTextField.setText(libro.getNombreLibro());
		isbnTextField.setText(libro.getISBN());
		fechaDatePicker.setValue(libro.getFechaintro().toLocalDate());
		if (libro.getDepositoLegal()!=null) 
		depositoLegalTextField.setText(libro.getDepositoLegal().getDepositolegal());
		
		unselectedList.addAll(session.createQuery("from Autor").getResultList());
		selectedList.addAll(libro.getAutores());
		for (Autor autor : selectedList) {
			if (unselectedList.contains(autor)) {
				unselectedList.remove(autor);
			}
		}
		addStage.show();
		session.close();;
	}

	public SessionFactory getSesionFactory() {
		return sesionFactory;
	}

	public void setSesionFactory(SessionFactory sesionFactory) {
		this.sesionFactory = sesionFactory;
	}
	
}

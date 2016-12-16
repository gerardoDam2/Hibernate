package gerardo.visualizar;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import gerardo.addNewLibro.AddController;
import gerardo.conexion.HibernateUtil;
import gerardo.tablas.Autor;
import gerardo.tablas.DepositoLegal;
import gerardo.tablas.Ejemplar;
import gerardo.tablas.Libro;
import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class VisualizarController implements Initializable {

	private BorderPane view;
	
	private AddController addController;

	@FXML
	private TableColumn<DepositoLegal, String> despositoCol;

	@FXML
	private TableColumn<Autor, String> codAutorCol;

	@FXML
	private TableColumn<Autor, String> nombreAutorCol;

	@FXML
	private TableColumn<Ejemplar, String> codEjemplarCol;

	@FXML
	private TableColumn<Ejemplar, String> importeEjemplarCol;

	@FXML
	private TableColumn<Ejemplar, String> monedaEjemplarCol;

	@FXML
	private TableView<DepositoLegal> depositoTableView;

	@FXML
	private TableView<Autor> autoresTableView;

	@FXML
	private TableView<Libro> tableViewLibros;

	@FXML
	private TableView<Ejemplar> ejemplaresTableView;

	@FXML
	private TableColumn<Libro, Integer> codigoLibroCol;

	@FXML
	private TableColumn<Libro, String> nombreLibroCol;

	@FXML
	private TableColumn<Libro, String> isbnLibroCol;

	@FXML
	private TableColumn<Libro, String> fechaLibroCol;

	@FXML
	private Button nuevoButton;

	@FXML
	private Button modificarButton;

	@FXML
	private Button eliminarButton;

    @FXML
    private ImageView updateImage;
	

	@FXML
	private Button updateButton;
	
	private BooleanProperty connStatus;

	private SessionFactory sesionFactory;

	public VisualizarController(BooleanProperty connStatus) {
		this.connStatus=connStatus;
		this.addController= new AddController(this);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("VisualizarView.fxml"));
			loader.setController(this);
			view = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		view.disableProperty().bind(connStatus.not());
	}

	
	public SessionFactory getSesionFactory() {
		return sesionFactory;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// BINDINGS

		
		tableViewLibros.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableViewLibros.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldV, newV) -> onSelectedLibroChange(oldV, newV));

		
		nuevoButton.setOnAction(e->addController.mostrarAddScene());
		modificarButton.setOnAction(e->addController.mostrarModScene(tableViewLibros.getSelectionModel().getSelectedItem(),tableViewLibros.getSelectionModel().getSelectedIndex()));
		// TABLA LIBROS
		codigoLibroCol.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("codLibro"));
		nombreLibroCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("nombreLibro"));
		isbnLibroCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("ISBN"));
		DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		fechaLibroCol.setCellValueFactory(e -> {
			SimpleStringProperty fecha = new SimpleStringProperty();
			fecha.setValue(myDateFormatter.format(e.getValue().getFechaintro().toLocalDate()));
			return fecha;	
		});

		// TABLA DEPOSITO
		despositoCol.setCellValueFactory(new PropertyValueFactory<DepositoLegal,String>("depositolegal"));
		despositoCol.prefWidthProperty().bind(depositoTableView.widthProperty());
		

		// TABLA AUTORES
		codAutorCol.setCellValueFactory(new PropertyValueFactory<Autor,String>("codAutor"));
		nombreAutorCol.setCellValueFactory(new PropertyValueFactory<Autor,String>("nombreAutor"));
		
		codAutorCol.prefWidthProperty().bind(autoresTableView.widthProperty().multiply(0.2));
		nombreAutorCol.prefWidthProperty().bind(autoresTableView.widthProperty().multiply(0.8));
		
		// TABLA EJEMPLARES
		codEjemplarCol.setCellValueFactory(new PropertyValueFactory<Ejemplar,String>("codEjemplar"));
		importeEjemplarCol.setCellValueFactory(new PropertyValueFactory<Ejemplar,String>("importe"));
		monedaEjemplarCol.setCellValueFactory(new PropertyValueFactory<Ejemplar,String>("moneda"));
		
		ObservableList<TableColumn<Ejemplar, ?>> colsEje = ejemplaresTableView.getColumns();
		for (TableColumn<Ejemplar, ?> tableColumn : colsEje) {
			tableColumn.prefWidthProperty().bind(ejemplaresTableView.widthProperty().divide(3));
		}
		
		eliminarButton.disableProperty().bind(Bindings.size(tableViewLibros.getSelectionModel().getSelectedItems()).isEqualTo(0));
		
		modificarButton.disableProperty().bind(eliminarButton.disabledProperty());
		
		ObservableList<TableColumn<Libro, ?>> columnas = tableViewLibros.getColumns();
		for (TableColumn<Libro, ?> tableColumn : columnas) {
			tableColumn.prefWidthProperty().bind(tableViewLibros.widthProperty().divide(4));
		}
		

	}

	private void onSelectedLibroChange(Libro oldV, Libro newV) {
		autoresTableView.itemsProperty().get().clear();
		depositoTableView.itemsProperty().get().clear();
		ejemplaresTableView.itemsProperty().get().clear();

		if (newV != null) {
			if (newV.getDepositoLegal()!=null) {
			depositoTableView.itemsProperty().get().add(newV.getDepositoLegal());
			}
			ejemplaresTableView.itemsProperty().get().addAll(newV.getEjemplares());
			autoresTableView.itemsProperty().get().addAll(newV.getAutores());
		}
	}

	

	@FXML
	void onEliminarAction(ActionEvent event) {
		Session session = sesionFactory.openSession();
		ObservableList<Libro> librosSeleccionados = tableViewLibros.getSelectionModel().getSelectedItems();
			
		
	
		Transaction tx = session.beginTransaction();
			String hqlDel= "delete Libro where codLibro=?";
			for (Libro libro : librosSeleccionados) {
				session.delete(libro);
			}
		tx.commit();
		
		session.close();
		onUpdateAction(null);
	}

	@FXML
	void onModificarAction(ActionEvent event) {

	}

	@FXML
	void onNuevoAction(ActionEvent event) {

	}

	@FXML
	public void onUpdateAction(MouseEvent event) {
		RotateTransition rt = new RotateTransition(Duration.seconds(1) ,updateImage);
		rt.setFromAngle(0);
		rt.setToAngle(360);
		rt.play();
		Session session = sesionFactory.openSession();
		List<Libro> libros = session.createQuery("from Libro").getResultList();
		tableViewLibros.itemsProperty().get().clear();
		tableViewLibros.getItems().addAll(libros);
		session.close();
	}

	public BorderPane getView() {
		return view;
	}


	public void setSesionFactory(SessionFactory sesionFactory) {
		this.sesionFactory = sesionFactory;
		addController.setSesionFactory(sesionFactory);
	}


	public void target( int libro) {
		tableViewLibros.getSelectionModel().select(libro);
	}

	  
	
}

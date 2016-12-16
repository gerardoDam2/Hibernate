package gerardo.tablas;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="ejemplares")
public class Ejemplar implements Serializable {
	
	public Ejemplar(double importe, String moneda, Libro libro) {
		super();
		this.importe = importe;
		this.moneda = moneda;
		this.libro = libro;
	}
	
	public Ejemplar() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private  int codEjemplar;
	
	@Column(columnDefinition="DECIMAL(5,2)")
	private double importe;
	
	private String moneda;
	
	@ManyToOne
	@JoinColumn(name="codLibro") 
	private Libro libro;

	public int getCodEjemplar() {
		return codEjemplar;
	}

	public void setCodEjemplar(int codEjemplar) {
		this.codEjemplar = codEjemplar;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	


	
	
}

package gerardo.tablas;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "despositoLegal")
public class DepositoLegal implements Serializable {
	
	
	

	

	@Id
	@GeneratedValue(generator = "myForeign")
	@GenericGenerator( name = "myForeign", strategy = "foreign ", parameters = {@org.hibernate.annotations.Parameter(name = "property", value = "libro")}) 
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinColumn(name = "codLibroDeposito")
	private Libro libro;

	@Column(columnDefinition = "VARCHAR(20)")
	private String depositolegal;
	
	
	 public DepositoLegal(Libro libro, String depositolegal) {
		this.libro = libro;
		this.depositolegal = depositolegal;
	}

	public DepositoLegal() {
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public String getDepositolegal() {
		return depositolegal;
	}

	public void setDepositolegal(String depositolegal) {
		this.depositolegal = depositolegal;
	}

}

package gerardo.tablas;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="libros")
public class Libro implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int codLibro;
	
	@Column(columnDefinition="VARCHAR(40)")
	private String nombreLibro;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String ISBN;
	
	private Date fechaintro;
	
	
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinTable(name="librosautores",joinColumns={@JoinColumn (name="codLibro")}
	,inverseJoinColumns={@JoinColumn (name="codAutor")})
    @Cascade({CascadeType.SAVE_UPDATE})
	private Set<Autor> autores= new HashSet <>(); 
	
	
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER,mappedBy="libro") 
	private List<Ejemplar> ejemplares = new ArrayList<>();
	
	@OneToOne(cascade={javax.persistence.CascadeType.ALL})
	@PrimaryKeyJoinColumn
	private DepositoLegal depositoLegal;
	
	public Libro (){
	}


	public int getCodLibro() {
		return codLibro;
	}


	public void setCodLibro(int codLibro) {
		this.codLibro = codLibro;
	}


	public String getNombreLibro() {
		return nombreLibro;
	}


	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}


	public String getISBN() {
		return ISBN;
	}


	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}


	public Date getFechaintro() {
		return fechaintro;
	}


	public void setFechaintro(Date fechaintro) {
		this.fechaintro = fechaintro;
	}



	

	public Set<Autor> getAutores() {
		return autores;
	}


	public void setAutores(Set<Autor> autores) {
		this.autores = autores;
	}


	public List<Ejemplar> getEjemplares() {
		return ejemplares;
	}


	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}


	public DepositoLegal getDepositoLegal() {
		return depositoLegal;
	}


	public void setDepositoLegal(DepositoLegal depositoLegal) {
		this.depositoLegal = depositoLegal;
	}

	
	
	
}

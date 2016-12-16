package gerardo.tablas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="autores")
public class Autor implements Serializable {

	@Id 
	@Column(columnDefinition="VARCHAR(4)")
	private String codAutor;
	
	@Column(columnDefinition="VARCHAR(30)")
	private String nombreAutor;
	
	
	

	public Autor() {
		
	}

	
	public Autor(String codAutor, String nombreAutor) {
		this.codAutor = codAutor;
		this.nombreAutor = nombreAutor;
	}


	public String getCodAutor() {
		return codAutor;
	}

	public void setCodAutor(String codAutor) {
		this.codAutor = codAutor;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}


	@Override
	public String toString() {
		return "( "+codAutor+" )"+"  "+nombreAutor;
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		if (codAutor == null) {
			if (other.codAutor != null)
				return false;
		} else if (!codAutor.equals(other.codAutor))
			return false;
		return true;
	}


	

	
	


	
}

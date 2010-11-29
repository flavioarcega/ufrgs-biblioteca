package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import biblioteca.persistence.dao.AutorDAO;
import biblioteca.persistence.entity.Autor;

public class AutorBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutorDAO autorDAO;
	
	public AutorBC() {
		autorDAO = new AutorDAO();
	}

	public List<Autor> listarAutores() {
		return autorDAO.listAll();
	}
	
	public List<Autor> salvarAutores(List<Autor> lista) {
		try {
			for (Autor autor : lista)
				if (autor.getNome().isEmpty() || autor.getExcluir()) {
					autorDAO.delete(autor);
					autor.setExcluir(true);
				}
			List<Autor> listaRetorno = new ArrayList<Autor>();
			for (Autor autor : lista) 
				if (!autor.getExcluir()) {
					autor = autorDAO.insertOrUpdate(autor);
					listaRetorno.add(autor);
				}
			return listaRetorno;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}

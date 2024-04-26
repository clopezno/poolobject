package ubu.gii.dass.c01;


import java.util.*;
/**
 * Pool que gestiona dos objetos de tipo Reusables para poder ser compartidos.
 * @author Carlos López clopezno@ubu.es
 */

public final class ReusablePool{

	private Vector<Reusable> reusables;
	private static ReusablePool instance;

	private ReusablePool(int size){
		reusables = new Vector<Reusable>(size);
		for(int i=0;i<size;i++)
			reusables.add(new Reusable());
	}

	/**
	 * Método singleton que crea u obtiene la única instancia del Pool que gestiona dos objetos Reusables
	 * @return la instancia única del Pool 
	 * 
	 */
	public static ReusablePool getInstance(){
		if (instance == null)
			instance = new ReusablePool(2);
		return instance; 
	}
	/**
	 * Adquire una instancia del Objeto Reusable disponible en el Pool
	 * @return un objeto reusable disponible en el pool
	 * @exception NotFreeInstanceException si no hay instancias de objetos reusables disponibles
	 * 
	 */
	public Reusable acquireReusable() throws NotFreeInstanceException{
		if (reusables.size()>0){
			Reusable r=(Reusable)reusables.lastElement();
			reusables.remove(r);
			return r;			
		}
		else{
			throw(new NotFreeInstanceException());
		}

	}
	/**
	 * El cliente libera una instancia del objeto Reusable y se guarda en el Pool para poder ser utilizada por otro cliente.
	 * @param r una instancia objeto reusable
	 * @exception DuplicatedInstanceException si el objeto reusable ya existe en el pool
	 * 
	 */

	public void releaseReusable(Reusable r) throws DuplicatedInstanceException {
		if (reusables.contains(r)==false){
			reusables.add(r);
		}
		else{
			throw(new DuplicatedInstanceException());
		}
	}
} 
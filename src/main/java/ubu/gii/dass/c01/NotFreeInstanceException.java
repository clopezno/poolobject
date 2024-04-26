package ubu.gii.dass.c01 ;

public class NotFreeInstanceException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFreeInstanceException(){
		super("No hay más instancias reutilizables disponibles. Reintentalo más tarde");
	}
}
package ubu.ii.dmsii.actividad01 ;

public class NotFreeInstanceException extends Exception{
	
	public NotFreeInstanceException(){
		super("No hay más instancias reutilizables disponibles. Reintentalo más tarde");
	}
}
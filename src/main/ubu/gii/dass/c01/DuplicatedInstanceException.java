package ubu.gii.dass.c01;

public class DuplicatedInstanceException extends Exception {
	
	public  DuplicatedInstanceException(){
		super("Ya existe esa instancia en el pool.");
	}

}

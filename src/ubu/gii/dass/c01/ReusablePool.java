package ubu.gii.dass.c01;


import java.util.*;


public class ReusablePool{

	private Vector<Reusable> reusables;
	private static ReusablePool instance;

	private ReusablePool(int size){
		reusables = new Vector<Reusable>(size);
		for(int i=0;i<size;i++)
			reusables.add(new Reusable());
	}

	public static ReusablePool getInstance(){
		if (instance == null)
			instance = new ReusablePool(2);
		return instance; 
	}

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

	public void releaseReusable(Reusable r){
		if (reusables.contains(r)==false){
			reusables.add(r);
		}
	}
} 
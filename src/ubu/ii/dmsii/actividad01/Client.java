package ubu.ii.dmsii.actividad01;

public class Client{
	
	
	public static void main(String arg[]) throws NotFreeInstanceException{
	  ReusablePool pool;
	  Reusable r1,r2,r3;
	  
	  pool = ReusablePool.getInstance();
	  r1 = pool.acquireReusable();
	  r2 = pool.acquireReusable();
	   
	  System.out.println(r1.util());
	  System.out.println(r2.util());
	  
	  
	  pool.releaseReusable(r2);
	  r3= pool.acquireReusable();
	  
	  System.out.println(r3.util());
	  System.out.println(r1.util());
	
	}

} 
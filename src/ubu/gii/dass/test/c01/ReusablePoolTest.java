/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;
import ubu.gii.dass.c01.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase Test que prueba la clase ReusablePool con 100% de cobertura.
 * 
 * @author CUEVAS DÍEZ JOSÉ RAMÓN
 * @author LÓPEZ MARÍN LAURA
 * @author CUADRADO GARCÍA IRENE
 * @author EPIKHIN ANTON
 */
public class ReusablePoolTest {

	/**
	 * Pool que contendrÃ¡ los objetos reusables
	 */
	private ReusablePool pool;
	/**
	 * Objetos reusables;
	 */
	private Reusable reusable1, reusable2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
		reusable1 = pool.acquireReusable();
		reusable2 = pool.acquireReusable();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool.releaseReusable(reusable1);
		pool.releaseReusable(reusable2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		// Adquiere instancia de ReusablePool.
		ReusablePool poolTest = ReusablePool.getInstance();

		// Comprueba que pool y poolTest son instancia de ReusablePool.
		assertTrue(pool instanceof ReusablePool);
		assertTrue(poolTest instanceof ReusablePool);

		// Compara los dos objetos son de la misma instancia.
		// Debido a que el metodo 'equals' no es sobreescrito, 'equals' y '=='
		// darán el mismo resultado.
		assertTrue(pool == poolTest);
		assertTrue(pool.equals(poolTest));
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		// acquirir correctamente
		try {
			pool.releaseReusable(reusable2);
			Reusable reusable3 = pool.acquireReusable();
		} catch (Exception e) {			
			fail("No se esperaba ninguna excepción ");
		}
		try {
			// acquirir sin posibilidad de hacerlo, NotFreeInstance
			Reusable reusable4 = pool.acquireReusable();
		} catch (NotFreeInstanceException e) {
			assertEquals("No hay más instancias reutilizables disponibles. Reintentalo más tarde", e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * .
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {
		
		try {
			// se libera del pool un objeto reusable, correcto
			pool.releaseReusable(reusable1);
			//se libera del pool un objeto reusable, correcto
			pool.releaseReusable(reusable2);
			// se intenta liberar el mismo objeto reusable,
			// DuplicatedInstanceException
			pool.releaseReusable(reusable1);
			fail("Se esperaba excepción de tipo DuplicatedInstanceException");
				
		} catch (DuplicatedInstanceException e) {
			assertEquals("Ya existe esa instancia en el pool.", e.getMessage());
		} catch (Exception e) {
			fail("No se espera ninguna excepcion");
		}
		
		try {
			//se intenta liberar un objeto nulo, Excepcion 
			pool.releaseReusable(null);	
			fail("Se esperaba excepción de tipo Exception que controle la liberación de nulos");
		}catch (Exception e){
			assertEquals("No se puede liberar un objeto nulo", e.getMessage());
		}
		
		try{
			//se intenta liberar un objeto reusable, cuando ya esta completo el pool
			Reusable reusable3=new Reusable();	
			pool.releaseReusable(reusable3);
			fail("Se esperaba excepción de tipo Exception que controle la dimensión máxima del pool");
		}catch(Exception e){
			assertEquals("No se puede liberar mas objetos que la dimension del pool", e.getMessage());
			
		}
		
		// se añade los objetos reusables para dejar el pool vacio (estado inicial)
		reusable1 = pool.acquireReusable();
		reusable2 = pool.acquireReusable();
		
	}

}

package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReusablePoolTest {

    @Test
    @DisplayName("testGetInstance")
    void testGetInstance() {
		// Comprobacion de que el pool no es nulo y que se comporta como un singleton
        ReusablePool pool1 = ReusablePool.getInstance();
        assertNotNull(pool1, "El pool no debería ser nulo tras la inicialización");
		
		// Comprobamos que dos llamadas a getInstance devuelven la misma instancia
        ReusablePool pool2 = ReusablePool.getInstance();
        assertSame(pool1, pool2, "getInstance debe devolver exactamente la misma instancia (Singleton)");
    }

	@Test
    @DisplayName("testAcquireReusableSuccess")
    void testAcquireReusableSuccess() throws Exception {
        // Modificacion del pool para comprobar que se obtienen objetos reutilizables validos
        ReusablePool pool = ReusablePool.getInstance();
        Reusable r = pool.acquireReusable();
        
        try {
            assertNotNull(r, "Debe devolver un objeto Reusable valido");
        } finally {
            // Restauramos el pool a su estado original para los demas tests
            pool.releaseReusable(r);
        }
    }
	
	@Test
    @DisplayName("testAcquireReusableThrowsException")
    void testAcquireReusableThrowsException() throws Exception {
        // Modificacion del pool para dejarlo sin instancias libres y comprobar que lanza la excepcion
        ReusablePool pool = ReusablePool.getInstance();
        
        // Vaciamos el pool (asumiendo que tiene tamaño 2)
        Reusable r1 = pool.acquireReusable();
        Reusable r2 = pool.acquireReusable();
        
        try {
            // El tercer intento debe lanzar la excepcion
            assertThrows(NotFreeInstanceException.class, () -> {
                pool.acquireReusable();
            }, "Debe lanzar NotFreeInstanceException al no haber instancias libres");
        } finally {
            // Restauramos el pool a su estado original para los demas tests
            pool.releaseReusable(r1);
            pool.releaseReusable(r2);
        }
    }
	
	@Test
    @DisplayName("testReleaseReusableSuccess")
    void testReleaseReusableSuccess() throws Exception {
        // Modificacion del pool para comprobar que se pueden liberar objetos correctamente sin lanzar excepciones
        ReusablePool pool = ReusablePool.getInstance();
        
        // Extraemos uno para vaciar un hueco
        Reusable r = pool.acquireReusable();
        
        // Restauramos el pool a su estado original para los demas tests
        assertDoesNotThrow(() -> {
            pool.releaseReusable(r);
        }, "No debe lanzar excepcion al liberar una instancia valida que no esta en el pool");
    }
	
	@Test
    @DisplayName("testReleaseReusableThrowsException")
    void testReleaseReusableThrowsException() throws Exception {
        // Modificacion del pool para comprobar que al intentar liberar un objeto que ya esta en el pool se lanza la excepcion
        ReusablePool pool = ReusablePool.getInstance();
        
        // Sacamos una instancia
        Reusable r = pool.acquireReusable();
        
        assertThrows(DuplicatedInstanceException.class, () -> {
            pool.releaseReusable(r); // Primera devolucion funciona y deja el pool restaurado
            pool.releaseReusable(r); // Segunda devolucion lanza excepcion
        }, "Debe lanzar DuplicatedInstanceException al intentar devolver un objeto que ya esta en el pool");
		
    }
	
}
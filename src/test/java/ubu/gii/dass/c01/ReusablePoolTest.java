package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

public class ReusablePoolTest {
    private static ReusablePool pool;
    private static final int maxResources = 2;


    @BeforeAll
    public static void setUp(){
        pool = ReusablePool.getInstance();
    }

    @AfterAll
    public static void tearDown() throws Exception {
    }

    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        ReusablePool pool1 = ReusablePool.getInstance();
        ReusablePool pool2 = ReusablePool.getInstance();
        assertNotNull(pool1);
        assertNotNull(pool2);
        assertEquals(pool1, pool2, "Los dos son identicos");
    }

    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        Reusable obj1 = pool.acquireReusable();
        Reusable obj2 = pool.acquireReusable();

        assertNotNull(obj1, "El primero no deberia ser null");
        assertNotNull(obj2, "El segundo no deberia ser null");
        assertNotSame(obj1, obj2, "Los objetos deberian ser diferentes");
    }

    @Test
    @DisplayName("testReleaseReusable")
    public void testReleaseReusable() {
        Reusable obj1 = pool.acquireReusable();
        
        assertNotNull(obj1, "Objeto no deberia ser null");
        pool.releaseReusable(obj1);
        
        Reusable obj2 = pool.acquireReusable();
        assertSame(obj1, obj2, "No deberia ser null");
    }
}

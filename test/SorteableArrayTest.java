import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SorteableArrayTest {

    private SorteableArray sortableArray;

    @Before
    public void setUp() throws Exception {
        sortableArray = new SorteableArray(10);
    }

    @Test
    public void test_estaVacio() {
        assertTrue(sortableArray.isEmpty());
    }

    @Test
    public void test_siElArrayContieneAlUno(){
        assertFalse(sortableArray.contains(1));
    }

    @Test
    public void test_agregoUnElementoAlSorteableArrayYVerificoConElPeekQueSeaElPrimerElemento(){
        sortableArray.add(1);
        assertEquals(1, sortableArray.peek());
    }

    @Test
    public void test_agregoUnElementoAlSorteableArrayYVerificoConElPopQueSeaElPrimerElemento(){
        sortableArray.add(1);
        assertEquals(1, sortableArray.pop());
    }

    @Test
    public void test_hagoElPopDeUnArrayConUnElementoYElArrayQuedaVacio(){
        sortableArray.add(1);
        sortableArray.pop();
        assertTrue(sortableArray.isEmpty());
    }

    @Test
    public void test_tamanioDelArray(){
        sortableArray.add(4);
        assertEquals(1, sortableArray.size());
    }

    @Test
    public void test_contieneUnElementoAgregado(){
        sortableArray.add(1);
        assertTrue(sortableArray.contains(1));
    }

    @Test
    public void cuandoOrdenoUnArregloObtengoElMismoArregloOrdenadoCorrectamente() throws InterruptedException{
        sortableArray.add(17);
        sortableArray.add(1);
        sortableArray.add(22);
        sortableArray.add(23);
        sortableArray.add(2);

        sortableArray.mergeSort(4);

        int[] expectedArray = new int[8];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        expectedArray[2] = 17;
        expectedArray[3] = 22;
        expectedArray[4] = 23;


    }
}


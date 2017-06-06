import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SorteableArrayTest {

    @Test
    public void whenTheSorteableArrayIsCreatedIsEmpty() {
        SorteableArray sorteableArray = new SorteableArray(5);
        assertTrue(sorteableArray.isEmpty());
    }

    @Test
    public void whenTheSorteableArrayIsCreatedAndAddAnElementNotIsEmpty() {
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(99);
        assertFalse(sorteableArray.isEmpty());
    }

    @Test
    public void whenSendContainsToSorteableArrayReturnTrue(){
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(1);
        sorteableArray.add(2);
        sorteableArray.add(3);
        assertTrue(sorteableArray.contains(2));
    }

    @Test
    public void whenSendContainsToSorteableArrayReturnFalse(){
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(1);
        sorteableArray.add(2);
        sorteableArray.add(3);
        assertFalse(sorteableArray.contains(4));
    }

    @Test
    public void whenSendPeekToSorteableArrayReturnCorrectNumber(){
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(1);
        sorteableArray.add(2);
        sorteableArray.add(3);
        assertEquals(1, sorteableArray.peek());
    }

    @Test
    public void whenSendPopToSorteableArrayReturnCorrectNumberAndTheNumberNotExistsInSorteableArray(){
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(3);
        sorteableArray.add(4);
        sorteableArray.add(5);
        assertTrue(sorteableArray.contains(3));

        int elementPop = sorteableArray.pop();

        assertEquals(3, elementPop);
        assertFalse(sorteableArray.contains(3));
    }

    @Test
    public void whenSendSizeToSorteableArrayWithoutElementsReturnZero(){
        SorteableArray sorteableArray = new SorteableArray(5);
        assertEquals(0, sorteableArray.size());
    }

    @Test
    public void whenSendSizeToSorteableArrayWithTwoElementsReturnTwo(){
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(4);
        sorteableArray.add(2);
        assertEquals(2, sorteableArray.size());
    }

    @Test
    public void whenAddAnElementToSorteableArrayNowThisContainsThisElement(){
        SorteableArray sorteableArray = new SorteableArray(4);
        assertFalse(sorteableArray.contains(10));

        sorteableArray.add(10);

        assertTrue(sorteableArray.contains(10));
    }

    @Test
    public void whenAddAnElementToFullSorteableArrayThisDuplicatesHisArrayLenght(){
        SorteableArray sorteableArray = new SorteableArray(4);
        sorteableArray.add(10);
        sorteableArray.add(11);
        sorteableArray.add(12);
        sorteableArray.add(13);
        assertTrue(sorteableArray.array().length == 4);

        sorteableArray.add(14);

        assertTrue(sorteableArray.array().length == 8);
    }

    @Test
    public void whenSortASorteableArrayReturnTheArrayOrdered() throws InterruptedException{
        SorteableArray sorteableArray = new SorteableArray(5);
        sorteableArray.add(17);
        sorteableArray.add(1);
        sorteableArray.add(22);
        sorteableArray.add(23);
        sorteableArray.add(2);

        sorteableArray.mergeSort(2);

        assertEquals(1, sorteableArray.getInPosition(0));
        assertEquals(2, sorteableArray.getInPosition(1));
        assertEquals(17, sorteableArray.getInPosition(2));
        assertEquals(22, sorteableArray.getInPosition(3));
        assertEquals(23, sorteableArray.getInPosition(4));
    }
}


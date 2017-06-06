import org.junit.Test;
import org.junit.Before;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ConcurrenceTest {

  private SorteableArray sortedSorteableArrayWithTenElements;
  private SorteableArray sortedSorteableArrayOneHundredElements;
  private SorteableArray sortedSorteableArrayAThousandElements;
  private SorteableArray sortedSorteableArrayTenThousandElements;
  private SorteableArray unsortedSorteableArraWithTenElements;
  private SorteableArray unsortedSorteableArrayOneHundredElements;
  private SorteableArray unsortedSorteableArrayAThousandElements;
  private SorteableArray unsortedSorteableArrayTenThousandElements;
  private Integer nElements;

  @Before
  public void setUp() {
    this.sortedSorteableArrayWithTenElements = new SorteableArray(10);
    this.unsortedSorteableArraWithTenElements = new SorteableArray(10);
    this.sortedSorteableArrayOneHundredElements = new SorteableArray(100);
    this.unsortedSorteableArrayOneHundredElements = new SorteableArray(100);
    this.sortedSorteableArrayAThousandElements = new SorteableArray(1000);
    this.unsortedSorteableArrayAThousandElements = new SorteableArray(1000);
    this.sortedSorteableArrayTenThousandElements = new SorteableArray(10000);
    this.unsortedSorteableArrayTenThousandElements = new SorteableArray(10000);
  }

  private void fillSortedCList(int maxAmount,SorteableArray sortedSorteableArray) {
    for (int i = 1; i <= maxAmount; i++) {
      sortedSorteableArray.add(i);
    }
  }

  private void fillUnsortedCList(int maxAmount,SorteableArray sortedSorteableArray) {
    for (int i = maxAmount; i >= 1; i--) {
      sortedSorteableArray.add(i);
    }
  }

  private boolean compareArray(int [] a1, int [] a2){
    boolean equals = false;
    if(a1.length == a2.length){
      int emembers =0;
      for(int i=0 ; i < a1.length ; i++){
        if(a1[i] == a2[i])
          emembers++;
      }
      if(emembers == a1.length)
        equals=true;
    }
    return equals;
  }

  @Test
  public void tenElementsOneThread() throws InterruptedException {
    nElements = 10;
    fillSortedCList(nElements, sortedSorteableArrayWithTenElements);
    fillUnsortedCList(nElements, unsortedSorteableArraWithTenElements);
    unsortedSorteableArraWithTenElements.mergeSort(1);
    assertTrue(compareArray(unsortedSorteableArraWithTenElements.array(), sortedSorteableArrayWithTenElements.array()));
  }

  @Test
  public void tenElementsThreeThreads() throws InterruptedException {
    nElements = 10;
    fillSortedCList(nElements, sortedSorteableArrayWithTenElements);
    fillUnsortedCList(nElements, unsortedSorteableArraWithTenElements);
    unsortedSorteableArraWithTenElements.mergeSort(3);
    assertTrue(compareArray(unsortedSorteableArraWithTenElements.array(), sortedSorteableArrayWithTenElements.array()));
  }

  @Test
  public void hundredElementsOneThread() throws InterruptedException {
    nElements = 100;
    fillSortedCList(nElements, sortedSorteableArrayOneHundredElements);
    fillUnsortedCList(nElements, unsortedSorteableArrayOneHundredElements);
    unsortedSorteableArrayOneHundredElements.mergeSort( 1);
    assertTrue(compareArray(unsortedSorteableArrayOneHundredElements.array(), sortedSorteableArrayOneHundredElements.array()));
  }

  @Test
  public void hundredElementsThreeThreads() throws InterruptedException {
    nElements = 100;
    fillSortedCList(nElements, sortedSorteableArrayOneHundredElements);
    fillUnsortedCList(nElements, unsortedSorteableArrayOneHundredElements);
    unsortedSorteableArrayOneHundredElements.mergeSort(3);
    assertTrue(compareArray(unsortedSorteableArrayOneHundredElements.array(), sortedSorteableArrayOneHundredElements.array()));
  }

  @Test
  public void thousandElementsOneThread() throws InterruptedException {
    nElements = 1000;
    fillSortedCList(nElements, sortedSorteableArrayAThousandElements);
    fillUnsortedCList(nElements, unsortedSorteableArrayAThousandElements);
    unsortedSorteableArrayAThousandElements.mergeSort(1);
    assertTrue(compareArray(unsortedSorteableArrayAThousandElements.array(), sortedSorteableArrayAThousandElements.array()));
  }

  @Test
  public void thousandElementsThreeThreads() throws InterruptedException {
    nElements = 1000;
    fillSortedCList(nElements, sortedSorteableArrayAThousandElements);
    fillUnsortedCList(nElements, unsortedSorteableArrayAThousandElements);
    unsortedSorteableArrayAThousandElements.mergeSort(3);
    assertTrue(compareArray(unsortedSorteableArrayAThousandElements.array(), sortedSorteableArrayAThousandElements.array()));
  }

  @Test
  public void tenThousandElementsOneThread() throws InterruptedException {
    nElements = 10000;
    fillSortedCList(nElements, sortedSorteableArrayTenThousandElements);
    fillUnsortedCList(nElements, unsortedSorteableArrayTenThousandElements);
    unsortedSorteableArrayTenThousandElements.mergeSort(1);
    assertTrue(compareArray(unsortedSorteableArrayTenThousandElements.array(), sortedSorteableArrayTenThousandElements.array()));
  }

  @Test
  public void tenThousandElementsThreeThreads() throws InterruptedException {
    nElements = 10000;
    fillSortedCList(nElements, sortedSorteableArrayTenThousandElements);
    fillUnsortedCList(nElements, unsortedSorteableArrayTenThousandElements);
    unsortedSorteableArrayTenThousandElements.mergeSort(3);
    assertTrue(compareArray(unsortedSorteableArrayTenThousandElements.array(), sortedSorteableArrayTenThousandElements.array()));
  }



}

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RangeTest {

  private RangeOfWork rangeOfWork;

  @Test
  public void rangeIsValid(){
    rangeOfWork = new RangeOfWork(0, 5);
    assertTrue(rangeOfWork.isValid());
  }

  @Test
  public void rangeIsNotValid(){
    rangeOfWork = new RangeOfWork(-1, 5);
    assertFalse(rangeOfWork.isValid());
  }

  @Test
  public void correctSize() {
    rangeOfWork = new RangeOfWork(2, 5);
    assertSame(rangeOfWork.size(), 3);
  }

  @Test
  public void rangeNotEmpty() {
    rangeOfWork = new RangeOfWork(1, 5);
    assertFalse(rangeOfWork.isEmpty());
  }

  @Test
  public void rangeIsEmpty() {
    rangeOfWork = new RangeOfWork(5, 4);
    assertTrue(rangeOfWork.isEmpty());
  }

}

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RangeOfWorkTest {

  private RangeOfWork rangeOfWork;

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

  @Test
  public void whenCreateARangeOfWorkWithTheSameStartAndEndIsTheLast() {
    rangeOfWork = new RangeOfWork(2, 2);
    assertTrue(rangeOfWork.isTheLast());
  }

  @Test
  public void whenCreateARangeOfWorkWithDistinctStartAndEndNotIsTheLast() {
    rangeOfWork = new RangeOfWork(2, 5);
    assertFalse(rangeOfWork.isTheLast());
  }

  @Test
  public void whenGiveIsReadyToWorkToRangeOfWorkWithSonsThatReadyToWorkReturnTrue() {
    rangeOfWork = new RangeOfWork(0, 1);
    RangeOfWork rangeOfWorkLeft = new RangeOfWork(0, 0);
    RangeOfWork rangeOfWorkRight = new RangeOfWork(1, 1);
    rangeOfWork.addSon(rangeOfWorkLeft);
    rangeOfWork.addSon(rangeOfWorkRight);

    assertTrue(rangeOfWork.isReadyToWork());
  }

  @Test
  public void whenGiveIsReadyToWorkToRangeOfWorkWithSonsThatNotReadyToWorkReturnFalse() {
    rangeOfWork = new RangeOfWork(0, 2);
    RangeOfWork rangeOfWorkLeft = new RangeOfWork(0, 1);
    RangeOfWork rangeOfWorkRight = new RangeOfWork(1, 2);
    rangeOfWork.addSon(rangeOfWorkLeft);
    rangeOfWork.addSon(rangeOfWorkRight);

    assertFalse(rangeOfWork.isReadyToWork());
  }

}

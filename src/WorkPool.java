import java.util.ArrayList;
import java.util.stream.Collectors;

public class WorkPool {

    private ArrayList<RangeOfWork> rangeOfWorks;

    public WorkPool() {
        this.rangeOfWorks = new ArrayList<>();
    }

    public synchronized Boolean isEmpty() {
        return this.rangeOfWorks.isEmpty();

    }

    public synchronized void push(RangeOfWork rangeOfWork) {
        rangeOfWorks.add(rangeOfWork);
        notifyAll();
    }

    public synchronized RangeOfWork getFirstReadyToWork() throws InterruptedException {
        while(!hayAlMenosUnoQueEsteReadyToWork())
            wait();
        RangeOfWork rangeOfWork = firstRangeOfWorkReadyToWork();
        this.rangeOfWorks.remove(rangeOfWork);
        notifyAll();
        return rangeOfWork;
    }

    private RangeOfWork firstRangeOfWorkReadyToWork() {
        return rangeOfWorks.stream().filter(aRange -> aRange.readyToWork()).findFirst().get();
    }

    public synchronized boolean hayAlMenosUnoQueEsteReadyToWork() {
        return rangeOfWorks.stream().anyMatch(aRange-> aRange.readyToWork());

    }

    public  void clean() {
        this.rangeOfWorks =
                rangeOfWorks.stream().filter(aRange -> !aRange.isTheLast()).collect(Collectors.toCollection(ArrayList::new));

    }

    public Integer quantityOfWork() {
        return this.rangeOfWorks.size();
    }
}
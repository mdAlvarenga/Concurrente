import java.util.ArrayList;
import java.util.stream.Collectors;

public class WorkPool {

    private ArrayList<RangeOfWork> rangeOfWorks;

    public WorkPool() {
        this.rangeOfWorks = new ArrayList<>();
    }

    public void push(RangeOfWork rangeOfWork) {
        rangeOfWorks.add(rangeOfWork);
    }

    public synchronized RangeOfWork getFirstReadyToWork() throws InterruptedException {
        while(!atLeastOneIsReady())
            wait();
        RangeOfWork rangeOfWork = firstRangeOfWorkReadyToWork();
        this.rangeOfWorks.remove(rangeOfWork);
        notifyAll();
        return rangeOfWork;
    }

    private RangeOfWork firstRangeOfWorkReadyToWork() {
        return rangeOfWorks.stream().filter(aRange -> aRange.isReadyToWork()).findFirst().get();
    }

    public synchronized boolean atLeastOneIsReady() {
        return rangeOfWorks.stream().anyMatch(aRange-> aRange.isReadyToWork());
    }

    public  void cleanTrivialRanges() {
        this.rangeOfWorks = this.rangeOfWorks
                                    .stream()
                                    .filter(aRange -> !aRange.isTheLast())
                                    .collect(Collectors.toCollection(ArrayList::new));
    }

    public int quantityOfWork() {
        return this.rangeOfWorks.size();
    }
}
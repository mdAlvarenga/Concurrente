import java.util.ArrayList;

public class WorkPool {

    private ArrayList<RangeOfWork> rangeOfWorks;

    public WorkPool() {
        this.rangeOfWorks = new ArrayList<RangeOfWork>();
    }

    public Boolean isEmpty() {
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

    private boolean hayAlMenosUnoQueEsteReadyToWork() {
        return rangeOfWorks.stream().anyMatch(aRange-> aRange.readyToWork());

    }

}
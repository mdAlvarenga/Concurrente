import java.util.ArrayList;

public class RangeOfWork {

    private int start;
    private int end;
    private ArrayList<RangeOfWork> sons;
    private boolean isSorted;

    public RangeOfWork(int start, int end) {
        this.start = start;
        this.end = end;
        this.sons = new ArrayList<>();
        this.isSorted = isTheLast();
    }

    public int size() {
        return this.end - this.start;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public boolean isTheLast() {
        return this.start() == this.end() ;
    }

    public boolean readyToWork() {
        return !isSorted()&& (isTheLast() || allSonsAreSortered());
    }

    private boolean allSonsAreSortered() {
        return sons.stream().allMatch(aRange -> aRange.isSorted());
    }

    public void addSon(RangeOfWork rangeOfWork) {
        this.sons.add(rangeOfWork);
    }

    public boolean isSorted() {
        return isSorted;
    }

    public RangeOfWork getFirstSon() {
        return sons.get(0);
    }

    public RangeOfWork getSecondSon() {
        return sons.get(1);
    }

    public synchronized void finishOrder() {
        this.isSorted = true;
    }
}

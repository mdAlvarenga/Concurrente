import java.util.ArrayList;
import java.util.List;

public class RangeOfWork {

    private int start;
    private int end;
    private List<RangeOfWork> sons;
    private boolean isSorted;

    public RangeOfWork(int start, int end) {
        this.start = start;
        this.end = end;
        this.sons = new ArrayList<>();
        this.isSorted = false;
    }

    public int size() {
        return this.end - this.start;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public boolean isTheLast() {
        return this.getStart() == getEnd() ;
    }

    public boolean readyToWork() {
        return !isSorted()&& (isTheLast() || allSonsIsSortered());
    }

    private boolean allSonsIsSortered() {
        return sons.stream().allMatch(aRange -> aRange.isSorted());
    }

    public void agregarHijo(RangeOfWork rangeOfWork) {
        this.sons.add(rangeOfWork);
    }

    public boolean isSorted() {
        return isSorted;
    }

    public RangeOfWork getSecondSon() {
        return sons.get(1);
    }


    public RangeOfWork getFirstSon() {
        return sons.get(0);
    }

    public void finishOrder() {
        this.isSorted = true;
    }
}

public class RangeOfWork {

    private int start;
    private int end;
    private RangeOfWork father;

    public RangeOfWork(int newStart, int newEnd, RangeOfWork father) {
        this.start = newStart;
        this.end = newEnd;
        this.father = father;
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
}
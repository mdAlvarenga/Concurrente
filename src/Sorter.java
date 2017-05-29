public class Sorter extends Thread {

    private SorteableArray sorteableArray;
    private WorkPool workPool;

    public Sorter(SorteableArray sorteableArray, WorkPool WorkPool) {
        this.sorteableArray = sorteableArray;
        this.workPool = WorkPool;
    }

    private void mergeSortWorker(WorkPool workPool) throws InterruptedException {
        while(!workPool.isEmpty()) {
            RangeOfWork rangeToWork = workPool.pop();
            //Acá hay que hacer el sort
        }
    }

    @Override
    public void run() {
        try {
            this.mergeSortWorker(workPool);
        }
        catch (InterruptedException e) {}
    }
}
public class Sorter extends Thread {

    private SorteableArray sorteableArray;
    private WorkPool workPool;
    private JobStopper jobStopper;

    public Sorter(SorteableArray sorteableArray, WorkPool WorkPool, JobStopper jobStopper) {
        this.sorteableArray = sorteableArray;
        this.workPool = WorkPool;
        this.jobStopper = jobStopper;
    }

    private void mergeSortWorker(WorkPool workPool) throws InterruptedException {
        while(workPool.hayAlMenosUnoQueEsteReadyToWork()) {
            RangeOfWork rangeToWork = workPool.getFirstReadyToWork();
            System.out.println("Soy el thread:" +Thread.currentThread()+" y Tome el trabajo que tiene Start " + rangeToWork.start() + " hasta " + rangeToWork.end());
            if(rangeToWork.isTheLast()){
                rangeToWork.finishOrder();
               return;
            }
            RangeOfWork firstRangeSon = rangeToWork.getFirstSon();
            RangeOfWork secondRangeSon = rangeToWork.getSecondSon();

            SorteableArray firstSorteableArray = createSorteableFrom(firstRangeSon);
            SorteableArray secondSorteableArray = createSorteableFrom(secondRangeSon);

            this.sorteableArray.replaceWithArrayBetween(merge(firstSorteableArray, secondSorteableArray),firstRangeSon.start(),secondRangeSon.end());
            rangeToWork.finishOrder();
            this.jobStopper.decrease();
        }
        System.out.println("Soy el thread:" +Thread.currentThread()+" y ya no tengo mas trabajo");

    }

    private SorteableArray merge(SorteableArray aSorteableArray, SorteableArray otherSorteableArray) {

        SorteableArray bothArraysMerged = new SorteableArray(aSorteableArray.size()+ otherSorteableArray.size());
        while ( !aSorteableArray.isEmpty() && !otherSorteableArray.isEmpty()){
            if(aSorteableArray.peek() <= otherSorteableArray.peek())
                bothArraysMerged.add(aSorteableArray.pop());
            else
                bothArraysMerged.add(otherSorteableArray.pop());
        }
        bothArraysMerged.addAll(aSorteableArray);
        bothArraysMerged.addAll(otherSorteableArray);
        return bothArraysMerged;
    }

    private SorteableArray createSorteableFrom(RangeOfWork aRange) {

        SorteableArray sorteableArray;
        if(aRange.isTheLast()){
            sorteableArray = new SorteableArray(1);
            sorteableArray.add(this.sorteableArray.getInPosition(aRange.start()));
        }else {
            sorteableArray = new SorteableArray(aRange.end() - aRange.start());
            for (int i = aRange.start(); i <= aRange.end(); i++) {
                sorteableArray.add(this.sorteableArray.getInPosition(i));
            }
        }

        return sorteableArray;
    }

    @Override
    public void run() {
        try {
            this.mergeSortWorker(workPool);
        }
        catch (InterruptedException catchedError) {
            catchedError.printStackTrace();
        }
    }
}
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
            System.out.println("Soy el thread:" +Thread.currentThread()+" y Tome el trabajo que tiene Start " + rangeToWork.getStart() + " hasta " + rangeToWork.getEnd());
            if(rangeToWork.isTheLast()){
                rangeToWork.finishOrder(workPool);
               return;
            }
            RangeOfWork firstRangeSon = rangeToWork.getFirstSon();
            RangeOfWork secondRangeSon = rangeToWork.getSecondSon();

            SorteableArray firstSorteableArray = createSorteableFrom(firstRangeSon);
            SorteableArray secondSorteableArray = createSorteableFrom(secondRangeSon);

            this.sorteableArray.replaceArrayBetween(merge(firstSorteableArray, secondSorteableArray),firstRangeSon.getStart(),secondRangeSon.getEnd());
            rangeToWork.finishOrder(workPool);
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
            sorteableArray.add(this.sorteableArray.getInPosition(aRange.getStart()));
        }else {
            sorteableArray = new SorteableArray(aRange.getEnd() - aRange.getStart());
            for (int i = aRange.getStart(); i <= aRange.getEnd(); i++) {
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
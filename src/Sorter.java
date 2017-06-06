public class Sorter extends Thread {

    private SorteableArray sorteableArray;
    private WorkPool workPool;
    private JobStopper jobStopper;

    public Sorter(SorteableArray sorteableArray, WorkPool WorkPool, JobStopper jobStopper) {
        this.sorteableArray = sorteableArray;
        this.workPool = WorkPool;
        this.jobStopper = jobStopper;
    }

    private void mergeSortWorker() throws InterruptedException {
        while(workPool.atLeastOneIsReady()) {
            RangeOfWork rangeToWork = workPool.getFirstReadyToWork();
            if(rangeToWork.isTheLast()){
                rangeToWork.finishOrder();
                return;
            }
            RangeOfWork firstRangeSon = rangeToWork.getFirstSon();
            RangeOfWork secondRangeSon = rangeToWork.getSecondSon();

            this.sorteableArray.replaceWithArrayBetween(
                                    merge(createSorteableFrom(firstRangeSon), createSorteableFrom(secondRangeSon)),
                                    firstRangeSon.start(),
                                    secondRangeSon.end());

            rangeToWork.finishOrder();
            this.jobStopper.decrease();
        }
    }

    private SorteableArray merge(SorteableArray aSorteableArray, SorteableArray otherSorteableArray) {
        SorteableArray mergedSorteableArray = new SorteableArray(aSorteableArray.size()+ otherSorteableArray.size());
        while ( !aSorteableArray.isEmpty() && !otherSorteableArray.isEmpty()){
            if(aSorteableArray.peek() <= otherSorteableArray.peek())
                mergedSorteableArray.add(aSorteableArray.pop());
            else
                mergedSorteableArray.add(otherSorteableArray.pop());
        }
        mergedSorteableArray.addAll(aSorteableArray);
        mergedSorteableArray.addAll(otherSorteableArray);
        return mergedSorteableArray;
    }

    private SorteableArray createSorteableFrom(RangeOfWork aRange) {
        SorteableArray sorteableArray;
        if(aRange.isTheLast()){
            sorteableArray = new SorteableArray(1);
            sorteableArray.add(this.sorteableArray.getInPosition(aRange.start()));
        }else {
            sorteableArray = new SorteableArray(aRange.end() - aRange.start());
            for (int position = aRange.start(); position <= aRange.end(); position++) {
                sorteableArray.add(this.sorteableArray.getInPosition(position));
            }
        }

        return sorteableArray;
    }

    @Override
    public void run() {
        try {
            this.mergeSortWorker();
        }
        catch (InterruptedException catchedError) {
            catchedError.printStackTrace();
        }
    }
}
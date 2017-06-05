public class Sorter extends Thread {

    private SorteableArray sorteableArray;
    private WorkPool workPool;

    public Sorter(SorteableArray sorteableArray, WorkPool WorkPool) {
        this.sorteableArray = sorteableArray;
        this.workPool = WorkPool;
    }

    private void mergeSortWorker(WorkPool workPool) throws InterruptedException {
        while(!workPool.isEmpty()) {
            RangeOfWork rangeToWork = workPool.getFirstReadyToWork();
            if(rangeToWork.isTheLast()){
               return;
            }
            RangeOfWork firstRangeSon = rangeToWork.getFirstSon();
            RangeOfWork secondRangeSon = rangeToWork.getSecondSon();

            SorteableArray firstSorteableArray = createSorteableFrom(firstRangeSon.getStart(), firstRangeSon.getEnd());
            SorteableArray secondSorteableArray = createSorteableFrom(secondRangeSon.getStart(), secondRangeSon.getEnd());

            this.sorteableArray.replaceArrayBetween(merge(firstSorteableArray, secondSorteableArray),firstRangeSon.getStart(),secondRangeSon.getEnd());
            rangeToWork.finishOrder();
        }
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

    /*
    buscar en el sorteableArray, los elementos entre esos dos indices, y meterlos en un array auxiliar.
     */
    private SorteableArray createSorteableFrom(int start, int end) {
        SorteableArray r = new SorteableArray(end - start);
        for (int i = start; i <= end; i++) {
            r.add(sorteableArray.getInPosition(i));
        }
        return r;
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
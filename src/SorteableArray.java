import java.util.ArrayList;
import java.util.List;

public class SorteableArray {

    private int[] array;
    private int quantityOfElements;

    public SorteableArray(int sizeOfArray) {
        this.array = new int[sizeOfArray];
        this.quantityOfElements = 0;
    }

    public int[] array() {
        return this.array;
    }

    public synchronized int size() {
        return this.quantityOfElements;
    }

    public synchronized Boolean isEmpty() {
        return this.quantityOfElements == 0;
    }

    public synchronized Boolean contains(int anElement) {
        boolean contains = false;
        for (int index = 0; index < this.size(); index++) {
            contains = array[index] == anElement || contains;
        }
        return contains;
    }

    private void safeAdd(int anElement) {
        this.array[quantityOfElements] = anElement;
        quantityOfElements++;
    }

    private void duplicateArray(){
        int[] backUpArray = this.array;
        this.array = new int[backUpArray.length * 2];
        for (int indexToAdd = 0; indexToAdd < backUpArray.length; indexToAdd++) {
            this.array[indexToAdd] = backUpArray[indexToAdd];
        }
    }

    private int safePop(){
        int firstElement = array[0];
        int[] arrayBackUp = new int[array.length];
        for (int indexToAdd = 0; indexToAdd < arrayBackUp.length-1; indexToAdd++) {
            arrayBackUp[indexToAdd] = array[indexToAdd+1];
        }
        this.array = arrayBackUp;
        quantityOfElements--;
        return firstElement;
    }

    public synchronized void add(int toAdd) {
        if (quantityOfElements == this.array.length)
            duplicateArray();
        safeAdd(toAdd);
        notifyAll();
    }

    public synchronized int peek() {
        while (this.isEmpty()) {
            try {
                wait();
            }catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
        return array[0];
    }

    public synchronized int pop() {
        while (this.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
        return safePop();
    }

    public synchronized void replaceWithArrayBetween(SorteableArray arrayToReplace, int startPositionToReplace, int endPositionToReplace) {
        int indexForExternalArray = 0;

        for (int toReplaceIndex = startPositionToReplace; toReplaceIndex <= endPositionToReplace; toReplaceIndex++) {
            this.array[toReplaceIndex] = arrayToReplace.getInPosition(indexForExternalArray);
            indexForExternalArray++;
        }
    }

    public synchronized void addAll(SorteableArray elementsToAdd) {
        int[] arrayToAdd = elementsToAdd.array();
        for (int indexToAdd = 0; indexToAdd < elementsToAdd.size(); indexToAdd++) {
            this.add(arrayToAdd[indexToAdd]);
        }
    }

    public void mergeSort( int threadQuantity) throws InterruptedException {
        if (this.size() > 1) {
            WorkPool workPool = createWorkPoolWithUnitsOfWork();

            JobStopper jobStopper = new JobStopper(workPool.quantityOfWork());
            initializeThreadPool(workPool, threadQuantity, jobStopper);

            jobStopper.waitToFinalize();
        }
    }

    private void initializeThreadPool( WorkPool workPool, Integer threadQuantity, JobStopper jobStopper) {
        List<Sorter> threadPool = new ArrayList<>();
        for (int i = 0; i < threadQuantity; i++) {
            threadPool.add(new Sorter(this, workPool, jobStopper));
        }
        threadPool.stream().forEach(aThread -> aThread.start());
    }

    private WorkPool createWorkPoolWithUnitsOfWork() {
        WorkPool workPool = new WorkPool();
        RangeOfWork principalRangeOFWork = new RangeOfWork(0, size() - 1);
        RangeOfWork leftRangeOfWork = new RangeOfWork(0, size() / 2);
        RangeOfWork rightRangeOfWork = new RangeOfWork(size() / 2 + 1, size() - 1);
        principalRangeOFWork.addSon(leftRangeOfWork);
        principalRangeOFWork.addSon(rightRangeOfWork);
        workPool.push(principalRangeOFWork);
        workPool.push(leftRangeOfWork);
        workPool.push(rightRangeOfWork);

        createUnitsOfWorks(workPool, leftRangeOfWork);
        createUnitsOfWorks(workPool, rightRangeOfWork);
        workPool.clean();
        return workPool;
    }

    private void createUnitsOfWorks(WorkPool workPool, RangeOfWork aRangeOfWork) {
        if(!(aRangeOfWork.isTheLast())){
            RangeOfWork leftRangeOfWork = new RangeOfWork(aRangeOfWork.start(), (aRangeOfWork.start())+ aRangeOfWork.size() / 2);
            RangeOfWork rightRangeOfWork = new RangeOfWork((aRangeOfWork.start()) + (aRangeOfWork.size() / 2) + 1, aRangeOfWork.end());
            aRangeOfWork.addSon(leftRangeOfWork);
            aRangeOfWork.addSon(rightRangeOfWork);
            workPool.push(leftRangeOfWork);
            workPool.push(rightRangeOfWork);
            createUnitsOfWorks(workPool, leftRangeOfWork);
            createUnitsOfWorks(workPool, rightRangeOfWork);
        }
    }

    public int getInPosition(int aPosition) {
        return this.array[aPosition];
    }

    public static void main(String[] args) throws InterruptedException {
        int i = 50;
        SorteableArray sorteableArray = new SorteableArray(50);
        while(i > 0){
            sorteableArray.add((int)(Math.random()*100+1));;
            i--;
        }
        System.out.print("Antes del orden ");
        Printer.printList(sorteableArray.array);

        sorteableArray.mergeSort(10);

        System.out.print("despues del orden :");
        Printer.printList(sorteableArray.array());
    }
}
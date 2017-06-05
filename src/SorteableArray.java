import java.util.ArrayList;
import java.util.List;

public class SorteableArray {

    private int[] array;
    private int cantidadDeElementosEnElArray;

    public SorteableArray(int tamanio) {
        this.array = new int[tamanio];
        this.cantidadDeElementosEnElArray = 0;
    }

    public int[] array() {
        return this.array;
    }

    public synchronized int size() {
        return this.cantidadDeElementosEnElArray;
    }

    public synchronized Boolean isEmpty() {
        return this.cantidadDeElementosEnElArray == 0;
    }

    public synchronized Boolean contains(int elem) {
        boolean contain = false;
        for (int i = 0; i < this.size(); i++) {
            contain = array[i] == elem;
        }
        return contain;
    }

    private void safeAdd(int valor) {
        this.array[cantidadDeElementosEnElArray] = valor;
        cantidadDeElementosEnElArray++;

    }

    private void AmpliarArray(){
        int[] backUpArray = this.array;
        this.array = new int[backUpArray.length * 2];
        for (int i = 0; i < backUpArray.length; i++) {
            this.array[i] = backUpArray[i];
        }
    }

    private int primerElementoDelArray(){
        int first = array[0];
        int[] copiaArray = new int[array.length];
        for (int i = 0; i < copiaArray.length-1; i++) {
            copiaArray[i] = array[i+1];
        }
        this.array = copiaArray;
        cantidadDeElementosEnElArray--;
        return first;
    }

    public synchronized void add(int toAdd) {
        if (cantidadDeElementosEnElArray == this.array.length)
            AmpliarArray();
        safeAdd(toAdd);
        notifyAll();
    }

    public synchronized int peek() {
        while (this.isEmpty()) {
            try {
                wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return array[0];
    }

    public synchronized int pop() {
        while (this.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return primerElementoDelArray();
    }

    public synchronized void replaceArrayBetween(SorteableArray arrayToReplace, int startPositionToReplace, int endPositionToReplace) {
        int indexForExternalArray = 0;

        for (int toReplaceIndex = startPositionToReplace; toReplaceIndex <= endPositionToReplace; toReplaceIndex++) {
            this.array[toReplaceIndex] = arrayToReplace.getInPosition(indexForExternalArray);
            indexForExternalArray++;
        }
        notifyAll();
    }

    public synchronized void addAll(SorteableArray elementsToAdd) {
        int[] arrayToAdd = elementsToAdd.array();
        for (int indexToAdd = 0; indexToAdd < elementsToAdd.size(); indexToAdd++) {
            this.add(arrayToAdd[indexToAdd]);
        }
    }

    public void mergeSort( int threadQuantity) throws InterruptedException {
        if (this.size() > 1) {
            WorkPool allUnitsOfWorks = createAllUnitsOfWorks(this);
            allUnitsOfWorks.clean();
            JobStopper jobStopper = new JobStopper(allUnitsOfWorks.quantityOfWork());
            initializeThreadPool(this, allUnitsOfWorks, threadQuantity, jobStopper);
            jobStopper.waitToFinalize();
            System.out.print("se acabo todo, todillo");
        }
    }

    private void initializeThreadPool(SorteableArray sorteableArray, WorkPool workPool, Integer threadQuantity, JobStopper jobStopper) {
        List<Sorter> threadPool = new ArrayList<>();
        for (int i = 0; i < threadQuantity; i++) {
            threadPool.add(new Sorter(sorteableArray, workPool, jobStopper));
        }
        threadPool.stream().forEach(a -> a.start());
    }

    private WorkPool createAllUnitsOfWorks(SorteableArray sorteableArray) {
        WorkPool workPool = new WorkPool();
        RangeOfWork principalRangeOFWork = new RangeOfWork(0, sorteableArray.size() - 1);
        RangeOfWork leftRangeOfWork = new RangeOfWork(0, sorteableArray.size() / 2);
        RangeOfWork rightRangeOfWork = new RangeOfWork(sorteableArray.size() / 2 + 1, sorteableArray.size() - 1);
        principalRangeOFWork.agregarHijo(leftRangeOfWork);
        principalRangeOFWork.agregarHijo(rightRangeOfWork);
        workPool.push(principalRangeOFWork);
        workPool.push(leftRangeOfWork);
        workPool.push(rightRangeOfWork);

        createUnitsOfWorks(workPool, leftRangeOfWork);
        createUnitsOfWorks(workPool, rightRangeOfWork);

        return workPool;
    }

    private void createUnitsOfWorks(WorkPool workPool, RangeOfWork aRangeOfWork) {
        if(!(aRangeOfWork.isTheLast())){
            RangeOfWork leftRangeOfWork = new RangeOfWork(aRangeOfWork.getStart(), (aRangeOfWork.getStart())+aRangeOfWork.size() / 2);
            RangeOfWork rightRangeOfWork = new RangeOfWork((aRangeOfWork.getStart()) + (aRangeOfWork.size() / 2) + 1, aRangeOfWork.getEnd());
            aRangeOfWork.agregarHijo(leftRangeOfWork);
            aRangeOfWork.agregarHijo(rightRangeOfWork);
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
        int i = 1000;
        SorteableArray sorteableArray = new SorteableArray(1000);
        while(i > 0){
            sorteableArray.add((int)(Math.random()*10000+1));;
            i--;
        }
        System.out.print("Antes del orden ");
        Printer.printList(sorteableArray.array);

        sorteableArray.mergeSort(10);

        System.out.print("despues del orden :");
        Printer.printList(sorteableArray.array());
    }
}
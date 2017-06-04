import java.util.ArrayList;
import java.util.List;

public class SorteableArray {

    private int[] array;
    private int cantidadDeElementosEnElArray;

    public SorteableArray(int tamanio) {
        this.array = new int[tamanio];
        this.cantidadDeElementosEnElArray = 0;
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

    private synchronized void siHayLugarEnElArrayAgregar(int valor) {
        array[cantidadDeElementosEnElArray] = valor;
        cantidadDeElementosEnElArray++;
        notifyAll();
    }

    private synchronized void agregarANuevoArraySiNoHayLugar(int valor){
        int[] copiaArray = array;
        array = new int[copiaArray.length * 2];
        for (int i = 0; i < copiaArray.length; i++) {
            array[i] = copiaArray[i];
        }
        array[cantidadDeElementosEnElArray] = valor;
        cantidadDeElementosEnElArray++;
    }

    private synchronized int primerElementoDelArray(){
        int first = array[0];
        int[] copiaArray = new int[array.length];
        for (int i = 0; i < copiaArray.length-1; i++) {
            copiaArray[i] = array[i+1];
        }
        this.array = copiaArray;
        cantidadDeElementosEnElArray--;
        return first;
    }

    public synchronized void add(int valor) {
        if (cantidadDeElementosEnElArray < this.array.length)
            siHayLugarEnElArrayAgregar(valor);
        else
            agregarANuevoArraySiNoHayLugar(valor);
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

    public void mergeSort( int threadQuantity) throws InterruptedException {
        if (this.size() > 1) {
            WorkPool allUnitsOfWorks = createAllUnitsOfWorks(this); //Primero creamos todas las unid de trabajo posibles
            //Creamos todos los threads que podamos, que van a ir trabajando a medida que se vayan desocupando,
            // con las unid de trabajo que tengan todas sus "hijas" finalizadas
            initializeThreadPool(this, allUnitsOfWorks, threadQuantity);
        }
    }

    private void initializeThreadPool(SorteableArray sorteableArray, WorkPool workPool, Integer threadQuantity) {
        List<Sorter> r = new ArrayList<Sorter>();
        for (int i = 0; i < threadQuantity; i++) {
            r.add(new Sorter(sorteableArray, workPool));
        }
        r.stream().forEach(a -> a.start());
    }

    /*
        Ya recibe un sorteableArray con dos o mas elementos.
     */
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

    public synchronized void addAll(SorteableArray elementsToAdd) {
            int[] arrayToAdd = elementsToAdd.array();
            for (int indexToAdd = 0; indexToAdd < elementsToAdd.size(); indexToAdd++) {
                this.add(arrayToAdd[indexToAdd]);
            }
        }

    public int[] array() {
        return this.array;
    }

    public synchronized void replaceArrayBetween(SorteableArray arrayToReplace, int startPositionToReplace, int endPositionToReplace) {
        int indexForExternalArray = 0;

        for (int toReplaceIndex = startPositionToReplace; toReplaceIndex <= endPositionToReplace; toReplaceIndex++) {
            this.array[toReplaceIndex] = arrayToReplace.getInPosition(indexForExternalArray);
            indexForExternalArray++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int i = 9;
        SorteableArray sorteableArray = new SorteableArray(9);
        while(i > 0){
            sorteableArray.add((int)(Math.random()*100+1));;
            i--;
        }
        System.out.print("Antes del orden ");
        Printer.printList(sorteableArray.array);

        sorteableArray.mergeSort(3);

        System.out.print("thread actual:");
        System.out.print(Thread.currentThread().getName());

        System.out.print("despues del orden :");
        Printer.printList(sorteableArray.array());
    }
}
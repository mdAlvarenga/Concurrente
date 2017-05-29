public class SorteableArray {

    private int[] array;
    private int cantidadDeElementosEnElArray;

    public SorteableArray(int tamanio) {
        this.array = new int[tamanio];
        this.cantidadDeElementosEnElArray = 0;
    }

    public int[] array(){
        return this.array;
    }

    public int size() {
        return this.cantidadDeElementosEnElArray;
    }

    public Boolean isEmpty() {
        return this.cantidadDeElementosEnElArray == 0;
    }

    public Boolean contains(int elem) {
        boolean contain = false;
        for (int i = 0; i < this.size(); i++) {
            contain = array[i] == elem;
        }
        return contain;
    }

    public synchronized void add(int valor) {
        if (cantidadDeElementosEnElArray < this.array.length)
            siHayLugarEnElArrayAgregar(valor);
        else
            agregarANuevoArraySiNoHayLugar(valor);
        notifyAll();
    }

    private void siHayLugarEnElArrayAgregar(int valor) {
        array[cantidadDeElementosEnElArray] = valor;
        cantidadDeElementosEnElArray++;
        notifyAll();
    }

    private void agregarANuevoArraySiNoHayLugar(int valor){
        int[] copiaArray = array;
        array = new int[copiaArray.length * 2];
        for (int i = 0; i < copiaArray.length; i++) {
            array[i] = copiaArray[i];
        }
        array[cantidadDeElementosEnElArray] = valor;
        cantidadDeElementosEnElArray++;
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

    private int primerElementoDelArray(){
        int first = array[0];
        int[] copiaArray = new int[array.length];
        for (int i = 0; i < copiaArray.length-1; i++) {
            copiaArray[i] = array[i+1];
        }
        replaceArrayWith(copiaArray);
        cantidadDeElementosEnElArray--;
        return first;
    }

    private void replaceArrayWith(int[] anArray) {
        this.array = anArray;
    }

    private void initializeThreadPool(SorteableArray sorteableArray, Integer threadQuantity, WorkPool workPool) {
        for (int i = 0; i < threadQuantity; i++) {
            new Sorter(sorteableArray, workPool).start();
        }
    }

    public void mergeSort(SorteableArray sorteableArray, int threadQuantity) throws InterruptedException {
        WorkPool workPool = createAllUnitsOfWorks(sorteableArray);
        initializeThreadPool(sorteableArray, threadQuantity, workPool);
        //matar a todos los putos threads!!!
    }

    private WorkPool createAllUnitsOfWorks(SorteableArray sorteableArray) {
        WorkPool workPool = new WorkPool();

        RangeOfWork leftRangeOfWork = new RangeOfWork(0, sorteableArray.size() / 2, null);
        RangeOfWork rightRangeOfWork = new RangeOfWork(sorteableArray.size() / 2 + 1, sorteableArray.size(), null);
        workPool.push(leftRangeOfWork);
        workPool.push(rightRangeOfWork);

        createUnitsOfWorks(workPool, leftRangeOfWork);
        createUnitsOfWorks(workPool, rightRangeOfWork);
        return workPool;
    }

    private void createUnitsOfWorks(WorkPool workPool, RangeOfWork aRangeOfWork) {
        if(!(aRangeOfWork.isTheLast())){
            RangeOfWork leftRangeOfWork = new RangeOfWork(aRangeOfWork.getStart(), (aRangeOfWork.getStart())+aRangeOfWork.size() / 2, aRangeOfWork);
            RangeOfWork rightRangeOfWork = new RangeOfWork((aRangeOfWork.getStart()) + (aRangeOfWork.size() / 2) + 1, aRangeOfWork.getEnd(), aRangeOfWork);
            workPool.push(leftRangeOfWork);
            workPool.push(rightRangeOfWork);

            createUnitsOfWorks(workPool, leftRangeOfWork);
            createUnitsOfWorks(workPool, rightRangeOfWork);
        }
    }

}

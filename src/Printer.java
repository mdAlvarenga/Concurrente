public class Printer {

    public static void printList(int[] list){
        System.out.println("Comienza el array");

        for (int i = 0; i <= list.length - 1; i++) {
            System.out.println(list[i] + " ");
        }
        System.out.println("Fin del array");
    }
}
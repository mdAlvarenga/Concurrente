public class Printer {

    public static void printList(int[] list){
        System.out.println("Begin the array: ");

        for (int index = 0; index <= list.length - 1; index++) {
            System.out.println(list[index] + " ");
        }
        System.out.println("End of array.");
    }
}
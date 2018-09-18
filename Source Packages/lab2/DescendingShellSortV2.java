package lab2;

import java.util.ArrayList;
import java.util.List;

/**
 * A program that sorts an array of size N given from the command line in
 * descending order using the shellsort algorithm and prints out the whole array
 * in each iteration of the inner loop. It also prints the number of swaps when
 * sorting the array. A new method "inversions" is also implemented which counts
 * all of the inversions in the array.
 * 
 * @author Antonio
 *
 */
public class DescendingShellSortV2 {

    private static boolean greater(Comparable first, Comparable second) {
        return first.compareTo(second) > 0;
    }

    private static void swap(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void show(Comparable[] array) {
        int i = 0;
        StringBuilder str = new StringBuilder();
        if (array.length == 0) {
            str.append("Empty list...");
            System.out.println(str.toString());
        }

        while (i < array.length - 1)
            str.append("[" + array[i++].toString() + "], ");
        str.append("[" + array[i] + "]");

        System.out.println(str.toString());
    }

    /**
     * This method will sort the given array using the shell sort algorithm. It will
     * first find the maximum possible interval for the array (gap sequence in form
     * of 3k + 1) in a while loop. Then the sorting will begin with the largest
     * h-sort where h is the biggest interval. The h-sort will decrement to a third
     * of the previous h for every outer while loop, until it finally will 1-sort
     * (insertion sort) the partially sorted array. In this implementation the array
     * and swaps will be printed out in each iteration of the inner loop.
     * 
     * @param array The array to be sorted. Must implement Comparable.
     */
    public static void shellSort(Comparable[] array) {
        int numberOfSwaps = 0;
        if (isSorted(array)) {
            System.out.println("Already sorted!");
            return;
        }
        int interval = 1;
        while (interval < array.length / 3)
            interval = interval * 3 + 1; // Get the largest possible h-sort interval (1,4,13,...)

        // Loop starting with the maximum interval until it becomes insertion sort
        while (interval > 0) {
            // h-sort the list
            for (int i = interval; i < array.length; i++) {
                for (int j = i; j >= interval && greater(array[j], array[j - interval]); j = j - interval) {
                    show(array);
                    swap(array, j, j - interval);
                    System.out.println("Swap number: " + ++numberOfSwaps);
                }
            }
            interval = interval / 3;
        }
        show(array);
        System.out.println("Total number of swaps was: " + numberOfSwaps);
    }

    /**
     * Checks if the list is sorted in descending order.
     * 
     * @param list
     * @return
     */
    public static boolean isSorted(Comparable[] array) {
        for (int i = 0; i < array.length - 1; i++)
            if (!greater(array[i], array[i + 1]))
                return false;
        return true;
    }

    /**
     * Iterates through the list using two for-loops and puts the inversions found
     * in a String-list which is printed out in the end. (Descending order).
     * 
     * @param array The array that will be checked for inversions.
     */
    public static void inversions(Comparable[] array) {
        List<String> inversionList = new ArrayList<String>();
        Comparable item;
        int numberOfInversions = 0;
        for (int i = 0; i < array.length - 1; i++) {
            item = array[i];
            for (int j = i + 1; j < array.length; j++) {
                if (!greater(item, array[j])) {
                    inversionList.add("[[" + i + "," + array[i] + "]," + "[" + j + "," + array[j] + "]]");
                    numberOfInversions++;
                }
            }
        }
        System.out.println("Total number of inversions: " + numberOfInversions);
        System.out.println(inversionList.toString());
    }

    /**
     * A test client where the user input is read and put to a comparable array and
     * then the inversions will be printed out and then finally shellsorted.
     * 
     * @param args User input from the command line where args[0] is the size N of
     *             the array and args[1] is the array in format:
     *             [X,X1,X2,X3,...,XN].
     */
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        Comparable[] array = new Comparable[size];
        StringBuilder strBuild = new StringBuilder();
        for (int i = 1; i < args[1].length() - 1; i++)
            strBuild.append(args[1].charAt(i));
        int j = 0;
        for (String str : strBuild.toString().split(",")) {
            array[j++] = Integer.parseInt(str);
        }
        inversions(array);
        System.out.println("Now sorting the array in descending order: ");
        shellSort(array);
    }
}

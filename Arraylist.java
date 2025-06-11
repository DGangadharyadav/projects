package dataStructure;

import java.util.ArrayList;

public class Arraylist<object> {
    public static int minOperations(ArrayList<Integer> arr) {
        int n = arr.size();
		System.out.println( "the array size of n is : " +n);
        int less = 0, more = 0;

        for (int i = 0; i < n - 1; i++) {
            if (i % 2 == 0) {
                if (arr.get(i) >= arr.get(i + 1)) {
                    less++;
                }
                if (arr.get(i) <= arr.get(i + 1)) {
                    more++;
                }
            } else {
                if (arr.get(i) <= arr.get(i + 1)) {
                    less++;
                }
                if (arr.get(i) >= arr.get(i + 1)) {
                    more++;
                }
            }
        }

        return Math.max(less, more);
        
    }
    

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        System.out.println(minOperations(arr));
        

    }
}


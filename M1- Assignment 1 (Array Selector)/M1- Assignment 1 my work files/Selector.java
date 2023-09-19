import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Ssai Sanjanna Ganji (szg0161@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  January 29, 2023
*
*/
public final class Selector {

    /**
     * Can't instantiate this class.
     *
     * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
     *
     */
    private Selector() { }


    /**
     * Selects the minimum value from the array a. This method
     * throws IllegalArgumentException if a is null or has zero
     * length. The array a is not changed by this method.
     */
    public static int min(int[] a) {
        if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int min = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] < min) { 
            min = a[i]; 
         }
      }
      return min;
    }


    /**
     * Selects the maximum value from the array a. This method
     * throws IllegalArgumentException if a is null or has zero
     * length. The array a is not changed by this method.
     */
    public static int max(int[] a) {
if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0]; 
      for (int i = 0; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i]; 
      }
      }
        return max;
      
    }


    /**
     * Selects the kth minimum value from the array a. This method
     * throws IllegalArgumentException if a is null, has zero length,
     * or if there is no kth minimum value. Note that there is no kth
     * minimum value if k < 1, k > a.length, or if k is larger than
     * the number of distinct values in the array. The array a is not
     * changed by this method.
     */
    public static int kmin(int[] a, int k) {
if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      int numEqual = 0; 
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] == a[i+1]) {
            numEqual++;
         }
      }
      if (k > a.length - numEqual) {
         throw new IllegalArgumentException();
      }
      
      int[] minArray = Arrays.copyOf(a, a.length); 
      
      Arrays.sort(minArray); 
    
      for (int i = 0; i < k - 1; i++) {
         if (minArray[i] == minArray[i+1]) { 
            k++;
         }
      }
      
      int kmin = minArray[k - 1];
      return kmin;
    }


    /**
     * Selects the kth maximum value from the array a. This method
     * throws IllegalArgumentException if a is null, has zero length,
     * or if there is no kth maximum value. Note that there is no kth
     * maximum value if k < 1, k > a.length, or if k is larger than
     * the number of distinct values in the array. The array a is not
     * changed by this method.
     */
    public static int kmax(int[] a, int k) {
        if (a == null) {
         throw new IllegalArgumentException();
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      if (k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      
      int numEqual = 0;
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] == a[i+1]) {
            numEqual++;
         }
      }
      
      if (k > a.length - numEqual) {
         throw new IllegalArgumentException();
      }
      
      int[] maxArray = Arrays.copyOf(a, a.length); 
      
      for (int i = 0; i < maxArray.length; i++) {
         maxArray[i] = maxArray[i] * -1;
      }
      Arrays.sort(maxArray); 
      for (int i = 0; i < maxArray.length; i++) {
         maxArray[i] = maxArray[i] * -1;
      }
      
      for (int i = 0; i < k - 1; i++) {
         if (maxArray[i] == maxArray[i+1]) { 
            k++;
         }
      }
      
      int kmax = maxArray[k - 1];
      return kmax;
    }


    /**
     * Returns an array containing all the values in a in the
     * range [low..high]; that is, all the values that are greater
     * than or equal to low and less than or equal to high,
     * including duplicate values. The length of the returned array
     * is the same as the number of values in the range [low..high].
     * If there are no qualifying values, this method returns a
     * zero-length array. Note that low and high do not have
     * to be actual values in a. This method throws an
     * IllegalArgumentException if a is null or has zero length.
     * The array a is not changed by this method.
     */
    public static int[] range(int[] a, int low, int high) {
        if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
        int[] arrayOfRange = new int[0];
        int p = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] <= high && a[i] >= low) {
                arrayOfRange = Arrays.copyOf(arrayOfRange, arrayOfRange.length + 1);
                arrayOfRange[p] = a[i];
                p++;
            }
        }
     
        return arrayOfRange;
        
    }


    /**
     * Returns the smallest value in a that is greater than or equal to
     * the given key. This method throws an IllegalArgumentException if
     * a is null or has zero length, or if there is no qualifying
     * value. Note that key does not have to be an actual value in a.
     * The array a is not changed by this method.
     */
    public static int ceiling(int[] a, int key) {
        if (a == null) { 
         throw new IllegalArgumentException();
      }
      if (a.length == 0) { //
         throw new IllegalArgumentException();
      }
      boolean noQualifyingValue = true;
      int numSmallest = Selector.max(a); 
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key && a[i] <= numSmallest) {
            numSmallest = a[i];
            noQualifyingValue = false; 
         }
      }
      if (noQualifyingValue) { 
         throw new IllegalArgumentException();
      }
      
      return numSmallest;
    }


    /**
     * Returns the largest value in a that is less than or equal to
     * the given key. This method throws an IllegalArgumentException if
     * a is null or has zero length, or if there is no qualifying
     * value. Note that key does not have to be an actual value in a.
     * The array a is not changed by this method.
     */
    public static int floor(int[] a, int key) {
        if (a == null) { 
         throw new IllegalArgumentException();
      }
      if (a.length == 0) { //
         throw new IllegalArgumentException();
      }
      boolean noQualifyingValue = true;
      int numLargest = Selector.min(a); 
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key && a[i] >= numLargest) {
            numLargest = a[i];
            noQualifyingValue = false; 
         }
      }
      if (noQualifyingValue) { 
         throw new IllegalArgumentException();
      }
      
      return numLargest;

}
}

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Ssai Sanjanna Ganji (szg0161@auburn.edu)
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
     * Returns the minimum value in the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty, this method throws a
     * NoSuchElementException. This method will not change coll in any way.
     *
     * @param coll    the Collection from which the minimum is selected
     * @param comp    the Comparator that defines the total order on T
     * @return        the minimum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> T min(Collection<T> coll, Comparator<T> comp) {
        if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
       Iterator<T> scan = coll.iterator();
      T smallest = scan.next(); 
      for (T num : coll) { 
         if (comp.compare(num, smallest) < 0) {
            smallest = num;
         }
      }
          return smallest;
    }


    /**
     * Selects the maximum value in the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty, this method throws a
     * NoSuchElementException. This method will not change coll in any way.
     *
     * @param coll    the Collection from which the maximum is selected
     * @param comp    the Comparator that defines the total order on T
     * @return        the maximum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> T max(Collection<T> coll, Comparator<T> comp) {
        if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
            Iterator<T> scan = coll.iterator();
      T largest = scan.next(); 
      for (T num : coll) { 
         if (comp.compare(num, largest) > 0) {
            largest = num;
         }
      }
            return largest;
    }


    /**
     * Selects the kth minimum value from the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty or if there is no kth minimum
     * value, this method throws a NoSuchElementException. This method will not
     * change coll in any way.
     *
     * @param coll    the Collection from which the kth minimum is selected
     * @param k       the k-selection value
     * @param comp    the Comparator that defines the total order on T
     * @return        the kth minimum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
         if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      if (k < 1 || k > coll.size()) {
         throw new NoSuchElementException();
      }
      ArrayList<T> numList = new ArrayList<T>(coll);
      java.util.Collections.sort(numList, comp);
      Iterator<T> scan = numList.iterator();
      scan.next();
            int sameNum = 0;
      for (T num : numList) {
         if (scan.hasNext()) {
            if (comp.compare(num, scan.next()) == 0) {
               sameNum++; 
            }
         }
      }
      if (k > coll.size() - sameNum) { 
         throw new NoSuchElementException();
      }   
     for (int i = 0; i < k - 1; i++) {
         if (numList.get(i) == numList.get(i+1)) {
            k++; 
         }
      }      
      T kmin = numList.get(k - 1);
      return kmin;
    }


    /**
     * Selects the kth maximum value from the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty or if there is no kth maximum
     * value, this method throws a NoSuchElementException. This method will not
     * change coll in any way.
     *
     * @param coll    the Collection from which the kth maximum is selected
     * @param k       the k-selection value
     * @param comp    the Comparator that defines the total order on T
     * @return        the kth maximum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
        if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      if (k < 1 || k > coll.size()) {
         throw new NoSuchElementException();
      }
      
      ArrayList<T> numList = new ArrayList<T>(coll);
      java.util.Collections.sort(numList, comp);
      Iterator<T> scan = numList.iterator();
      scan.next();
      
      int sameNum = 0;
      for (T num : numList) {
         if (scan.hasNext()) {
            if (comp.compare(num, scan.next()) == 0) {
               sameNum++; 
            }
         }
      }
      
      if (k > coll.size() - sameNum) { 
         throw new NoSuchElementException();
      }
      
      for (int i = coll.size() - 1; i > coll.size() - k; i--) {
         if (numList.get(i) == numList.get(i-1)) {
            k++; 
         }
      }
      
      T kmax = numList.get(coll.size() - k);
      return kmax;
    }


    /**
     * Returns a new Collection containing all the values in the Collection coll
     * that are greater than or equal to low and less than or equal to high, as
     * defined by the Comparator comp. The returned collection must contain only
     * these values and no others. The values low and high themselves do not have
     * to be in coll. Any duplicate values that are in coll must also be in the
     * returned Collection. If no values in coll fall into the specified range or
     * if coll is empty, this method throws a NoSuchElementException. If either
     * coll or comp is null, this method throws an IllegalArgumentException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the range values are selected
     * @param low     the lower bound of the range
     * @param high    the upper bound of the range
     * @param comp    the Comparator that defines the total order on T
     * @return        a Collection of values between low and high
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> Collection<T> range(Collection<T> coll, T low, T high,
                                                      Comparator<T> comp) {
       if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      Collection<T> numRange = new ArrayList<T>(); 
      
      for (T num : coll) {
         if (comp.compare(num, low) >= 0 && comp.compare(num, high) <= 0) {
            numRange.add(num); 
         }
      }
      
      if (numRange.isEmpty()) { 
         throw new NoSuchElementException();
      }
      
      return numRange;
    }


    /**
     * Returns the smallest value in the Collection coll that is greater than
     * or equal to key, as defined by the Comparator comp. The value of key
     * does not have to be in coll. If coll or comp is null, this method throws
     * an IllegalArgumentException. If coll is empty or if there is no
     * qualifying value, this method throws a NoSuchElementException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the ceiling value is selected
     * @param key     the reference value
     * @param comp    the Comparator that defines the total order on T
     * @return        the ceiling value of key in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
        if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      boolean notFound = true;
      T numCeiling = Selector.max(coll, comp); 
      
      for (T num : coll) {
         if (comp.compare(num, key) >= 0
                  && comp.compare(num, numCeiling) <= 0) {
            numCeiling = num;
            notFound = false; 
         }
      }
      
      if (notFound) { 
         throw new NoSuchElementException();
      }
      
      return numCeiling;
    }


    /**
     * Returns the largest value in the Collection coll that is less than
     * or equal to key, as defined by the Comparator comp. The value of key
     * does not have to be in coll. If coll or comp is null, this method throws
     * an IllegalArgumentException. If coll is empty or if there is no
     * qualifying value, this method throws a NoSuchElementException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the floor value is selected
     * @param key     the reference value
     * @param comp    the Comparator that defines the total order on T
     * @return        the floor value of key in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
    public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      boolean notFound = true;
      T numFloor = Selector.min(coll, comp); 
      
      for (T num : coll) {
         if (comp.compare(num, key) <= 0
                  && comp.compare(num, numFloor) >= 0) {
            numFloor = num;
            notFound = false; 
         }
      }
      
      if (notFound) { 
         throw new NoSuchElementException();
      }
      
      return numFloor;
   }
}

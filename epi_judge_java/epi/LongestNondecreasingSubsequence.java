package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LongestNondecreasingSubsequence {
  @EpiTest(testDataFile = "longest_nondecreasing_subsequence.tsv")

  public static int longestNondecreasingSubsequenceLength(List<Integer> A) {
    // TODO - you fill in here.
    // brute force approach is to calculate all possible subsequences at each index starting from 0, exponential complexity,
    // longest subsequence that ends at index i + 1 must contain the longest subsequence till i
    // so if we can recursively calculate longest subsequence

    List<List<SubSequenceInfo>> cache = new ArrayList<>();

    for(int a: A) {
      List<SubSequenceInfo> list = new ArrayList<>();
      for(int b: A) {
        list.add(new SubSequenceInfo());
      }

      cache.add(list);
    }



     calculateSubSequence(A, 0, A.size() - 1, cache);
    int max = Integer.MIN_VALUE;
    for(List<SubSequenceInfo> infos: cache) {
      for (SubSequenceInfo info: infos) {
        max = info.size > max ? info.size : max;
      }
    }
    return max;
  }

  // 4 0   max of(4, 0)

  private static SubSequenceInfo calculateSubSequence (List<Integer> A, int start, int end, List<List<SubSequenceInfo>> cache) {
    System.out.println("Start is : " + start);
    System.out.println("End is : " + end);
    for(List<SubSequenceInfo> info: cache) {
      System.out.println(info);
    }

    if(start > end) {
      return new SubSequenceInfo(0, new int[0]);
    } else if (start == end) {
      cache.get(start).set(end, new SubSequenceInfo(1, new int[] {A.get(start)}));
    } else {
     if(cache.get(start).get(end).size == -1) {
       SubSequenceInfo infoLower = calculateSubSequence(A, start + 1, end, cache);
       SubSequenceInfo infoUpper = calculateSubSequence(A, start, end - 1, cache);
       int lowest = getLowestNumberInLargestSubsequence(infoLower);
       int highest = getHighestNumberInLargestSubsequence(infoUpper);
       int lowSize = 0;
       int highSize = 0;
       if(A.get(start) <= lowest) {
         lowSize = infoLower.size + 1;
       }

       if(A.get(end) >= highest) {
         highSize = infoUpper.size + 1;
       }
       boolean upperChose = highSize > lowSize;
       int currentSize = upperChose ? highSize : lowSize;

       if(highSize == lowSize && highSize == 0) {
         cache.get(start).set(end, infoLower.size > infoUpper.size ? infoLower : infoUpper);
       } else {
         int [] current = new int[currentSize];
         if(upperChose) {
           copyArray(current, 0, infoUpper.subArray, 0, infoUpper.subArray.length - 1);
           current[current.length - 1] = A.get(end);
         } else {
           copyArray(current, 1, infoLower.subArray, 0, infoLower.subArray.length - 1);
           current[0] = A.get(start);
         }

         cache.get(start).set(end, new SubSequenceInfo(current.length, current));
       }
     }
    }
    return cache.get(start).get(end);
  }

  private static void copyArray(int [] to, int toStart, int [] from, int start, int end) {
    for(int i=start, j = toStart; i <= end; i++, j++) {
      to[j] = from[i];
    }
  }

  private static int getLowestNumberInLargestSubsequence(SubSequenceInfo infoLower) {
    int [] lower = infoLower.subArray;
    return lower[0];
  }

  private static int getHighestNumberInLargestSubsequence(SubSequenceInfo infoUpper) {
    return infoUpper.subArray[infoUpper.subArray.length - 1];
  }

  private static class SubSequenceInfo {
    public int size;
    public int [] subArray;

    SubSequenceInfo (int size, int [] subArray) {
      this.size = size;
      this.subArray = subArray;
    }
    SubSequenceInfo () {
      this.size = -1;
    }

    @Override
    public String toString() {
      return "SubSequenceInfo{" +
              "size=" + size +
              ", subArray=" + Arrays.toString(subArray) +
              '}';
    }
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestNondecreasingSubsequence.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

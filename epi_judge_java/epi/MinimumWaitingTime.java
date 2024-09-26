package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class MinimumWaitingTime {
  @EpiTest(testDataFile = "minimum_waiting_time.tsv")

  public static int minimumTotalWaitingTime(List<Integer> serviceTimes) {
    // TODO - you fill in here.
    Collections.sort(serviceTimes);
    int temp = 0;
    int prev = 0;
    for(int i=0; i < serviceTimes.size() - 1; i++) {

      temp = temp + prev + serviceTimes.get(i);
      prev = prev + serviceTimes.get(i);
    }
    return temp;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumWaitingTime.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

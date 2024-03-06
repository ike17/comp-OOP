import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that benchmarks an implementation of List by
 * performing successive insertions at its front.
 *
 * @author Nick Efford
 */
public class ListBenchmark {

  private List<Integer> list;
  private int numInsertions;
  private long duration;

  /**
   * Creates a ListBenchmark object that will test the given
   * List using the given number of insertions.
   *
   * @param list List of objects into which data will be inserted
   * @param numInsertions Number of insertions to be performed
   */
  public ListBenchmark(List<Integer> list, int numInsertions) {
    this.list = list;
    this.numInsertions = numInsertions;
  }

  /**
   * Runs the benchmark, recording total time taken for all insertions.
   */
  public void run() {
    int value = 1;
    long startTime = System.nanoTime();

    for (int i = 0; i < numInsertions; ++i)
      list.add(0, value);

    duration = System.nanoTime() - startTime;
  }

  /**
   * Reports on time taken to perform insertions.
   */
  public void report() {
    System.out.printf("%s: %.5f seconds\n",
      list.getClass().getName(), duration * 1e-9);
  }

  /**
   * Example of benchmarking ArrayList and LinkedList.
   *
   * Running as
   * <pre>
   *   java ListBenchmark 5000
   * </pre>
   * will benchmark these two classes using 5000 insertions.
   *
   * @param args Command-line arguments; the first is the number of
   *   insertions required.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java ListBenchmark <numInsertions>");
      System.exit(1);      
    }
    int size = Integer.parseInt(args[0]);

    LinkedList<Integer> linkedList = new LinkedList<Integer>();
    ListBenchmark linkedTest = new ListBenchmark(linkedList, size);

    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    ListBenchmark arrayTest = new ListBenchmark(arrayList, size);

    linkedTest.run();
    linkedTest.report();

    arrayTest.run();
    arrayTest.report();

    System.exit(0);
  }
}

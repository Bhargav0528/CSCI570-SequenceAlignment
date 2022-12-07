package metrics;

import enums.MemorySize;
import enums.TimeUnit;

public final class MetricsHelper {

  public static double getMemory(MemorySize size) {
    double currentMemory = Runtime.getRuntime().totalMemory();
    return switch (size) {
      case KB -> currentMemory - Runtime.getRuntime().freeMemory() / 10e3;
      case MB -> currentMemory - Runtime.getRuntime().freeMemory() / 10e6;
      default -> currentMemory - Runtime.getRuntime().freeMemory();
    };
  }

  private static double getTime(TimeUnit timeUnit) {
    return switch (timeUnit) {
      case MILLISECONDS -> System.nanoTime()/10e6;
      case SECONDS ->  System.nanoTime()/10e9;
      case MINUTES -> System.nanoTime()/(60 *10e9);
      default -> System.nanoTime();
    };
  }

}

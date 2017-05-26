public class ThreadExample implements Runnable {
  
  private String greeting;   // Message to print to console

  public ThreadExample(String greeting) {
    this.greeting = greeting;
  }

  public void run() {
    for (;;) {
      System.out.println(Thread.currentThread().getName() + ":  " + greeting);
      try {
        Thread.sleep((long) (Math.random() * 100)); // Sleep 0 to 100 milliseconds
      } catch (InterruptedException e) {}  // Will not happen
    }
  }
  
  public static void main(String[] args) {
    new Thread(new ThreadExample("Hello")).start();
    new Thread(new ThreadExample("Aloha")).start();
    new Thread(new ThreadExample("Ciao")).start();
  }
}
package avantgard;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Avantgard {
    public ExecutorService executor = Executors.newFixedThreadPool(50);
    public final int n = 8;
    public List<Color[][]> gridList = new CopyOnWriteArrayList<>();
    public AtomicInteger taskCounter = new AtomicInteger(0);

    public Avantgard() {}

    public void miniMain() {
        Color[][] grid = new Color[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = Color.UNCOLORED;
            }
        }

        // Submit the initial task
        taskCounter.incrementAndGet();
        executor.submit(new PaintTask(grid, 0, 0, this));

        // Wait for all tasks to finish
        while (taskCounter.get() > 0) {
            //System.out.println("Task Counter: " + taskCounter.get());
            //System.out.println("Grid List Size: " + gridList.size());
        }
        
        try {
            executor.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //printAllGrids();
    }

    private void printAllGrids() {
        System.out.println("--------------------");
        for (Color[][] g : gridList) {
            printGrid(g);
        }
    }

    public void printGrid(Color[][] grid) {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < n; j++) {
                System.out.print((grid[i][j] == Color.COLORED ? "C" : " ") + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }

}

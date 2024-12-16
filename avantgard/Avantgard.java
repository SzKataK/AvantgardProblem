package avantgard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Avantgard {
    public ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    public final int n = 8;
    public AtomicInteger taskCounter = new AtomicInteger(0);
    private final Object lock = new Object();

    public Avantgard() {}

    public void miniMain() {
        Color[][] grid = new Color[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = Color.UNCOLORED;
            }
        }

        clearFile();

        taskCounter.incrementAndGet();
        executor.submit(new PaintTask(grid, 0, 0, 0, this));

        // All possible starting points
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                taskCounter.incrementAndGet();
                executor.submit(new PaintTask(grid, i, j, 0, this));

                while (taskCounter.get() > 0) {
                    System.out.println("Task Counter: " + taskCounter.get());
                }
            }
        }

        // Wait for all tasks to finish
        while (taskCounter.get() > 0) {
            System.out.println("Task Counter: " + taskCounter.get());
        }
        
        try {
            executor.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //printAllGrids();
    }

    public void printGrid(Color[][] grid) {
        System.out.println("--------------------");
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < n; j++) {
                System.out.print((grid[i][j] == Color.COLORED ? "C" : " ") + " ");
            }
            System.out.println();
        }
    }

    private void clearFile() {
        String filePath = "output.txt";
        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeGridToFile(Color[][] grid, int countColored) {
        String filePath = "output.txt";
        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write("--------------------\n");
                writer.write("  0 1 2 3 4 5 6 7\n");
                for (int i = 0; i < grid.length; i++) {
                    writer.write(i + " ");
                    for (int j = 0; j < grid[i].length; j++) {
                        writer.write((grid[i][j] == Color.COLORED ? "C" : " ") + " ");
                    }
                    writer.write("\n");
                }
                writer.write("Count Colored: " + countColored + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package avantgard;

import java.util.ArrayList;
import java.util.List;

public class PaintTask implements Runnable {
    // Fields //
    private Color[][] grid;         // State of the grid
    private int x, y;               // Current position
    private Avantgard avantgard;    // Reference to the main class

    // Constructor //
    public PaintTask(Color[][] grid, int x, int y, Avantgard avantgard) {
        this.grid = copyGrid(grid);
        this.x = x;
        this.y = y;
        this.avantgard = avantgard;
    }

    // Methods //

    @Override
    public void run() {
        while (true) {
            // Paint the current position
            grid[x][y] = Color.COLORED;

            // Get valid directions
            List<int[]> dirs = getValidDirections(x, y);

            // No more valid directions (end condition)
            if (dirs.isEmpty()) {
                avantgard.gridList.add(grid);
                avantgard.taskCounter.decrementAndGet();
                avantgard.printGrid(grid);
                return;
            }

            // Only one valid direction (continue the same thread)
            if (dirs.size() == 1) {
                x = dirs.get(0)[0];
                y = dirs.get(0)[1];
                continue;
            }

            // Multiple valid directions (create new threads)
            x = dirs.get(0)[0];
            y = dirs.get(0)[1];

            for (int i = 1; i < dirs.size(); i++) {
                int newX = dirs.get(i)[0];
                int newY = dirs.get(i)[1];

                avantgard.taskCounter.incrementAndGet();
                avantgard.executor.submit(new PaintTask(grid, newX, newY, avantgard));
            }
        }
    }

    private List<int[]> getValidDirections(int testX, int testY) {
        List<int[]> dirs = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] d : directions) {
            int newX = testX + d[0];
            int newY = testY + d[1];
            if (isValidDirection(newX, newY)) {
                dirs.add(new int[]{newX, newY});
            }
        }

        return dirs;
    }

    private boolean isValidDirection(int testX, int testY) {
        // Check if the new position is within the grid and not colored
        if (testX < 0 || testX >= 8 || testY < 0 || testY >= 8) return false;
        if (grid[testX][testY] == Color.COLORED) return false;

        // Check if the new position is vaild
        int count = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] d : directions) {
            int newX = testX + d[0];
            int newY = testY + d[1];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grid[newX][newY] == Color.COLORED) {
                count++;
            }
        }

        return count == 1;
    }

    private Color[][] copyGrid(Color[][] original) {
        Color[][] copy = new Color[8][];
        for (int i = 0; i < 8; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

}

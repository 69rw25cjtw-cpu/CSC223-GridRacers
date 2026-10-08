import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Racetrack class for loading the track and calculating weights
public class Racetrack {

    // 2d array for the track characters
    private char[][] track;

    // 2d array for the weights of each spot
    private int[][] weights;

    // constants
    final private int FINISH_WEIGHT = 0;
    final private int WALL_WEIGHT = 9999;
    final private int INIT_TRACK_WEIGHT = -1;

    // default constructor
    public Racetrack() {
    }

    // constructor with filename
    public Racetrack(String filename) {
        if (loadTrack(filename)) {
            initWeights();
            calculateWeights();
        }
    }

    // loads track from text file into 2d array
    public boolean loadTrack(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Track file not found -> " + filename);
            return false;
        }

        if (lines.isEmpty()) {
            System.out.println("Error: Track file is empty -> " + filename);
            return false;
        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        this.track = new char[rows][cols];
        this.weights = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            for (int c = 0; c < cols; c++) {
                if (c < line.length()) {
                    this.track[r][c] = line.charAt(c);
                } else {
                    this.track[r][c] = 'X'; // default to wall
                }
            }
        }
        return true;
    }

    // sets up starting weights: wall=9999, finish=0, track=-1
    public void initWeights() {
        if (track == null) {
            return;
        }
        int rows = height();
        int cols = width();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char symbol = track[r][c];
                if (symbol == 'X') {
                    weights[r][c] = WALL_WEIGHT;
                } else if (symbol == 'F') {
                    weights[r][c] = FINISH_WEIGHT;
                } else {
                    weights[r][c] = INIT_TRACK_WEIGHT;
                }
            }
        }
    }

    // calculates weights moving outward from finish line (0, then 1, 2, 3...)
    public void calculateWeights() {
        if (track == null || weights == null) {
            return;
        }

        int currentWeight = 0;
        boolean addedAny = true;

        // 8 directions (up, down, left, right and diagonals)
        int[] dRow = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dCol = {-1,  0,  1, -1, 1, -1, 0, 1};

        int rows = height();
        int cols = width();

        // keep looping until no more weights get added
        while (addedAny) {
            addedAny = false;
            int nextWeight = currentWeight + 1;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    // find spots with current weight
                    if (weights[r][c] == currentWeight) {
                        // check all 8 neighbors around it
                        for (int i = 0; i < 8; i++) {
                            int nr = r + dRow[i];
                            int nc = c + dCol[i];

                            // check bounds so we dont crash
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                                // if not set yet (-1), give it next weight
                                if (weights[nr][nc] == INIT_TRACK_WEIGHT) {
                                    weights[nr][nc] = nextWeight;
                                    addedAny = true;
                                }
                            }
                        }
                    }
                }
            }
            currentWeight++;
        }
    }

    // prints out dimensions like 9x11
    public void displayDimensions() {
        if (track == null) {
            System.out.println("No track currently loaded.");
            return;
        }
        System.out.println("Track Dimensions: " + height() + "x" + width());
    }

    // displays racetrack layout with blanks for track spaces
    public void displayTrack() {
        if (track == null) {
            System.out.println("No track currently loaded.");
            return;
        }
        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                char ch = track[r][c];
                String val = (ch == 'T') ? " " : String.valueOf(ch);
                if (c == 0) {
                    System.out.print(val);
                } else {
                    System.out.printf("%4s", val);
                }
            }
            System.out.println();
        }
    }

    // displays the weights grid formatted with columns
    public void displayWeights() {
        if (weights == null) {
            System.out.println("No weights available.");
            return;
        }
        System.out.println("====================TRACK WEIGHTS=================================");
        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                String val;
                if (weights[r][c] == WALL_WEIGHT) {
                    val = "X";
                } else {
                    val = String.valueOf(weights[r][c]);
                }
                if (c == 0) {
                    System.out.print(val);
                } else {
                    System.out.printf("%4s", val);
                }
            }
            System.out.println();
        }
        System.out.println("====================TRACK WEIGHTS=================================");
    }

    // getters and setters
    public char getTrack(int row, int col) {
        return track[row][col];
    }

    public void setTrack(int row, int col, char value) {
        track[row][col] = value;
    }

    public int getWeight(int row, int col) {
        return weights[row][col];
    }

    public void setWeight(int row, int col, int value) {
        weights[row][col] = value;
    }

    // returns number of rows
    public int height() {
        return track != null ? track.length : 0;
    }

    // returns number of cols
    public int width() {
        return (track != null && track.length > 0) ? track[0].length : 0;
    }

    // prints ascii banner
    public void displayBanner() {
        String art = "   ______     _     __   ____                           \n" +
                     "  / ____/____(_)___/ /  / __ \\____ _________  __________\n" +
                     " / / __/ ___/ / __  /  / /_/ / __ `/ ___/ _ \\/ ___/ ___/\n" +
                     "/ /_/ / /  / / /_/ /  / _, _/ /_/ / /__/  __/ /  (__  ) \n" +
                     "\\____/_/  /_/\\__,_/  /_/ |_|\\__,_/\\___/\\___/_/  /____/  \n" +
                     "                                                        ";

        System.out.println(art + "\n");
    }
}

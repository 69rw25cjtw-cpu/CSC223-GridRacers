import java.util.Scanner;

// Main driver class for Grid Racers
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Racetrack racetrack = new Racetrack();

        // show the ascii title banner
        racetrack.displayBanner();

        // menu options for tracks
        System.out.println("track1.txt..........press 1");
        System.out.println("track2.txt..........press 2");
        System.out.println("track3.txt..........press 3");
        System.out.println("track4.txt..........press 4");
        System.out.print("Select a track: ");

        String choice = scanner.nextLine().trim();
        String filename = null;

        // pick file based on input
        switch (choice) {
            case "1":
                filename = "track1.txt";
                break;
            case "2":
                filename = "track2.txt";
                break;
            case "3":
                filename = "track3.txt";
                break;
            case "4":
                filename = "track4.txt";
                break;
            default:
                // if they typed a filename directly
                filename = choice;
                break;
        }

        if (filename != null && !filename.isEmpty()) {
            boolean loaded = racetrack.loadTrack(filename);
            if (loaded) {
                // set up weights and calculate distances
                racetrack.initWeights();
                racetrack.calculateWeights();

                // print everything out
                racetrack.displayWeights();
                racetrack.displayTrack();
                racetrack.displayDimensions();
            } else {
                System.out.println("Failed to process racetrack from: " + filename);
            }
        }

        scanner.close();
    }
}

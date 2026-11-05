// Car class to represent a car on the racetrack
public class Car implements Comparable<Car> {

    // car variables
    private int id;
    private int row;
    private int col;
    private int rowVelocity;
    private int colVelocity;
    private int maxSpeed;
    private int place;
    private int currentWeight;

    // default max speed
    public static final int DEFAULT_MAX_SPEED = 5;

    // default constructor
    public Car() {
        this(1, 0, 0, DEFAULT_MAX_SPEED);
    }

    // constructor with id and start position
    public Car(int id, int row, int col) {
        this(id, row, col, DEFAULT_MAX_SPEED);
    }

    // full constructor
    public Car(int id, int row, int col, int maxSpeed) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.maxSpeed = maxSpeed;
        this.rowVelocity = 1;
        this.colVelocity = 1;
        this.place = id;
        this.currentWeight = Integer.MAX_VALUE;
    }

    // updates position and increases velocity based on how far it moved
    public void updateCarInfo(int newRow, int newCol) {
        // how many spaces moved vertically and horizontally
        int deltaRow = Math.abs(newRow - this.row);
        int deltaCol = Math.abs(newCol - this.col);

        // update to new position
        this.row = newRow;
        this.col = newCol;

        // increase row velocity if moved vertically, dont go over max speed
        if (deltaRow > 0) {
            this.rowVelocity = Math.min(this.maxSpeed, this.rowVelocity + deltaRow);
        }

        // increase col velocity if moved horizontally, dont go over max speed
        if (deltaCol > 0) {
            this.colVelocity = Math.min(this.maxSpeed, this.colVelocity + deltaCol);
        }
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRowVelocity() {
        return rowVelocity;
    }

    public void setRowVelocity(int rowVelocity) {
        this.rowVelocity = Math.min(this.maxSpeed, Math.max(1, rowVelocity));
    }

    public int getColVelocity() {
        return colVelocity;
    }

    public void setColVelocity(int colVelocity) {
        this.colVelocity = Math.min(this.maxSpeed, Math.max(1, colVelocity));
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    // returns coords formatted as row,col
    public String getCoordinatesString() {
        return row + "," + col;
    }

    // returns velocity formatted as rowVel,colVel
    public String getVelocityString() {
        return rowVelocity + "," + colVelocity;
    }

    // returns car info formatted for output
    public String getInfoString() {
        return String.format("Place: %d Car ID: %d Coordinates: %d,%d Max Speed: %d Velocity: %d,%d",
                place, id, row, col, maxSpeed, rowVelocity, colVelocity);
    }

    // compare based on weight (lower weight = closer to finish line = better place)
    @Override
    public int compareTo(Car other) {
        if (this.currentWeight != other.currentWeight) {
            return Integer.compare(this.currentWeight, other.currentWeight);
        }
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return getInfoString();
    }
}

package TheGameOfLife;

public class implementGrid implements Grid{
    boolean[][] board;
    private Cell[][] grid;
    private int rows;
    private int cols;
    private CircularQueue<Cell> updateQueue;

    //constructor
    public implementGrid(int size){
        this.board = new boolean[size][size];
        updateQueue = new implementCircularQueue<>();
        rows = size;
        cols = size;
    }

    //initializes the grid
    @Override
    public void initialize(){
        this.grid = new Cell[rows][cols];

        // Initialize each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.grid[i][j] = new implementCell(i, j);
            }
        }

        // Enqueue cells based on initial configuration
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j]) {
                    updateQueue.enqueue(grid[i][j]);
                }
            }
        }
    }

    //this method updates the new grid
    @Override
    public void update() {
        Cell[][] newGrid = new Cell[rows][cols];

        // Iterate through each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveNeighbors = grid[i][j].countAliveNeighbors(grid);

                // Apply Game of Life rules to determine the new state
                boolean newState = false;

                if (grid[i][j].isAlive()) {
                    // Underpopulation or Overpopulation: Cell dies
                    if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                        newState = true;
                    }
                } else {
                    // Reproduction: Dead cell becomes alive
                    if (aliveNeighbors == 3) {
                        newState = true;
                        // Enqueue here
                        updateQueue.enqueue(grid[i][j]);
                    }
                }

                // Store the new state in the temporary array
                newGrid[i][j] = new implementCell(i, j);
                newGrid[i][j].setAlive(newState);
            }
        }

        // Update the board array with the new state
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = newGrid[i][j].isAlive();
            }
        }

        updateDequeue();

    }

    //takes care of the dequeue
    @Override
    public void updateDequeue() {
        //dequeue all and flip state
        for (int i = 0; i < updateQueue.getSize(); i++) {
            Cell obj = updateQueue.dequeue();
            if (obj.isAlive()) {
                obj.setAlive(false);
            } else {
                obj.setAlive(true);
            }
        }
    }

    //this method returns the current board
    @Override
    public boolean[][] getBoard(){
        return board;
    }



}

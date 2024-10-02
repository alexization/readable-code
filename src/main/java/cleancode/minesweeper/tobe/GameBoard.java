package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {
    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        board = new Cell[gameLevel.getColSize()][gameLevel.getRowSize()];
        landMineCount = gameLevel.getLandMineCount();
    }

    public void flag(int rowIndex, int colIndex) {
        findCell(rowIndex, colIndex).flag();
    }

    public boolean isLandMineCell(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.isLandMine();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        open(row, col);

        if (doesCellHaveLandMineCount(row, col)) {
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount(0);
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                /*
                 * cell 은 검증되지 않은 값이다. 즉, null 이 들어있을 수도 있는 값이다.
                 * 언제든 null이 될 수 있는 가능성이 있는 쪽에서 메서드를 호출하면 문제가 생길 수 있다.
                 * equals() 메서드는 어느쪽에서 호출해도 동일한 결과가 나오기 때문에
                 * cell.equals(CLOSED_CELL_SIGN) 보다는 상수인 CLOSED_CELL_SIGN 에서 메서드를 호출하는 것이 안전하다.
                 * 이처럼 null이 아닌 확정적인 값에서 메서드를 호출하는 습관을 가지는게 중요하다.
                 *  */
                .allMatch(Cell::isChecked);
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }
    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        /* 지뢰 세팅 */
        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            Cell landMineCell = findCell(landMineRow, landMineCol);
            landMineCell.turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                Cell cell = findCell(row, col);
                cell.updateNearbyLandMineCount(count);
            }
        }
    }

    public int countNearbyLandMines(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }
}

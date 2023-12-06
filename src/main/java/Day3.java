import java.util.ArrayList;
import java.util.List;

public class Day3 {
    public static int processInput(List<String> lines) {
        int value = 0;

        Matrix m = new Matrix(lines);
        for (int row = 0; row < m.rowCount(); row++) {
            for (int col = 0; col < m.colCount(); col++) {
                char c = m.getCharacter(row, col);
                if (Character.isDigit(c) || c == '.') {
                    continue;
                }
                
                List<Integer> partNumbers = m.getPartNumbers(row, col);
                for (Integer partNumber : partNumbers) {
                    value += partNumber;
                }
            }
        }

        return value;
    }

    public static int processInputAlt(List<String> lines) {
        int value = 0;

        Matrix m = new Matrix(lines);
        for (int row = 0; row < m.rowCount(); row++) {
            for (int col = 0; col < m.colCount(); col++) {
                char c = m.getCharacter(row, col);
                if (c != '*') {
                    continue;
                }

                List<Integer> partNumbers = m.getPartNumbers(row, col);
                if (partNumbers.size() != 2) {
                    continue;
                }
                value += (partNumbers.get(0) * partNumbers.get(1));
            }
        }

        return value;
    }

    private record Matrix(List<String> lines) {
        public char getCharacter(int row, int col) {
            return lines.get(row).charAt(col);
        }

        public int colCount() {
            return lines.get(0).length();
        }

        public int rowCount() {
            return lines.size();
        }

        public boolean isPartNumberElement(int row, int col) {
            return isPartNumberIndicator(row - 1, col - 1) ||
                    isPartNumberIndicator(row - 1, col) ||
                    isPartNumberIndicator(row - 1, col + 1) ||
                    isPartNumberIndicator(row, col - 1) ||
                    isPartNumberIndicator(row, col) ||
                    isPartNumberIndicator(row, col + 1) ||
                    isPartNumberIndicator(row + 1, col - 1) ||
                    isPartNumberIndicator(row + 1, col) ||
                    isPartNumberIndicator(row + 1, col + 1);
        }

        public boolean isPartNumberIndicator(int row, int col) {
            if (row < 0 || col < 0) {
                return false;
            }
            if (row >= rowCount() || col >= colCount()) {
                return false;
            }

            char c = getCharacter(row, col);
            return !Character.isDigit(c) && c != '.';
        }

        public List<Integer> getPartNumbers(int row, int col) {
            List<Integer> partNumbers = new ArrayList<>();
            Integer up = scanPartNumber(row - 1, col);
            if (up != null) {
                partNumbers.add(up);
            } else {
                Integer upLeft = scanPartNumber(row - 1, col - 1);
                if (upLeft != null) {
                    partNumbers.add(upLeft);
                }

                Integer upRight = scanPartNumber(row - 1, col + 1);
                if (upRight != null) {
                    partNumbers.add(upRight);
                }
            }

            Integer left = scanPartNumber(row, col - 1);
            if (left != null) {
                partNumbers.add(left);
            }

            Integer right = scanPartNumber(row, col + 1);
            if (right != null) {
                partNumbers.add(right);
            }

            Integer down = scanPartNumber(row + 1, col);
            if (down != null) {
                partNumbers.add(down);
            } else {
                Integer downLeft = scanPartNumber(row + 1, col - 1);
                if (downLeft != null) {
                    partNumbers.add(downLeft);
                }

                Integer downRight = scanPartNumber(row + 1, col + 1);
                if (downRight != null) {
                    partNumbers.add(downRight);
                }
            }
            
            return partNumbers;
        }

        private Integer scanPartNumber(int row, int col) {
            if (row < 0 || col < 0 || row >= rowCount() || col >= colCount()) {
                return null;
            }

            char c = getCharacter(row, col);
            if (!Character.isDigit(c)) {
                return null;
            }

            StringBuilder b = new StringBuilder();
            b.append(c);

            int scanCol = col - 1;
            while (scanCol >= 0) {
                c = getCharacter(row, scanCol);
                if (Character.isDigit(c)) {
                    b.insert(0, c);
                    scanCol--;
                } else {
                    break;
                }
            }

            scanCol = col + 1;
            while (scanCol < colCount()) {
                c = getCharacter(row, scanCol);
                if (Character.isDigit(c)) {
                    b.append(c);
                    scanCol++;
                } else {
                    break;
                }
            }

            return Integer.parseInt(b.toString());
        }
    }
}

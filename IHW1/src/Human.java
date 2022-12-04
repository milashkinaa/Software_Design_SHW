import java.util.Scanner;

/** класс Human - работаем с ходами человека
 */
public class Human implements IPlayable {

    @Override
    public int[] GetMove(boolean[][] fst, boolean[][] snd, Map map)
            throws PlayerMoveException {
        System.out.println("Выберите номер клетки (строка и столбец. Например: 2 3)");
        int row = 0, column = 0;
        Scanner in = new Scanner(System.in);
        try {
            boolean is_row = in.hasNextInt();
            if (!is_row) {
                throw new PlayerMoveException();
            }
            row = in.nextInt();
            boolean is_column = in.hasNextInt();
            if (!is_column) {
                throw new PlayerMoveException();
            }
            column = in.nextInt();
        } catch (Exception e) {
            System.out.println(e);
        }
        boolean[][] possible_moves = map.GetPossibleMoves(snd, fst);
        if (!possible_moves[row - 1][column - 1]) {
            throw new PlayerMoveException();
        }
        fst[row - 1][column - 1] = true;
        return new int[] {row - 1, column - 1};
    }

    @Override
    public boolean Move(boolean[][] fst, boolean[][] snd, Map map, boolean is_first) {
        boolean[][] possible_moves = map.GetPossibleMoves(snd, fst);
        PrintPossibleMoves(possible_moves);
        boolean flag = false;
        for (boolean[] row : possible_moves) {
            if (flag)
                break;
            for (boolean el : row) {
                if (el) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag)
            return false;
        while (true) {
            try {
                int[] move = GetMove(fst, snd, map);
                map.DrawMap(snd, fst, move[0], move[1], is_first);
                return true;
            } catch (PlayerMoveException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private void PrintPossibleMoves(boolean[][] possible_moves) {
        System.out.println("Ваши возможные ходы:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possible_moves[i][j])
                    System.out.println(i + 1 + " " + (j + 1));
            }
        }
    }
}

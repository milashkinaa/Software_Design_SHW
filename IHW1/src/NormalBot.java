/** класс NormalBot - игра в легком режиме
 */
public class NormalBot implements IPlayable {
    @Override
    public int[] GetMove(boolean[][] fst, boolean[][] snd, Map map) {
        boolean[][] possible_moves = map.GetPossibleMoves(snd, fst);
        double[][] weight = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possible_moves[i][j]) {
                    double ss = 0;
                    if (i == 0 || i == 7) {
                        if (j == 0 || j == 7)
                            ss = 0.8;
                        else
                            ss = 0.4;
                    } else if (j == 0 || j == 7) {
                        if (i == 0 || i == 7)
                            ss = 0.8;
                        else
                            ss = 0.4;
                    }
                    boolean[][] new_cells = map.ChangeCells(i, j, fst, snd);
                    double s_i = 0;
                    for (int i1 = 0; i1 < 8; i1++) {
                        for (int j1 = 0; j1 < 8; j1++) {
                            if (new_cells[i1][j1]) {
                                if (i1 == 0 || i1 == 7 || j1 == 0 || j1 == 7)
                                    s_i += 2;
                                else
                                    s_i += 1;
                            }
                        }
                    }
                    weight[i][j] = s_i + ss;
                } else {
                    weight[i][j] = -100;
                }
            }
        }
        double[] move = new double[] {-1, -1, -1};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (weight[i][j] > move[0]) {
                    move[0] = weight[i][j];
                    move[1] = i;
                    move[2] = j;
                }
            }
        }
        fst[(int)move[1]][(int)move[2]] = true;
        return new int[] {(int)move[1], (int)move[2]};
    }

    @Override
    public boolean Move (boolean[][] fst, boolean[][] snd, Map map, boolean is_first) {
        boolean[][] possible_moves = map.GetPossibleMoves(snd, fst);
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
        int[] move = GetMove(fst, snd, map);
        map.DrawMap(snd, fst, move[0], move[1], is_first);
        return true;
    }
}

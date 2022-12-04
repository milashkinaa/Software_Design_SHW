/** класс CoolBot - игра в продвинутом режиме
 */
public class CoolBot implements IPlayable {
    @Override
    public int[] GetMove(boolean[][] fst, boolean[][] snd, Map map) {
        double[] max = new double[] {-100, -100, -100};
        double[][] weight = GetWeight(fst, snd, map);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (weight[i][j] != -100) {
                    Map temp_map = new Map();
                    temp_map.x_player_data = map.x_player_data;
                    temp_map.o_player_data = map.o_player_data;
                    boolean[][] new_fst = fst;
                    boolean[][] new_snd = snd;
                    boolean[][] changer = temp_map.ChangeCells(i, j, new_fst, new_snd);
                    new_fst = temp_map.ChangeSnd(new_fst, changer);
                    new_snd = temp_map.ChangeFst(new_snd, changer);
                    double[][] enemy_weight = GetWeight(new_snd, new_fst, temp_map);
                    double res = weight[i][j] - GetMax(enemy_weight);
                    if (res > max[0]) {
                        max[0] = res;
                        max[1] = i;
                        max[2] = j;
                    }
                }
            }
        }
        fst[(int)max[1]][(int)max[2]] = true;
        return new int[] {(int)max[1], (int)max[2]};
    }

    @Override
    public boolean Move(boolean[][] fst, boolean[][] snd, Map map, boolean is_first) {
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
        if (move[0] == -100 || move[1] == -100) {
            return false;
        }
        map.DrawMap(snd, fst, move[0], move[1], is_first);
        return true;
    }

    private double[][] GetWeight(boolean[][] fst, boolean[][] snd, Map map) {
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
                    weight[i][j] = s_i + ss;
                }
                    weight[i][j] = -100;
                }
            }
        }
        return weight;
    }

    private double GetMax(double[][] weight) {
        double move = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (weight[i][j] > move)
                    move = weight[i][j];
            }
        }
        return move;
    }
}
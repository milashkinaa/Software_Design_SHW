import static java.lang.Math.abs;

public class Map {
    public boolean[][] x_player_data = new boolean[8][8];
    public boolean[][] o_player_data = new boolean[8][8];

    public Map() {
        x_player_data[3][4] = true;
        x_player_data[4][3] = true;
        o_player_data[3][3] = true;
        o_player_data[4][4] = true;
    }
    /** метод DrawFstMap - отрисовка игрового поля
     */
    public void DrawFstMap(boolean[][] fst, boolean[][] snd) {
        o_player_data = fst;
        x_player_data = snd;
        boolean[][] possible_moves = GetPossibleMoves(snd, fst);
        System.out.print("   1 2 3 4 5 6 7 8\n");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + 1 + " |");
            for (int j = 0; j < 8; j++) {
                if (possible_moves[i][j]) {
                    System.out.print("*|");
                } else if (snd[i][j]) {
                    System.out.print("X|");
                } else if (fst[i][j]) {
                    System.out.print("O|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.print("\n");
        }
    }

    /** метод DrawSndMap - отрисовка игрового поля
     */
    public void DrawSndMap(boolean[][] fst, boolean[][] snd) {
        o_player_data = fst;
        x_player_data = snd;
        boolean[][] possible_moves = GetPossibleMoves(fst, snd);
        System.out.print("   1 2 3 4 5 6 7 8\n");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + 1 + " |");
            for (int j = 0; j < 8; j++) {
                if (possible_moves[i][j]) {
                    System.out.print("*|");
                } else if (snd[i][j]) {
                    System.out.print("X|");
                } else if (fst[i][j]) {
                    System.out.print("O|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.print("\n");
        }
    }

    /** метод DrawMap - отрисовка игрового поля
     */
    public void DrawMap(boolean[][] fst, boolean[][] snd, int row, int column, boolean is_first) {
        boolean[][] changer = ChangeCells(row, column, fst, snd);
        fst = ChangeFst(fst, changer);
        snd = ChangeSnd(snd, changer);
        if (is_first) {
            DrawFstMap(fst, snd);
        } else {
            DrawSndMap(snd, fst);
        }
    }

    public boolean[][] ChangeFst (boolean[][] arr, boolean[][] changer) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (changer[i][j]) {
                    arr[i][j] = false;
                }
            }
        }
        return arr;
    }

    public boolean[][] ChangeSnd (boolean[][] arr, boolean[][] changer) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (changer[i][j]) {
                    arr[i][j] = true;
                }
            }
        }
        return arr;
    }

    /** метод GetPossibleMoves - получаем доступные для игрока ходы
     */
    boolean[][] GetPossibleMoves(boolean[][] fst, boolean[][] snd) {
        boolean[][] possible_moves = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!fst[i][j] && !snd[i][j]) {
                    if (i != 7) {
                        if (GoDown_(i, j, fst, snd))
                            possible_moves[i][j] = true;
                        if (j != 0) {
                            if (GoDownBack_(i, j, fst, snd))
                                possible_moves[i][j] = true;
                        }
                    }
                    if (j != 7) {
                        if (GoAhead_(i, j, fst, snd))
                            possible_moves[i][j] = true;
                        if (i != 7) {
                            if (GoDownAhead_(i, j, fst, snd))
                                possible_moves[i][j] = true;
                        }
                    }
                    if (i != 0) {
                        if (GoUp_(i, j, fst, snd))
                            possible_moves[i][j] = true;
                        if (j != 7) {
                            if (GoUpAhead_(i, j, fst, snd))
                                possible_moves[i][j] = true;
                        }
                    }
                    if (j != 0) {
                        if (GoBack_(i, j, fst, snd))
                            possible_moves[i][j] = true;
                        if (i != 0) {
                            if (GoUpBack_(i, j, fst, snd))
                                possible_moves[i][j] = true;
                        }
                    }
                }
            }
        }
        return possible_moves;
    }

    public boolean[][] ChangeCells(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] for_change = new boolean[8][8];
        boolean[][] temp;
        if (row != 7) {
            temp = ChangeDown_(row, column, fst, snd);
            for_change = AddCells(temp, for_change);
            if (column != 0) {
                temp = ChangeDownBack_(row, column, fst, snd);
                for_change = AddCells(temp, for_change);
            }
        }
        if (column != 7) {
            temp = ChangeAhead_(row, column, fst, snd);
            for_change = AddCells(temp, for_change);
            if (row != 7) {
                temp = ChangeDownAhead_(row, column, fst, snd);
                for_change = AddCells(temp, for_change);
            }
        }
        if (row != 0) {
            temp = ChangeUp_(row, column, fst, snd);
            for_change = AddCells(temp, for_change);
            if (column != 7) {
                temp = ChangeUpAhead_(row, column, fst, snd);
                for_change = AddCells(temp, for_change);
            }
        }
        if (column != 0) {
            temp = ChangeBack_(row, column, fst, snd);
            for_change = AddCells(temp, for_change);
            if (row != 0) {
                temp = ChangeUpBack_(row, column, fst, snd);
                for_change = AddCells(temp, for_change);
            }
        }
        return for_change;
    }

    private boolean[][] AddCells (boolean[][] fst, boolean[][] snd) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fst[i][j]) {
                    snd[i][j] = true;
                }
            }
        }
        return snd;
    }

    private boolean GoDown_(int i, int j, boolean[][] fst, boolean[][] snd) {
        if (fst[i + 1][j]) {
            for (int k = i + 2; k < 8; k++) {
                if (!fst[k][j] && !snd[k][j])
                    return false;
                if (snd[k][j])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeDown_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        if (fst[row + 1][column]) {
            for (int k = row + 2; k < 8; k++) {
                if (snd[k][column]) {
                    for (int m = row + 1; m < k; m++)
                        answer[m][column] = true;
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoAhead_(int i, int j, boolean[][] fst, boolean[][] snd) {
        if (fst[i][j + 1]) {
            for (int k = j + 2; k < 8; k++) {
                if (!fst[i][k] && !snd[i][k])
                    return false;
                if (snd[i][k])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeAhead_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        if (fst[row][column + 1]) {
            for (int k = column + 2; k < 8; k++) {
                if (snd[row][k]) {
                    for (int m = column + 1; m < k; m++)
                        answer[row][m] = true;
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoUp_(int i, int j, boolean[][] fst, boolean[][] snd) {
        if (fst[i - 1][j]) {
            for (int k = i - 2; k != -1; k--) {
                if (!fst[k][j] && !snd[k][j])
                    return false;
                if (snd[k][j])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeUp_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        if (fst[row - 1][column]) {
            for (int k = row - 2; k != -1; k--) {
                if (snd[k][column]) {
                    for (int m = row - 1; m != k; m--)
                        answer[m][column] = true;
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoBack_(int i, int j, boolean[][] fst, boolean[][] snd) {
        if (fst[i][j - 1]) {
            for (int k = j - 2; k != -1; k--) {
                if (!fst[i][k] && !snd[i][k])
                    return false;
                if (snd[i][k])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeBack_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        if (fst[row][column - 1]) {
            for (int k = column - 2; k != -1; k--) {
                if (snd[row][k]) {
                    for (int m = column - 1; m != k; m--)
                        answer[row][m] = true;
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoDownBack_(int i, int j, boolean[][] fst, boolean[][] snd) {
        int temp_j = j - 1;
        if (fst[i + 1][j - 1]) {
            for (int k = i + 2; k < 8; k++) {
                if (temp_j - 1 < 0)
                    continue;
                temp_j--;
                if (!fst[k][temp_j] && !snd[k][temp_j])
                    return false;
                if (snd[k][temp_j])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeDownBack_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        int temp_j = column - 1;
        if (fst[row + 1][column - 1]) {
            for (int k = row + 2; k < 8; k++) {
                if (temp_j - 1 < 0)
                    continue;
                temp_j--;
                if (snd[k][temp_j]) {
                    int temp_j2 = column - 1;
                    for (int m = row + 1; m < k; m++) {
                        answer[m][temp_j2] = true;
                        temp_j2--;
                    }
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoDownAhead_(int i, int j, boolean[][] fst, boolean[][] snd) {
        int temp_j = j + 1;
        if (fst[i + 1][j + 1]) {
            for (int k = i + 2; k < 8; k++) {
                if (temp_j + 1 > 7)
                    continue;
                temp_j++;
                if (!fst[k][temp_j] && !snd[k][temp_j])
                    return false;
                if (snd[k][temp_j])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeDownAhead_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        int temp_j = column + 1;
        if (fst[row + 1][column + 1]) {
            for (int k = row + 2; k < 8; k++) {
                if (temp_j + 1 > 7)
                    continue;
                temp_j++;
                if (snd[k][temp_j]) {
                    int temp_j2 = column + 1;
                    for (int m = row + 1; m < k; m++) {
                        answer[m][temp_j2] = true;
                        temp_j2++;
                    }
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoUpAhead_(int i, int j, boolean[][] fst, boolean[][] snd) {
        int temp_j = j + 1;
        if (fst[i - 1][j + 1]) {
            for (int k = i - 2; k != -1; k--) {
                if (temp_j + 1 > 7)
                    continue;
                temp_j++;
                if (!fst[k][temp_j] && !snd[k][temp_j])
                    return false;
                if (snd[k][temp_j])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeUpAhead_(int row, int column, boolean[][] fst, boolean[][] snd) {
        boolean[][] answer = new boolean[8][8];
        int temp_j = column + 1;
        if (fst[row - 1][column + 1]) {
            for (int k = row - 2; k != -1; k--) {
                if (temp_j + 1 > 7)
                    continue;
                temp_j++;
                if (snd[k][temp_j]) {
                    int temp_j2 = column + 1;
                    for (int m = row - 1; m != k; m--) {
                        answer[m][temp_j2] = true;
                        temp_j2++;
                    }
                    return answer;
                }
            }
        }
        return answer;
    }

    private boolean GoUpBack_ (int i, int j, boolean[][] fst, boolean[][] snd) {
        int temp_j = j - 1;
        if (fst[i - 1][j - 1]) {
            for (int k = i - 2; k != -1; k--) {
                if (temp_j - 1 < 0)
                    continue;
                temp_j--;
                if (!fst[k][temp_j] && !snd[k][temp_j])
                    return false;
                if (snd[k][temp_j])
                    return true;
            }
        }
        return false;
    }

    private boolean[][] ChangeUpBack_ (int row, int column, boolean[][] fst, boolean[][] snd) {
        int temp_j = column - 1;
        boolean[][] answer = new boolean[8][8];
        if (fst[row - 1][column - 1]) {
            for (int k = row - 2; k != -1; k--) {
                if (temp_j - 1 < 0)
                    continue;
                temp_j--;
                if (snd[k][temp_j]) {
                    int temp_j2 = column - 1;
                    for (int m = row - 1; m != k; m--) {
                        answer[m][temp_j2] = true;
                        temp_j2--;
                    }
                    return answer;
                }
            }
        }
        return answer;
    }
}

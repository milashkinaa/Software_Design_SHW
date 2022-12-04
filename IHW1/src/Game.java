public class Game {
    static int max_points = 0;

    /** метод NormalBotGame - игра с компьютером в легком режиме.
     */
    public static void NormalBotGame() {
        Map map = new Map();
        map.DrawSndMap(map.o_player_data, map.x_player_data);
        Human player = new Human();
        NormalBot bot = new NormalBot();
        while (true) {
            boolean fstCheck = player.Move(map.x_player_data, map.o_player_data, map, true);
            if (!fstCheck)
                System.out.println("\nНет доступных ходов. Ходит другой игрок\n");
            boolean sndCheck = bot.Move(map.o_player_data, map.x_player_data, map, false);
            if (!sndCheck) {
                if (!fstCheck) {
                    System.out.println("\nКонец игры!\n");
                    PrintWinner(map);
                    return;
                }
                System.out.println("\nНет доступных ходов. Ходит другой игрок\n");
            }
        }
    }

    /** метод CoolBotGame - игра с компьютером в продвинутом режиме.
     */
    public static void CoolBotGame() {
        Map map = new Map();
        map.DrawSndMap(map.o_player_data, map.x_player_data);
        Human player = new Human();
        CoolBot bot = new CoolBot();
        while (true) {
            boolean fstCheck = player.Move(map.x_player_data, map.o_player_data, map, true);
            if (!fstCheck)
                System.out.println("\nНет доступных ходов. Ходит другой игрок\n");
            boolean sndCheck = bot.Move(map.o_player_data, map.x_player_data, map, false);
            if (!sndCheck) {
                if (!fstCheck) {
                    System.out.println("\nКонец игры!\n");
                    PrintWinner(map);
                    return;
                }
                System.out.println("\nНет доступных ходов. Ходит другой игрок\n");
            }
        }
    }

    /** метод PVPGame - игра для двоих игроков.
     */
    public static void PVPGame() {
        Map map = new Map();
        map.DrawSndMap(map.o_player_data, map.x_player_data);
        Human fst_player = new Human();
        Human snd_player = new Human();
        while (true) {
            boolean fstCheck = fst_player.Move(map.x_player_data, map.o_player_data, map, true);
            if (!fstCheck)
                System.out.println("\nНет доступных ходов. Ходит другой игрок\n");
            boolean sndCheck = snd_player.Move(map.o_player_data, map.x_player_data, map, false);
            if (!sndCheck) {
                if (!fstCheck) {
                    System.out.println("\nКонец игры!\n");
                    int[] points = CountPoints(map);
                    if (points[1] > max_points)
                        max_points = points[1];
                    PrintWinner(map);
                    return;
                }
                System.out.println("\nНет доступных ходов. Ходит другой игрок\n");
            }
        }
    }

    /** метод CountPoints - подсчет очков.
     */
    private static int[] CountPoints(Map map) {
        int[] points = new int[] {0, 0};
        for (boolean[] row : map.x_player_data) {
            for (boolean el : row) {
                if (el)
                    points[0]++;
            }
        }
        if (points[0] > max_points)
            max_points = points[0];
        for (boolean[] row : map.o_player_data) {
            for (boolean el : row) {
                if (el)
                    points[1]++;
            }
        }
        return points;
    }

    /** метод PrintWinner - вывод победителя игры.
     */
    private static void PrintWinner(Map map) {
        int[] points = CountPoints(map);
        if (points[0] > points[1]) {
            System.out.println("\nВыиграл первый игрок со счетом " + points[0] + " очков\n");
        } else if (points[0] < points[1]) {
            System.out.println("\nВыиграл второй игрок со счетом" + points[1] + " очков\n");
        } else {
            System.out.println("\nНичья!\n");
        }
    }
}

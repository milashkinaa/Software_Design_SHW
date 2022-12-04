import java.util.Scanner;

/** class Main - здесь осуществляется запуск игры,
 * выводим приветственное сообщение для пользователя,
 * просим его ввести число в зависимости от выбранной команды и вызываем соответствующий метод
 * если число ввели неверно, выводится соответствующее сообщение об ошибке
 */
public class Main {
    public static void main(String[] args) {
        int input = -1;
        do {
            System.out.println("\nЗдравствуйте! Давайте начнем игру в реверси!\n" +
                    "\nВыберите режим игры:\n" +
                    "1 - игра в лёгком режиме с компьютером\n" +
                    "2 - игра в продвинутом режиме с компьютером\n" +
                    "3 - игра для двоих\n" +
                    "4 - вывести рекордное количество очков\n" +
                    "0 - выйти из приложения\n");
            Scanner in = new Scanner(System.in);
            boolean is_input = false;
            while (!is_input) {
                is_input = in.hasNextInt();
                if (!is_input) {
                    System.out.println("\nНеправильный ввод! Попробуйте ещё раз!\n");
                }
            }
            input = in.nextInt();
            switch (input) {
                case 0:
                    break;
                case 1: {
                    Game.NormalBotGame();
                    break;
                }
                case 2: {
                    Game.CoolBotGame();
                    break;
                }
                case 3: {
                    Game.PVPGame();
                    break;
                }
                case 4:
                    System.out.println("\nВаш рекорд: " + Game.max_points);
                default:
                    System.out.println("\nНеправильный ввод! Попробуйте ещё раз!\n");
            }
        } while (input != 0);
    }
}
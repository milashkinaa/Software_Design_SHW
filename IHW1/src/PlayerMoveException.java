/** класс PlayerMoveException - обрабатываем случаи,
 * когда пользователь пытается ходить по недоступным клеткам
 */
public class PlayerMoveException extends RuntimeException {

    public PlayerMoveException() {
        super("Так ходить нельзя! Попробуйте ещё раз!\n");
    }
}

public interface IPlayable {
    int[] GetMove(boolean[][] fst, boolean[][] snd, Map map) throws PlayerMoveException;

    boolean Move (boolean[][] fst, boolean[][] snd, Map map, boolean is_first);
}

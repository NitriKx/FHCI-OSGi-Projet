package dcll.bowling2;

import dcll.ftsq.InterfaceScore;
import dcll.ftsq.ranking.InterfaceRanking;
import dcll.ftsq.ranking.Player;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Benoît Sauvère on 28/04/15.
 */
public class Ranking implements InterfaceRanking {

    /**
     * L'algorithme de calcul des scores.
     */
    private final InterfaceScore calculScore = new Scoring();

    @Override
    public final Player[] rank(final Player... array) {

        Player[] playersSorted = Arrays.copyOf(array, array.length);

        Arrays.sort(playersSorted, new Comparator<Player>() {
            @Override
            public int compare(final Player o1, final Player o2) {
                Integer score1 = calculScore.calcScore(o1.getGame());
                Integer score2 = calculScore.calcScore(o2.getGame());

                return score1.compareTo(score2) * -1;
            }
        });

        return playersSorted;
    }
}

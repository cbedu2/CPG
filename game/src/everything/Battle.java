package everything;

/**
 * Created by Steve on 3/5/2017.
 */

public class Battle {
    private Player user;
    private Player enemy;
    private int turnNo;

    public void playerTurn() {

        turnNo++;
        enemyTurn();
    }

    public void enemyTurn(){

        turnNo++;
    }

    public void doBattle() {
        while(!user.isDead() || !enemy.isDead()) {
            playerTurn();
        }
    }

    Battle(Player p1, Player e) {
        user = p1;
        enemy = e;
        turnNo = 1;

    }
}
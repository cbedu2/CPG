package everything.statusPackage;

import everything.Player;

/**
 * Created by Steve on 4/19/2017.
 */
public class Regeneration extends Status {
    int regenValue;

    public Regeneration (Player newUser, int numTurns, int regenHealth) {
        user = newUser;
        turns = numTurns;
        regenValue = regenHealth;
    }

    @Override
    public void statusEffectBeginning() {
        turns--;
        if (!user.isAtMaxHealth()) {
            user.increaseHealth(regenValue);
        }

    }
}

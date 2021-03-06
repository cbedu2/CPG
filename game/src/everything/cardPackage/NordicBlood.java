package everything.cardPackage;

import everything.Player;
import everything.statusPackage.*;
import everything.Card;
/**
 * Created by Steve on 3/10/2017.
 */
public class NordicBlood extends Card {
    public NordicBlood(){
        super("Reduce damage taken by 3 for the next 3 Turns","NordicBlood", "Defense", 6,7,true);
        tempCD = 0;
    }

    public void cardFunction(Player p1, Player p2){
        if(checkCooldown()){
            p1.increaseDefense(3);
            p1.addStatus(new IncreaseDefenseStatus(p1, 3, 3));
            super.setCooldown();
        }
    }
}

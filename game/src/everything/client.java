package everything;

/**
 * Created by Aaron on 3/4/17.
 */
public class client {
    public static void main(String args[]){
        boolean gameLoop = true;
        panelUI panelUIInst = new panelUI();
        int cardSelected;

        panelUIInst.setMessage("\nBattle with 'Shrak' the Ogre has begun!\n");    //this function takes a String and puts it in the message display

        while(gameLoop){
            while(panelUIInst.getCardWaiting()){

            }
            cardSelected = panelUIInst.getCardSelect(); //This gets the current selected card
                                                        //Pass into Battle
            //DO BATTLE
            panelUIInst.setCardWaitingTrue();
        }
    }
}

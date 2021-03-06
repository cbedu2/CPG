package everything;
import everything.cardPackage.*;
import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Aaron on 3/13/17.
 */
public class SaveState {
    private String savePath = "savePath/saveFile.json";
    private String shopPath = "savePath/shopFile.json";
    private int money;
    private ArrayList<String> cards;
    private ArrayList<String> cardPaths;
    private ArrayList<String> ownedCards;
    private Hand tmpHand;
    
    public SaveState(){
    	cards=new ArrayList<String>();
    	cardPaths = new ArrayList<String>();
    	ownedCards = new ArrayList<String>();
    	tmpHand= new Hand();
    	storeAllCardNames();
    	money=100;
    }

    protected void saveGame(User user){
    	JSONObject saveObj = new JSONObject();
    	saveObj.put("name",user.getName());
    	saveObj.put("maxHealth",user.getMaxHealth());
    	saveObj.put("defence", user.getDefense());
    	saveObj.put("hand",user.hand.getCardClass());
    	saveObj.put("money",money);
    	saveObj.put("level", user.getLevel());
    	saveObj.put("ownedCards",ownedCards.toArray(new String[ownedCards.size()]));
    	    	
    	try(FileWriter saveFile = new FileWriter(savePath)){
    		try {
				saveFile.write(saveObj.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("game has been saved");
    }

   protected User loadGame(){
        System.out.println("game has been loaded");
        User newPlayer;
        String name = null;
        int maxHealth = 0, defence = 0,level=0;
        String handList[] = null;
        Hand hand=null;

       String fileToString = readFile(savePath); 
       
       if(fileToString.equals("null")){
    	   return newGame();
       }
       JSONObject saveObj= stringtoJSON(fileToString);
       
       try{
    	   name = saveObj.getString("name");
    	   maxHealth = saveObj.getInt("maxHealth");
    	   defence = saveObj.getInt("defence");
    	   money = saveObj.getInt("money");
    	   level = saveObj.getInt("level");
    	   handList= getStringArray(saveObj.getJSONArray("hand"));
    	   hand = loadHand(handList);
    	   loadOwnedCards(saveObj.getJSONArray("ownedCards"));
    	   newPlayer= new User(name,maxHealth,defence,money,hand);
       }
       catch(JSONException | InstantiationException | IllegalAccessException e){
    	   e.printStackTrace();
    	   newPlayer = newGame();
    	   System.out.println("Failed to load save.");
    	   System.out.println("Loading new Game");
       }
     
       return newPlayer;
              
    }
    
    public User newGame(){
    	ownedCards.clear();
    	deleteSaveFile();
        User userPlayer = new User("Aaron", 30, 0);
        userPlayer.hand.addCard(new Block());
        addCardToOwned("Block");
        userPlayer.hand.addCard(new Cleave());
        addCardToOwned("Cleave");
        userPlayer.hand.addCard(new Mutton());
        addCardToOwned("Mutton");
        userPlayer.hand.addCard(new NordicBlood());
        addCardToOwned("NordicBlood");
        userPlayer.hand.addCard(new SavageStrike());
        addCardToOwned("SavageStrike");
        money=100;
    	return userPlayer;
    }
    
    private static Hand loadHand(String arr[]) throws InstantiationException, IllegalAccessException{
    	Class newClass= null;
    	Object obj;
    	int len = arr.length;
    	Hand hand=new Hand();
    	
    	for(int i=0;i<len;i++){
    		if(!arr[i].equals("null")){
    			
				try {	
					newClass = Class.forName(arr[i]);
					System.out.println(arr[i]);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("critical FAILURE!");
					e1.printStackTrace();
				}
    			
				obj = newClass.newInstance();
    			
    			hand.addCard((Card)obj);
    		}else{
    			break;
    		}

    	}
    	
    	return hand;
    }
    
    public static String[] getStringArray(JSONArray JArray){
    	int Jlen =JArray.length();
    	
    	String[] cardNames = new String[Jlen];
    	
    	for(int i=0;i<Jlen;i++){
    		cardNames[i] = JArray.getString(i);
    	}
		return cardNames;
    	
    }
    
    public static String readFile(String path){
    	try(BufferedReader reader = new BufferedReader(new FileReader(path))){
    		StringBuilder sb = new StringBuilder();
    		String line = reader.readLine();
    		
    		while(line!=null){
    			sb.append(line);
    			line=reader.readLine();
    		}
    		
    		return sb.toString();
    		
    	} catch (FileNotFoundException e) {
			return "null";
		} catch (IOException e) {
			return "null";
		}

    }
    
    private static JSONObject stringtoJSON(String readString){
    	
       	JSONObject json = new JSONObject(readString);

    	return json;
    }
    
    private String[] getShopCards(){
    	String JsonString = readFile(shopPath);
    	JSONObject jObj= new JSONObject(JsonString);   	
    	JSONArray shopJArray= new JSONArray(jObj.getJSONArray("ShopList"));    	
    	String[] ret = getStringArray(shopJArray);
    	return ret;
    }
    
    private void storeAllCardNames(){
    	File folder = new File("everything/cardPackage");
		File[] files = folder.listFiles();
		int len = files.length;
		String buf;
		 for(int i=0;i<len;i++){
		    if(files[i].isFile()){
		    	buf =files[i].getName();
		    	if((buf.contains(".class"))){	
		    		buf =buf.substring(0,buf.lastIndexOf(".class")).trim();
		    		cards.add(buf);
		    		cardPaths.add(buf.concat(".jpg"));
		    	}
		    }
		 }    	
    }
    public void loadOwnedCards(JSONArray jsonArray){
    	for(int i=0;i<jsonArray.length();i++){
    		ownedCards.add(jsonArray.getString(i));
    	}
    }
    public ArrayList<String> getCards(){
    	return cards;
    }
    
    public ArrayList<String> getCardPath(){
    	return cardPaths;
    }
    
    public void addCardToOwned(String newCard){
    	ownedCards.add(newCard);
    }
    
    public void depositMoney(int deposit){
    	if(deposit>0){
    		money+=deposit;
    	}
    }
    
    public void withdrawMoney( int withdrawal){
		
    	if(withdrawal<=money){
    		money-=withdrawal;
    	}    	
    }
    
    public int getMoney(){
    	System.out.println("money is:"+ money);
    	return money;
    }
    
    public boolean isOwned(String cardName){
    	for(int i =0;i<ownedCards.size();i++){
    		if(ownedCards.get(i).equals(cardName)){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public void prepareHand(String []paths){
    	paths =readImagePaths(paths);
    	try {
			tmpHand = loadHand(paths);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Hand getNewHand(){
    	return tmpHand;
    }
    
    private String[] readImagePaths(String []paths){
 
    	for(int i=0;i<paths.length;i++){
    		paths[i]=parseImagePath(paths[i]);
    		System.out.println("new hand card class:"+ paths[i]);
    	}
    	
    	return paths;
    }
    
    private String parseImagePath(String path){
    	path=path.replace("images/","everything.cardPackage.");
    	path=path.replace(".jpeg","");
    	path=path.replace(".jpg","");
    	path=path.trim();
    	
    	return path;
    }
    
    private void deleteSaveFile(){
    	File saveFile = new File(savePath);
    	saveFile.delete();
    }
   

}

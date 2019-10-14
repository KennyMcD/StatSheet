import javafx.beans.property.SimpleStringProperty;
public class Player implements Comparable<Player> {
 
     public SimpleStringProperty firstName;
     public int points;
     public int assists;
     public int rebounds;
     public int steals;
 
     public Player(String fName, int point, int assist, int rebound, int steal) {
         this.firstName = new SimpleStringProperty(fName);
         this.points = new Integer(point);
         this.assists = new Integer(assist);
         this.rebounds = new Integer(rebound);
         this.steals = new Integer(steal);
         
     }
 //--------------------------------
     public String getFirstName() {
         return firstName.get();
     }
 
     public void setFirstName(String fName) {
         firstName.set(fName);
     }
     //------------------
     public Integer getPoints() {
         return points;
     }
 
     public void setPoints(Integer point) {
         point = points;
     }
     //------------------
     public Integer getAssists() {
         return assists;
     }
 
     public void setAssists(Integer assist) {
         assist = assists;
     }
    //----------------------
     public Integer getRebounds() {
         return rebounds;
     }
 
     public void setRebounds(Integer rebound) {
         rebound = rebounds;
     }
     //----------------------
     public Integer getSteals() {
         return steals;
     }
 
     public void setSteals(Integer steal) {
         steal = steals;
     }

     public int compareTo(Player o) {
        return getFirstName().compareTo(o.getFirstName());
     }
     
     public String toString() {
        return getFirstName();
     }
     
}
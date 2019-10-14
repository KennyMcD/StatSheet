public class IntPlayer implements Comparable<IntPlayer>{
   private Integer i;
   private Player p;
   
   public IntPlayer(Player p, int in) {
         this.p = p;
         this.i = new Integer(in);
   }

   public IntPlayer(Player p, Integer in) {
         this.p = p;
         this.i = in;
   }
   
   
    public void setP(Player p) {
         this.p = p;
     }
    
     public Player getP() {
         return p;
     }
     
   public void setInt(Integer i) {
         this.i = i;
     }
   
   public Integer getInt() {
      return i;
   }
   
   public int compareTo(IntPlayer o) {
      return i.compareTo(o.getInt());
   }
     
     public String toString() {
        return i+" ("+p.getFirstName()+")";
     }
} 

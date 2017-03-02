
public class test {
	public static void main(String args[]){
		String s = "What would you like to drink?|water|Would you like ice/and lemon?";
		String[] ar = s.split("[|]+");
		for(String str : ar){
			System.out.println(str);
		}
	}
}

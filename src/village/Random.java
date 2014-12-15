package village;

public class Random {

	public static String randName(){
		StringBuffer sb = new StringBuffer();
		if(randBool()){		//prefix
			sb.append(randPrefix());
		}
		sb.append(randMiddle());
		if(randBool()){
			sb.append(randSuffix());
		}
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}
	
	private static String randSuffix() {
		int amount = 10;
		int a = 0 + (int) (Math.random() * (amount + 1));
		switch(a){
			case 0:
				return "ville";
			case 1:
				return " city";
			case 2:
				return "town";
			case 3:
				return "ar";
			case 4:
				return "polis";
			case 5:
				return "isco";
			case 6:
				return "as";
			case 7:
				return "tera";
			case 8:
				return " DC";
			case 9:
				return "bottom";
			case 10:
				return "rock";
		}
		return null;
	}
	
	private static String randMiddle() {
		int amount = 10;
		int a = 0 + (int) (Math.random() * (amount + 1));
		switch(a){
			case 0:
				return "porto";
			case 1:
				return "loma";
			case 2:
				return "idor";
			case 3:
				return "drem";
			case 4:
				return "tak";
			case 5:
				return "mop";
			case 6:
				return "sant";
			case 7:
				return "elar";
			case 8:
				return "verco";
			case 9:
				return "decal";
			case 10:
				return "erito";
		}
		return null;
	}

	private static String randPrefix() {
		int amount = 10;
		int a = 0 + (int) (Math.random() * (amount + 1));
		switch(a){
			case 0:
				return "San";
			case 1:
				return "Al";
			case 2:
				return "El";
			case 3:
				return "Ter";
			case 4:
				return "Tol";
			case 5:
				return "Fil";
			case 6:
				return "Go";
			case 7:
				return "Vo";
			case 8:
				return "As";
			case 9:
				return "Del";
			case 10:
				return "Po";
				
		}
		return null;
	}

	public static boolean randBool(){
		return (int)(Math.random() * 2) == 1;
	}
	
	public static float randFloat(float min, float max){
		return (float) (max - (Math.random() * (max - min)));
	}
}

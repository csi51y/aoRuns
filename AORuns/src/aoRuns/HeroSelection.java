package aoRuns;
import java.util.Scanner;

class SearchName {
	private int ind;
	private String[] nameHero = { "Azir", "Akali", "Alistar", "Amumu", "Anivia", 
			"Ahri", "Aatrox", "Aurelion Sol", "Bard", "Blitzcrank", "Braum", 
			"Brand", "Vi", "Warwick", "Varus", "Veigar", "Vayne", "Vel'Koz", 
			"Viktor", "Vladimir", "Volibear", "Wukong", "Galio", "Gangplank", 
			"Garen", "Hecarim", "Gnar", "Gragas", "Graves", "Darius", "Jax", 
			"Jarvan IV", "Jayce", "Jhin", "Jinx", "Diana", "Dr. Mundo", 
			"Draven", "Janna", "Zyra", "Zac", "Zed", "Xerath", "Ziggs", 
			"Zilean", "Zoe", "Ivern", "Illaoi", "Irelia", "Yorick", 
			"Kah'Zix", "Kayn", "Kai'Sa", "Kalista", "Camille", "Karma", 
			"Karthus", "Kassadin", "Cassiopeia", "Katarina", "Quinn", 
			"Kayle", "Caitlyn", "Kennen", "Kindred", "Kled", "Kog'Maw", 
			"Corki", "Xin Zhao", "LeBlanc", "Leona", "Lee Sin", "Lissandra", 
			"Lulu", "Lux", "Lucian", "Malzahar", "Malphite", "Maokai", 
			"Mr Baltasar", "Miss Fortune","Morgana", "Mordekaiser", "Nami", 
			"Nasus", "Nautilus", "Nidalee", "Neeko", "Nocturne", "Nunu & Willump", 
			"Olaf", "Orianna", "Ornn", "Pyke", "Pantheon", "Poppy", "Ryze", 
			"Rumble", "Rammus", "Rek'Sai", "Rengar", "Renekton", "Riven", 
			"Rakan", "Sylas", "Swain", "Sejuani", "Sivir", "Singed", "Syndra", 
			"Sion", "Skarner", "Sona", "Soraka", "Tahm Kench", "Taliyah", "Talon", 
			"Taric", "Twisted Fate", "Twitch", "Teemo", "Trundle", "Thresh", 
			"Tryndamere", "Tristana", "Udyr", "Urgot", "Fiddlestiks", "Fizz", 
			"Fiora", "Heimerdinger", "Cho'Gath", "Shaco", "Shay", "Shen", "Shyvana", 
			"Evelynn", "Ezreal", "Ekko", "Elisa", "Annie", "Ashe", "Yasuo", "Yuumi" };
	
	// Метод, который принимает число введенное пользователем,
	// а возвращает имя чемпиона.
	void result(int a) {
		if(a < 1) {
			System.out.println("Incorrect number.");
			return;
		} else if(a > 144) {
			System.out.println("The number entered is greater "
					+ "than the number of champions.");
			return;
		}
		
		ind = a - 1;
		System.out.println(nameHero[ind]);
		int nullVar;
	}
	
}

public class HeroSelection extends Exception {
	public static void main(String args[]) {
		SearchName rClass = new SearchName();
		Scanner in = new Scanner(System.in);
		
		int abc = 0;
		System.out.println("Enter champion numbers");
		
		try {
			abc = in.nextInt();
		} catch (Exception exc) {
			System.out.println("Input Error.");
		}
		rClass.result(abc);
	}
}
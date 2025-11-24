public class Oppgave3 {
	public static void main(String[] args) {
		// Testdatasett: 45, 3600, 3661, 7322 sekunder
		int[] testSekunder = {45, 3600, 3661, 7322};
        
		for (int totalSekunder : testSekunder) {
			int timer = totalSekunder / 3600;
			int resterende = totalSekunder % 3600;
			int minutter = resterende / 60;
			int sekunder = resterende % 60;
			
			System.out.printf("%d sekunder = %d timer, %d minutter, %d sekunder\n",
				totalSekunder, timer, minutter, sekunder);
		}
	}
}

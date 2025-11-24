public class Oppgave2 {
	public static void main(String[] args) {
		// Testdatasett: {timer, minutter, sekunder}
		int[][] testData = {
			{1, 0, 0},    // 1 time
			{0, 30, 0},   // 30 minutter
			{0, 0, 45},   // 45 sekunder
			{2, 15, 10}   // 2 timer, 15 minutter, 10 sekunder
		};
        
		for (int[] data : testData) {
			int timer = data[0];
			int minutter = data[1];
			int sekunder = data[2];
			int totalSekunder = timer * 3600 + minutter * 60 + sekunder;
			System.out.printf("%d timer, %d minutter, %d sekunder = %d sekunder\n",
				timer, minutter, sekunder, totalSekunder);
		}
	}
}

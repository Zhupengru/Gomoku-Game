package Gomoku;

public class WinSituation {
	public String judge(int[][] board) {
		String winner = "";
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (board[i][j] == 0)
					continue;
				if (level(i, j, board) >= 2) {
					if (board[i][j] % 2 == 1) {
						winner = "BLACK";
						return winner;
					}
					if (board[i][j] % 2 == 0) {
						winner = "WHITE";
						return winner;
					}
				}
			}
		}
		return winner;
	}

	public static int level(int i, int j, int[][] board) {
		int level = 0;
		int cnt = 1;
		int endi1, endi2, endj1, endj2;
		int color = board[i][j] % 2;

		// 5, 4, 3 in a row
		cnt = 1;
		endi2 = i;
		endj2 = j;
		endi1 = i;
		endj1 = j;
		while (endj2 < 14 && board[endi2][endj2 + 1] % 2 == color && board[endi2][endj2 + 1] > 0) {
			endj2++;
			cnt++;
		}
		while (endj1 > 0 && board[endi1][endj1 - 1] % 2 == color && board[endi1][endj1 - 1] > 0) {
			endj1--;
			cnt++;
		}
		if (cnt >= 5)
			return 2;
		
		cnt = 1;
		endi1 = i;
		endj1 = j;
		endi2 = i;
		endj2 = j;
		while (endi2 < 14 && board[endi2 + 1][endj2] % 2 == color && board[endi2 + 1][endj2] > 0) {
			endi2++;
			cnt++;
		}
		while (endi1 > 0 && board[endi1 - 1][endj1] % 2 == color && board[endi1 - 1][endj1] > 0) {
			endi1--;
			cnt++;
		}
		if (cnt >= 5)
			return 2;
		

		cnt = 1;
		endi1 = i;
		endj1 = j;
		endi2 = i;
		endj2 = j;
		while (endi2 < 14 && endj2 < 14 && board[endi2 + 1][endj2 + 1] % 2 == color
				&& board[endi2 + 1][endj2 + 1] > 0) {
			endi2++;
			endj2++;
			cnt++;
		}
		while (endi1 > 0 && endj1 > 0 && board[endi1 - 1][endj1 - 1] % 2 == color && board[endi1 - 1][endj1 - 1] > 0) {
			endi1--;
			endj1--;
			cnt++;
		}
		if (cnt >= 5)
			return 2;
		

		cnt = 1;
		endi1 = i;
		endj1 = j;
		endi2 = i;
		endj2 = j;
		while (endi2 > 0 && endj2 < 14 && board[endi2 - 1][endj2 + 1] % 2 == color && board[endi2 - 1][endj2 + 1] > 0) {
			endi2--;
			endj2++;
			cnt++;
		}
		while (endi1 < 14 && endj1 > 0 && board[endi1 + 1][endj1 - 1] % 2 == color && board[endi1 + 1][endj1 - 1] > 0) {
			endi1++;
			endj1--;
			cnt++;
		}
		if (cnt >= 5)
			return 2;
		

		return level;
	}
}

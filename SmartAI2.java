package Gomoku;

public class SmartAI2 {
	public static int[] nextStep(int[][] board, int step) {
		int[] next = new int[2];
		if (step <= 4)
			first10step(board, next, step);
		else {
			double selflevel = 0, oppolevel = 0, selfMaxLevel = 0, oppoMaxLevel = 0;
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (board[i][j] == 0) {
						board[i][j] = step;
						selflevel = levelCompute(i, j, board);
						if (selflevel >= 5){
							next[0] = i; next[1] = j;
							return next;
						}
						selfMaxLevel = Math.max(selflevel, selfMaxLevel);
						board[i][j] = step+1;
						oppolevel = levelCompute(i, j, board); 
						oppoMaxLevel = Math.max(oppolevel, oppoMaxLevel);
						board[i][j] = 0;
					}
				}
			}
			
			if (selfMaxLevel < 0.9 && oppoMaxLevel < 0.9){
				first10step(board, next, step);
				System.out.println("use simple rule "+ next[0] + "  " + next[1]);
				return next;
			}
			
			int[][] winner = new int[15][15];
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (board[i][j] == 0) {
						board[i][j] = step;
						double selfLevel = levelCompute(i, j, board);
						board[i][j] = step + 1;
						double oppoLevel = levelCompute(i, j, board);
						board[i][j] = step;
						if (selfMaxLevel >= 5) {
							if (step % 2 == 0) winner[i][j] = 2;
							else winner[i][j] = 1;
						}
						else if (oppoMaxLevel >= 5){
							if (oppoLevel >= 5)
								winner[i][j] = recursionCompute(board, step + 1, 0);
						}
						else if(selfMaxLevel >= 1.8 && selfMaxLevel >= oppoMaxLevel){
							if (selfLevel == selfMaxLevel){
								if (step % 2 == 0)
									winner[i][j] = 2;
								else 
									winner[i][j] = 1;
							}
						}
						else if(oppoMaxLevel >= 1.8 && selfMaxLevel < oppoMaxLevel){
							if (oppoLevel >= 1.8) {
								winner[i][j] = recursionCompute(board, step + 1, 0);
							} else{
								if (step % 2 == 0)
									winner[i][j] = 1;
								else
									winner[i][j] = 2;
							}
						}
						else if (selfMaxLevel >= 0.9){
							if (selfLevel >= 0.9)
								winner[i][j] = recursionCompute(board, step + 1, 0);
						}
						else if (oppoMaxLevel >= 0.9){
							if (oppoLevel >= 0.9)
								winner[i][j] = recursionCompute(board, step + 1, 0);
						}
						else if (selfLevel >= 0.1 || oppoLevel >= 0.1){
							winner[i][j] = 3;
						}
						board[i][j] = 0;
					}
					System.out.print(winner[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println(step + " out\n");
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (winner[i][j] < 3 & winner[i][j] > 0 && winner[i][j] % 2 == step % 2) {
						next[0] = i;
						next[1] = j;
						return next;
					}
				}
			}

			first10step(board, next, step);
			if (winner[next[0]][next[1]] == 3) {
				return next;
			}
			else{
				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 15; j++) {
						if (winner[i][j] == 3) {
							next[0] = i;
							next[1] = j;
							return next;
						}
					}
				}
			}
			System.out.println("use simple rule "+ next[0] + "  " + next[1]);
			return next;
		}

		return next;
	}

	public static int recursionCompute(int[][] initboard, int step, int depth) {
		if (depth > 10)
			return 3;
		int[][] board = new int[15][15];
		int[][] winner = new int[15][15];
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				board[i][j] = initboard[i][j];
		double selflevel = 0, oppolevel = 0, selfMaxLevel = 0, oppoMaxLevel = 0;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (board[i][j] == 0) {
					board[i][j] = step;
					selflevel = levelCompute(i, j, board); 
					selfMaxLevel = Math.max(selflevel, selfMaxLevel);
					board[i][j] = step+1;
					oppolevel = levelCompute(i, j, board); 
					oppoMaxLevel = Math.max(oppolevel, oppoMaxLevel);
					board[i][j] = 0;
				}
			}
		}


		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (board[i][j] == 0) {
					board[i][j] = step;
					double selfLevel = levelCompute(i, j, board);
					board[i][j] = step + 1;
					double oppoLevel = levelCompute(i, j, board);
					board[i][j] = step;
					if (selfMaxLevel >= 5) {
						if (step % 2 == 0) return 2;
						else return 1;
					}
					else if (oppoMaxLevel >= 5){
						if (oppoLevel >= 5)
							winner[i][j] = recursionCompute(board, step + 1, depth + 1);
						else{
							if (step % 2 == 0)
								winner[i][j] = 1;
							else
								winner[i][j] = 2;
						}
					}
					else if(selfMaxLevel >= 1.8 && selfMaxLevel >= oppoMaxLevel){
						if (selfLevel == selfMaxLevel) {
							if (step % 2 == 0) return 2;
							else return 1;
						}
					}
					else if(oppoMaxLevel >= 1.8 && selfMaxLevel < oppoMaxLevel){
						if (oppoLevel >= 1.8) {
							winner[i][j] = recursionCompute(board, step + 1, depth + 1);
						} else{
							if (step % 2 == 0)
								winner[i][j] = 1;
							else
								winner[i][j] = 2;
						}
					}
					else if (selfMaxLevel >= 0.9){
						if (selfLevel >= 0.9)
							winner[i][j] = recursionCompute(board, step + 1, depth + 1);
					}
					else if (oppoMaxLevel >= 0.9){
						if (oppoLevel >= 0.9)
							winner[i][j] = recursionCompute(board, step + 1, depth + 1);
					}
					else if (selfLevel >= 0.1 || oppoLevel >= 0.1){
						return 3;
					}
					board[i][j] = 0;
				}
			}
		}
		
		/*if (selfLevel >= 0.1 || oppoLevel >= 0.1) {
			WinSituation checkwin = new WinSituation();
			String win;
			win = checkwin.judge(board);
			if (win.equals("BLACK"))
				return 1;
			if (win.equals("WHITE"))
				return 2;
			winner[i][j] = recursionCompute(board, step + 1, depth + 1);
		}*/

		

		//boolean self = false, oppo = false;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (winner[i][j] < 3 && winner[i][j] > 0 && winner[i][j] % 2 == step % 2) {
					if (step % 2 == 0)
						return 2;
					else
						return 1;
				}
			}
		}
		for (int i = 0; i < 15; i++) 
			for (int j = 0; j < 15; j++) 
				if(winner[i][j] == 3) return 3;
				
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (winner[i][j] < 3 && winner[i][j] > 0 && winner[i][j] % 2 != step % 2) {
					if (step % 2 == 0)
						return 1;
					else
						return 2;
				}
			}
		}
		
		return 3;
	}

	public static int[] first10step(int[][] board, int[] next, int step) {
		double[][] selflevel = new double[15][15];
		double[][] oppolevel = new double[15][15];
		double selfMaxLevel = 0;
		double oppoMaxLevel = 0;
		if (step == 2) {
			if (board[7][7] > 0) {
				next[0] = 6;
				next[1] = 6;
				return next;
			} else {
				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 15; j++) {
						if (board[i][j] > 0) {
							if (i < 7)
								next[0] = i + 1;
							else
								next[0] = i - 1;
							if (j < 7)
								next[1] = j + 1;
							else
								next[1] = j - 1;
						}
					}
				}
			}
			return next;
		}

		int tempi = 0, tempj = 0; 
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (board[i][j] == 0) {
					board[i][j] = step;
					selflevel[i][j] = levelCompute(i, j, board);
					board[i][j] = step + 1;
					oppolevel[i][j] = levelCompute(i, j, board);
					if (selflevel[i][j] > selfMaxLevel) {
						selfMaxLevel = selflevel[i][j];
						next[0] = i;
						next[1] = j;
					}
					if (oppolevel[i][j] > oppoMaxLevel) {
						oppoMaxLevel = oppolevel[i][j];
						tempi = i;
						tempj = j;
					}
					board[i][j] = 0;
				}
			}
		}

	

		/*
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (board[i][j] == 0) {
					board[i][j] = step + 1;
					oppolevel[i][j] = levelCompute(i, j, board);
					if (oppolevel[i][j] > oppoMaxLevel) {
						oppoMaxLevel = oppolevel[i][j];
						tempi = i;
						tempj = j;
					}
					board[i][j] = 0;
				}
			}
		}*/
		System.out.println(selfMaxLevel + "  " + oppoMaxLevel);
		if(true);
		/*if (selfMaxLevel >= oppoMaxLevel);
			//return next;
		else {
			next[0] = tempi;
			next[1] = tempj;
			return next;
		}*/
		
		if (selfMaxLevel >= 5) return next;
		if (oppoMaxLevel >= 5) {
			next[0] = tempi;
			next[1] = tempj;
			return next;
		}
		
		if (selfMaxLevel >= oppoMaxLevel)
			return next;
		else {
			next[0] = tempi;
			next[1] = tempj;
			return next;
		}
	}

	public static double levelCompute(int i, int j, int[][] board) {
		// 5 in a row : 5
		// 4 in a row : 2.4, 0.9
		double level = 0;
		int cnt = 1;
		int endi1, endi2, endj1, endj2;
		int color = board[i][j] % 2;

		// y = 0;
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
			return 5;
		if (cnt == 4) {
			if ((endj1 > 0 && board[endi1][endj1 - 1] == 0) && (endj2 < 14 && board[endi2][endj2 + 1] == 0))
				level += 2.4;
			else if ((endj1 > 0 && board[endi1][endj1 - 1] == 0) || (endj2 < 14 && board[endi2][endj2 + 1] == 0))
				level += 1.3;
		}
		if (cnt == 3) {
			if ((endj2 < 13 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] % 2 == color
					&& board[endi2][endj2 + 2] > 0)
					&& (endj1 > 1 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] % 2 == color
							&& board[endi1][endj1 - 2] > 0))
				level += 2.4;
			else if ((endj2 < 13 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] % 2 == color
					&& board[endi2][endj2 + 2] > 0)
					|| (endj1 > 1 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] % 2 == color
							&& board[endi1][endj1 - 2] > 0))
				level += 1.2;
			else if ((endj1 > 0 && board[endi1][endj1 - 1] == 0)
					&& (endj2 < 13 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] == 0))
				level++;
			else if ((endj1 > 1 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] == 0)
					&& (endj2 < 14 && board[endi2][endj2 + 1] == 0))
				level++;
		}
		if (cnt == 2)
			if ((endj2 < 12 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] % 2 == color
					&& board[endi2][endj2 + 2] > 0 && board[endi2][endj2 + 3] == 0 && endj1 > 0
					&& board[endi1][endj1 - 1] == 0)
					|| (endj1 > 2 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] % 2 == color
							&& board[endi1][endj1 - 2] > 0 && board[endi1][endj1 - 3] == 0 && endj2 < 14
							&& board[endi2][endj2 + 1] == 0))
				level += 0.9;
			else
				level += 0.1;
		if (cnt == 1) {
			if ((endj2 < 11 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] % 2 == color
					&& board[endi2][endj2 + 2] > 0 && board[endi2][endj2 + 3] % 2 == color
					&& board[endi2][endj2 + 3] > 0 && board[endi2][endj2 + 4] % 2 == color
					&& board[endi2][endj2 + 4] > 0)
					&& (endj1 > 3 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] % 2 == color
							&& board[endi1][endj1 - 2] > 0 && board[endi1][endj1 - 3] % 2 == color
							&& board[endi1][endj1 - 3] > 0 && board[endi1][endj1 - 4] % 2 == color
							&& board[endi1][endj1 - 4] > 0))
				level += 2.4;
			else if ((endj2 < 11 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] % 2 == color
					&& board[endi2][endj2 + 2] > 0 && board[endi2][endj2 + 3] % 2 == color
					&& board[endi2][endj2 + 3] > 0 && board[endi2][endj2 + 4] % 2 == color
					&& board[endi2][endj2 + 4] > 0)
					|| (endj1 > 3 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] % 2 == color
							&& board[endi1][endj1 - 2] > 0 && board[endi1][endj1 - 3] % 2 == color
							&& board[endi1][endj1 - 3] > 0 && board[endi1][endj1 - 4] % 2 == color
							&& board[endi1][endj1 - 4] > 0))
				level += 1.2;
			else if ((endj2 < 11 && board[endi2][endj2 + 1] == 0 && board[endi2][endj2 + 2] % 2 == color
					&& board[endi2][endj2 + 2] > 0 && board[endi2][endj2 + 3] % 2 == color
					&& board[endi2][endj2 + 3] > 0 && board[endi2][endj2 + 4] == 0 && endj1 > 0
					&& board[endi1][endj1 - 1] == 0)
					|| (endj1 > 3 && board[endi1][endj1 - 1] == 0 && board[endi1][endj1 - 2] % 2 == color
							&& board[endi1][endj1 - 2] > 0 && board[endi1][endj1 - 3] % 2 == color
							&& board[endi1][endj1 - 3] > 0 && board[endi1][endj1 - 4] == 0 && endj2 < 14
							&& board[endi2][endj2 + 1] == 0))
				level += 0.9;
		}

		// x = 0;
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
			return 5;
		if (cnt == 4) {
			if ((endi1 > 0 && board[endi1 - 1][endj1] == 0) && (endi2 < 14 && board[endi2 + 1][endj2] == 0))
				level += 2.4;
			if ((endi1 > 0 && board[endi1 - 1][endj1] == 0) || (endi2 < 14 && board[endi2 + 1][endj2] == 0))
				level += 1.3;
		}
		if (cnt == 3) {
			if ((endi2 < 13 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] % 2 == color
					&& board[endi2 + 2][endj2] > 0)
					&& (endi1 > 1 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] % 2 == color
							&& board[endi1 - 2][endj1] > 0))
				level += 2.4;
			else if ((endi2 < 13 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] % 2 == color
					&& board[endi2 + 2][endj2] > 0)
					|| (endi1 > 1 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] % 2 == color
							&& board[endi1 - 2][endj1] > 0))
				level += 1.2;
			else if ((endi1 > 0 && board[endi1 - 1][endj1] == 0)
					&& (endi2 < 13 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] == 0))
				level++;
			else if ((endi1 > 1 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] == 0)
					&& (endi2 < 14 && board[endi2 + 1][endj2] == 0))
				level++;
		}
		if (cnt == 2)
			if ((endi2 < 12 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] % 2 == color
					&& board[endi2 + 2][endj2] > 0 && board[endi2 + 3][endj2] == 0 && endi1 > 0
					&& board[endi1 - 1][endj1] == 0)
					|| (endi1 > 2 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] % 2 == color
							&& board[endi1 - 2][endj1] > 0 && board[endi1 - 3][endj1] == 0 && endi2 < 14
							&& board[endi2 + 1][endj2] == 0))
				level += 0.9;
			else
				level += 0.1;
		if (cnt == 1) {
			if ((endi2 < 11 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] % 2 == color
					&& board[endi2 + 2][endj2] > 0 && board[endi2 + 3][endj2] % 2 == color
					&& board[endi2 + 3][endj2] > 0 && board[endi2 + 4][endj2] % 2 == color
					&& board[endi2 + 4][endj2] > 0)
					&& (endi1 > 3 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] % 2 == color
							&& board[endi1 - 2][endj1] > 0 && board[endi1 - 3][endj1] % 2 == color
							&& board[endi1 - 3][endj1] > 0 && board[endi1 - 4][endj1] % 2 == color
							&& board[endi1 - 4][endj1] > 0))
				level += 2.4;
			else if ((endi2 < 11 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] % 2 == color
					&& board[endi2 + 2][endj2] > 0 && board[endi2 + 3][endj2] % 2 == color
					&& board[endi2 + 3][endj2] > 0 && board[endi2 + 4][endj2] % 2 == color
					&& board[endi2 + 4][endj2] > 0)
					|| (endi1 > 3 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] % 2 == color
							&& board[endi1 - 2][endj1] > 0 && board[endi1 - 3][endj1] % 2 == color
							&& board[endi1 - 3][endj1] > 0 && board[endi1 - 4][endj1] % 2 == color
							&& board[endi1 - 4][endj1] > 0))
				level += 1.2;
			else if ((endi2 < 11 && board[endi2 + 1][endj2] == 0 && board[endi2 + 2][endj2] % 2 == color
					&& board[endi2 + 2][endj2] > 0 && board[endi2 + 3][endj2] % 2 == color
					&& board[endi2 + 3][endj2] > 0 && board[endi2 + 4][endj2] == 0 && endi1 > 0
					&& board[endi1 - 1][endj1] == 0)
					|| (endi1 > 3 && board[endi1 - 1][endj1] == 0 && board[endi1 - 2][endj1] % 2 == color
							&& board[endi1 - 2][endj1] > 0 && board[endi1 - 3][endj1] % 2 == color
							&& board[endi1 - 3][endj1] > 0 && board[endi1 - 4][endj1] == 0 && endi2 < 14
							&& board[endi2 + 1][endj2] == 0))
				level += 0.9;
		}

		// y = -x;
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
			return 5;
		if (cnt == 4) {
			if ((endi1 > 0 && endj1 > 0 && board[endi1 - 1][endj1 - 1] == 0)
					&& (endi2 < 14 && endj2 < 14 && board[endi2 + 1][endj2 + 1] == 0))
				level += 2.4;
			if ((endi1 > 0 && endj1 > 0 && board[endi1 - 1][endj1 - 1] == 0)
					|| (endi2 < 14 && endj2 < 14 && board[endi2 + 1][endj2 + 1] == 0))
				level += 1.3;
		}
		if (cnt == 3) {
			if ((endi2 < 13 && endj2 < 13 && board[endi2 + 1][endj2 + 1] == 0
					&& board[endi2 + 2][endj2 + 2] % 2 == color && board[endi2 + 2][endj2 + 2] > 0)
					&& (endi1 > 1 && endj1 > 1 && board[endi1 - 1][endj1 - 1] == 0
							&& board[endi1 - 2][endj1 - 2] % 2 == color && board[endi1 - 2][endj1 - 2] > 0))
				level += 2.4;
			else if ((endi2 < 13 && endj2 < 13 && board[endi2 + 1][endj2 + 1] == 0
					&& board[endi2 + 2][endj2 + 2] % 2 == color && board[endi2 + 2][endj2 + 2] > 0)
					|| (endi1 > 1 && endj1 > 1 && board[endi1 - 1][endj1 - 1] == 0
							&& board[endi1 - 2][endj1 - 2] % 2 == color && board[endi1 - 2][endj1 - 2] > 0))
				level += 1.2;
			else if ((endi1 > 0 && endj1 > 0 && board[endi1 - 1][endj1 - 1] == 0) && (endi2 < 13 && endj2 < 13
					&& board[endi2 + 1][endj2 + 1] == 0 && board[endi2 + 2][endj2 + 2] == 0))
				level++;
			else if ((endi1 > 1 && endj1 > 1 && board[endi1 - 1][endj1 - 1] == 0 && board[endi1 - 2][endj1 - 2] == 0)
					&& (endi2 < 14 && endj2 < 14 && board[endi2 + 1][endj2 + 1] == 0))
				level++;
		}
		if (cnt == 2)
			if ((endi2 < 12 && endj2 < 12 && board[endi2 + 1][endj2 + 1] == 0
					&& board[endi2 + 2][endj2 + 2] % 2 == color && board[endi2 + 2][endj2 + 2] > 0
					&& board[endi2 + 3][endj2 + 3] == 0 && endi1 > 0 && endj1 > 0 && board[endi1 - 1][endj1 - 1] == 0)
					|| (endi1 > 2 && endj1 > 2 && board[endi1 - 1][endj1 - 1] == 0
							&& board[endi1 - 2][endj1 - 2] % 2 == color && board[endi1 - 2][endj1 - 2] > 0
							&& board[endi1 - 3][endj1 - 3] == 0 && endi2 < 14 && endj2 < 14
							&& board[endi2 + 1][endj2 + 1] == 0))
				level += 0.9;
			else
				level += 0.1;
		if (cnt == 1) {
			if ((endi2 < 11 && endj2 < 11 && board[endi2 + 1][endj2 + 1] == 0
					&& board[endi2 + 2][endj2 + 2] % 2 == color && board[endi2 + 2][endj2 + 2] > 0
					&& board[endi2 + 3][endj2 + 3] % 2 == color && board[endi2 + 3][endj2 + 3] > 0
					&& board[endi2 + 4][endj2 + 4] % 2 == color && board[endi2 + 4][endj2 + 4] > 0)
					&& (endi1 > 3 && endj1 > 3 && board[endi1 - 1][endj1 - 1] == 0
							&& board[endi1 - 2][endj1 - 2] % 2 == color && board[endi1 - 2][endj1 - 2] > 0
							&& board[endi1 - 3][endj1 - 3] % 2 == color && board[endi1 - 3][endj1 - 3] > 0
							&& board[endi1 - 4][endj1 - 4] % 2 == color && board[endi1 - 4][endj1 - 4] > 0))
				level += 2.4;
			else if ((endi2 < 11 && endj2 < 11 && board[endi2 + 1][endj2 + 1] == 0
					&& board[endi2 + 2][endj2 + 2] % 2 == color && board[endi2 + 2][endj2 + 2] > 0
					&& board[endi2 + 3][endj2 + 3] % 2 == color && board[endi2 + 3][endj2 + 3] > 0
					&& board[endi2 + 4][endj2 + 4] % 2 == color && board[endi2 + 4][endj2 + 4] > 0)
					|| (endi1 > 3 && endj1 > 3 && board[endi1 - 1][endj1 - 1] == 0
							&& board[endi1 - 2][endj1 - 2] % 2 == color && board[endi1 - 2][endj1 - 2] > 0
							&& board[endi1 - 3][endj1 - 3] % 2 == color && board[endi1 - 3][endj1 - 3] > 0
							&& board[endi1 - 4][endj1 - 4] % 2 == color && board[endi1 - 4][endj1 - 4] > 0))
				level += 1.2;
			else if ((endi2 < 11 && endj2 < 11 && board[endi2 + 1][endj2 + 1] == 0
					&& board[endi2 + 2][endj2 + 2] % 2 == color && board[endi2 + 2][endj2 + 2] > 0
					&& board[endi2 + 3][endj2 + 3] % 2 == color && board[endi2 + 3][endj2 + 3] > 0
					&& board[endi2 + 4][endj2 + 4] == 0 && endi1 > 0 && endj1 > 0 && board[endi1 - 1][endj1 - 1] == 0)
					|| (endi1 > 3 && endj1 > 3 && board[endi1 - 1][endj1 - 1] == 0
							&& board[endi1 - 2][endj1 - 2] % 2 == color && board[endi1 - 2][endj1 - 2] > 0
							&& board[endi1 - 3][endj1 - 3] % 2 == color && board[endi1 - 3][endj1 - 3] > 0
							&& board[endi1 - 4][endj1 - 4] == 0 && endi2 < 14 && endj2 < 14
							&& board[endi2 + 1][endj2 + 1] == 0))
				level += 0.9;
		}

		// y = x;
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
			return 5;
		if (cnt == 4) {
			if ((endi1 < 14 && endj1 > 0 && board[endi1 + 1][endj1 - 1] == 0)
					&& (endi2 > 0 && endj2 < 14 && board[endi2 - 1][endj2 + 1] == 0))
				level += 2.4;
			if ((endi1 < 14 && endj1 > 0 && board[endi1 + 1][endj1 - 1] == 0)
					|| (endi2 > 0 && endj2 < 14 && board[endi2 - 1][endj2 + 1] == 0))
				level += 1.3;
		}
		if (cnt == 3) {
			if ((endi2 > 1 && endj2 < 13 && board[endi2 - 1][endj2 + 1] == 0 && board[endi2 - 2][endj2 + 2] % 2 == color
					&& board[endi2 - 2][endj2 + 2] > 0)
					&& (endi1 < 13 && endj1 > 1 && board[endi1 + 1][endj1 - 1] == 0
							&& board[endi1 + 2][endj1 - 2] % 2 == color && board[endi1 + 2][endj1 - 2] > 0))
				level += 2.4;
			else if ((endi2 > 1 && endj2 < 13 && board[endi2 - 1][endj2 + 1] == 0 && board[endi2 - 2][endj2 + 2] % 2 == color
					&& board[endi2 - 2][endj2 + 2] > 0)
					|| (endi1 < 13 && endj1 > 1 && board[endi1 + 1][endj1 - 1] == 0
							&& board[endi1 + 2][endj1 - 2] % 2 == color && board[endi1 + 2][endj1 - 2] > 0))
				level += 1.2;
			else if ((endi1 < 14 && endj1 > 0 && board[endi1 + 1][endj1 - 1] == 0) && (endi2 > 1 && endj2 < 13
					&& board[endi2 - 1][endj2 + 1] == 0 && board[endi2 - 2][endj2 + 2] == 0))
				level++;
			else if ((endi1 > 1 && endj1 < 13 && board[endi1 - 1][endj1 + 1] == 0 && board[endi1 - 2][endj1 + 2] == 0)
					&& (endi2 < 14 && endj2 > 0 && board[endi2 + 1][endj2 - 1] == 0))
				level++;
		}
		if (cnt == 2)
			if ((endi2 > 2 && endj2 < 12 && board[endi2 - 1][endj2 + 1] == 0 && board[endi2 - 2][endj2 + 2] % 2 == color
					&& board[endi2 - 2][endj2 + 2] > 0 && board[endi2 - 3][endj2 + 3] == 0 && endi1 < 14 && endj1 > 0
					&& board[endi1 + 1][endj1 - 1] == 0)
					|| (endi1 < 12 && endj1 > 2 && board[endi1 + 1][endj1 - 1] == 0
							&& board[endi1 + 2][endj1 - 2] % 2 == color && board[endi1 + 2][endj1 - 2] > 0
							&& board[endi1 + 3][endj1 - 3] == 0 && endi2 > 0 && endj2 < 14
							&& board[endi2 - 1][endj2 + 1] == 0))
				level += 0.9;
			else
				level += 0.1;
		if (cnt == 1) {
			if ((endi2 > 3 && endj2 < 11 && board[endi2 - 1][endj2 + 1] == 0 && board[endi2 - 2][endj2 + 2] % 2 == color
					&& board[endi2 - 2][endj2 + 2] > 0 && board[endi2 - 3][endj2 + 3] % 2 == color
					&& board[endi2 - 3][endj2 + 3] > 0 && board[endi2 - 4][endj2 + 4] % 2 == color
					&& board[endi2 - 4][endj2 + 4] > 0)
					&& (endi1 < 11 && endj1 > 3 && board[endi1 + 1][endj1 - 1] == 0
							&& board[endi1 + 2][endj1 - 2] % 2 == color && board[endi1 + 2][endj1 - 2] > 0
							&& board[endi1 + 3][endj1 - 3] % 2 == color && board[endi1 + 3][endj1 - 3] > 0
							&& board[endi1 + 4][endj1 - 4] % 2 == color && board[endi1 + 4][endj1 - 4] > 0))
				level += 2.4;
			else if ((endi2 > 3 && endj2 < 11 && board[endi2 - 1][endj2 + 1] == 0 && board[endi2 - 2][endj2 + 2] % 2 == color
					&& board[endi2 - 2][endj2 + 2] > 0 && board[endi2 - 3][endj2 + 3] % 2 == color
					&& board[endi2 - 3][endj2 + 3] > 0 && board[endi2 - 4][endj2 + 4] % 2 == color
					&& board[endi2 - 4][endj2 + 4] > 0)
					|| (endi1 < 11 && endj1 > 3 && board[endi1 + 1][endj1 - 1] == 0
							&& board[endi1 + 2][endj1 - 2] % 2 == color && board[endi1 + 2][endj1 - 2] > 0
							&& board[endi1 + 3][endj1 - 3] % 2 == color && board[endi1 + 3][endj1 - 3] > 0
							&& board[endi1 + 4][endj1 - 4] % 2 == color && board[endi1 + 4][endj1 - 4] > 0))
				level += 1.2;
			else if ((endi2 > 3 && endj2 < 11 && board[endi2 - 1][endj2 + 1] == 0
					&& board[endi2 - 2][endj2 + 2] % 2 == color && board[endi2 - 2][endj2 + 2] > 0
					&& board[endi2 - 3][endj2 + 3] % 2 == color && board[endi2 - 3][endj2 + 3] > 0
					&& board[endi2 - 4][endj2 + 4] == 0 && endi1 < 14 && endj1 > 0 && board[endi1 + 1][endj1 - 1] == 0)
					|| (endi1 < 11 && endj1 > 3 && board[endi1 + 1][endj1 - 1] == 0
							&& board[endi1 + 2][endj1 - 2] % 2 == color && board[endi1 + 2][endj1 - 2] > 0
							&& board[endi1 + 3][endj1 - 3] % 2 == color && board[endi1 + 3][endj1 - 3] > 0
							&& board[endi1 + 4][endj1 - 4] == 0 && endi2 > 0 && endj2 < 14
							&& board[endi2 - 1][endj2 + 1] == 0))
				level += 0.9;
		}

		return level;
	}
}

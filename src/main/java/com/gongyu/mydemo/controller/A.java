package com.gongyu.mydemo.controller;

public class A {
    public static void main(String[] args) {


            char[][] board = {
                    // 初始数独问题
                    {'4', '.', '.', '3', '.', '.', '7', '.', '.'},
                    {'.', '.', '.', '.', '.', '1', '6', '.', '.'},
                    {'.', '.', '1', '5', '.', '9', '.', '.', '8'},
                    {'.', '.', '2', '.', '.', '.', '.', '.', '6'},
                    {'.', '3', '.', '.', '.', '.', '4', '.', '1'},
                    {'.', '.', '.', '.', '7', '.', '.', '5', '.'},
                    {'6', '.', '.', '.', '.', '.', '.', '2', '4'},
                    {'.', '9', '.', '.', '6', '.', '.', '.', '.'},
                    {'8', '5', '.', '4', '.', '.', '.', '.', '.'}
            };

            if (solveSudoku(board)) {
                for (char[] row : board) {
                    for (char num : row) {
                        System.out.print(num + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("错误");
            }
        }

        private static boolean isValid(char[][] board, int row, int col, char val) {
            for (int i = 0; i < 9; i++) {
                if (board[i][col] == val) return false;
                if (board[row][i] == val) return false;
                if (board[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3] == val) return false;
            }
            return true;
        }

        private static boolean solveSudoku(char[][] board) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') {
                        for (char k = '1'; k <= '9'; k++) {
                            if (isValid(board, i, j, k)) {
                                board[i][j] = k;
                                if (solveSudoku(board)) return true;
                                else board[i][j] = '.';
                            }
                        }
                        return false;
                    }
                }
            }
            return true;


    }
    public int trans ( char ch){
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;


        }
    }
    String transStr (int ch) {
        switch (ch) {
            case 1:
                return "I";
            case 5:
                return "V";
            case 10:
                return "X";
            case 50:
                return "L";
            case 100:
                return "C";
            case 500:
                return "D";
            case 1000:
                return "M";
            case 900:
                return "CM";
            case 400:
                return "CD";
            case 90:
                return "XC";
            case 40:
                return "XL";
            case 9:
                return "IX";
            case 4:
                return "IV";
            default:
                return "";

        }
    }

}

package test;

import java.util.ArrayList;
import java.util.Arrays;

//import test.Tile.Bag;

public class Board {
    Tile[][] board;
    private static Board bo;
    String[][] specialScore;

    private Board() {
        board = new Tile[15][15];
        specialScore = new String[15][15];

        specialScore[7][7] = "star";

        specialScore[1][1] = "yellow";
        specialScore[2][2] = "yellow";
        specialScore[3][3] = "yellow";
        specialScore[4][4] = "yellow";
        specialScore[1][13] = "yellow";
        specialScore[2][12] = "yellow";
        specialScore[3][11] = "yellow";
        specialScore[4][10] = "yellow";
        specialScore[10][4] = "yellow";
        specialScore[11][3] = "yellow";
        specialScore[12][2] = "yellow";
        specialScore[13][1] = "yellow";
        specialScore[10][10] = "yellow";
        specialScore[11][11] = "yellow";
        specialScore[12][12] = "yellow";
        specialScore[13][13] = "yellow";

        specialScore[0][0] = "red";
        specialScore[0][7] = "red";
        specialScore[0][14] = "red";
        specialScore[7][0] = "red";
        specialScore[7][14] = "red";
        specialScore[14][0] = "red";
        specialScore[14][7] = "red";
        specialScore[14][14] = "red";

        specialScore[0][3] = "light_blue";
        specialScore[0][11] = "light_blue";
        specialScore[2][6] = "light_blue";
        specialScore[2][8] = "light_blue";
        specialScore[3][0] = "light_blue";
        specialScore[3][7] = "light_blue";
        specialScore[3][14] = "light_blue";
        specialScore[6][2] = "light_blue";
        specialScore[6][6] = "light_blue";
        specialScore[6][8] = "light_blue";
        specialScore[6][12] = "light_blue";
        specialScore[7][3] = "light_blue";
        specialScore[7][11] = "light_blue";
        specialScore[8][2] = "light_blue";
        specialScore[8][6] = "light_blue";
        specialScore[8][8] = "light_blue";
        specialScore[8][12] = "light_blue";
        specialScore[11][0] = "light_blue";
        specialScore[11][7] = "light_blue";
        specialScore[11][14] = "light_blue";
        specialScore[12][6] = "light_blue";
        specialScore[12][8] = "light_blue";
        specialScore[14][3] = "light_blue";
        specialScore[14][11] = "light_blue";

        specialScore[1][5] = "blue";
        specialScore[1][9] = "blue";
        specialScore[5][1] = "blue";
        specialScore[5][5] = "blue";
        specialScore[5][9] = "blue";
        specialScore[5][13] = "blue";
        specialScore[9][1] = "blue";
        specialScore[9][5] = "blue";
        specialScore[9][9] = "blue";
        specialScore[9][13] = "blue";
        specialScore[13][5] = "blue";
        specialScore[13][9] = "blue";
    }

    public static Board getBoard() {
        if (bo == null)
            bo = new Board();
        return bo;
    }

    public Tile[][] getTiles() {
        return this.board.clone();
    }

    public boolean boardLegal(Word w) {

        if (isOnBoard(w) == false)
            return false;

        if (isEmpty())
            return StartOnStar(w);

        return isOk(w);
    }

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    public ArrayList<Word> getWords(Word w) {
        ArrayList<Word> ow = retArr(this.board);
        int wordLen = w.tiles.length;
        Tile[][] nb = getTiles();

        if (w.vertical == true) {
            for (int i = 0; i < wordLen; i++) {
                if (w.tiles[i] != null)
                    nb[w.row + i][w.col] = w.tiles[i];
            }
        } else if (w.vertical == false) {
            for (int i = 0; i < wordLen; i++) {
                if (w.tiles[i] != null)
                    nb[w.row][w.col + i] = w.tiles[i];
            }
        }

        ArrayList<Word> nw = retArr(nb);
        nw.removeAll(ow);
        return nw;
    }

    private ArrayList<Word> retArr(Tile[][] nb) {
        ArrayList<Word> aw = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                int z = 0;
                if (nb[i][j] != null && (nb[i + 1][j] != null) && (nb[i - 1][j] == null || i == 0)) {
                    Tile[] mw = new Tile[15];
                    z = i;

                    while (nb[z][j] != null && z <= 14) {
                        mw[z - i] = nb[z][j];
                        z++;
                    }
                    Tile[] exactSizeMw = Arrays.copyOf(mw, z - i);
                    Word nw = new Word(exactSizeMw, i, j, true);
                    aw.add(nw);
                }

                else if (nb[i][j] != null && (nb[i][j + 1] != null) && (nb[i][j - 1] == null || j == 0)) {
                    Tile[] mw = new Tile[15];
                    z = j;

                    while (nb[i][z] != null && z <= 14) {
                        mw[z - j] = nb[i][z];
                        z++;
                    }
                    Tile[] exactSizeMw = Arrays.copyOf(mw, z - j);
                    Word nw = new Word(exactSizeMw, i, j, false);
                    aw.add(nw);
                }
            }
        }
        return aw;
    }

    public int getScore(Word w) {
        int score = 0;
        int mul_bonus = 1;
        if (w.vertical) {
            for (int i = 0; i < w.tiles.length; i++) {
                if (specialScore[w.row+i][w.col] != null) {
                    if (specialScore[w.row + i][w.col].equals("light_blue"))
                        score += w.tiles[i].score * 2;
                    else if (specialScore[w.row + i][w.col].equals("blue"))
                        score += w.tiles[i].score * 3;
                    else if (specialScore[w.row + i][w.col].equals("red")) {
                        mul_bonus *= 3;
                        score += w.tiles[i].score;
                    } else if (specialScore[w.row + i][w.col].equals("yellow")
                            || specialScore[w.row + i][w.col].equals("star")) {
                        mul_bonus *= 2;
                        score += w.tiles[i].score;
                        if (specialScore[w.row + i][w.col].equals("star")){
                            specialScore[w.row + i][w.col] = null;
                        }
                    }
                } else
                    score += w.tiles[i].score;
            }
        } else {
            for (int i = 0; i < w.tiles.length; i++) {
                if (specialScore[w.row][w.col + i] != null) {
                    if (specialScore[w.row][w.col + i].equals("light_blue"))
                        score += w.tiles[i].score * 2;
                    else if (specialScore[w.row][w.col + i].equals("blue"))
                        score += w.tiles[i].score * 3;
                    else if (specialScore[w.row][w.col + i].equals("red")) {
                        mul_bonus *= 3;
                        score += w.tiles[i].score;
                    } else if (specialScore[w.row][w.col + i].equals("yellow")
                            || specialScore[w.row][w.col + i].equals("star")) {
                        mul_bonus *= 2;
                        score += w.tiles[i].score;
                        if (specialScore[w.row][w.col+i].equals("star")){
                            specialScore[w.row][w.col+i] = null;
                        }
                    }
                } else
                    score += w.tiles[i].score;

            }
        }

        return score * mul_bonus;
    }

    public int tryPlaceWord(Word w) {
        int score = 0;
        if (boardLegal(w) == true) {
            ArrayList<Word> all_words = getWords(w);
            for (int i = 0; i < all_words.size(); i++) {
                if (dictionaryLegal(all_words.get(i)) == false)
                    return 0;
                score += getScore(all_words.get(i));
            }
            putOnBoard(w);
            return score;
        }
        return 0;
    }

    void putOnBoard(Word w) {
        if (w.vertical) {
            for (int i = 0; i < w.tiles.length; i++)
                if (w.tiles[i] != null)
                    board[w.row + i][w.col] = w.tiles[i];
        } else {
            for (int i = 0; i < w.tiles.length; i++)
                if (w.tiles[i] != null)
                    board[w.row][w.col + i] = w.tiles[i];
        }
    }

    private boolean isOnBoard(Word w) {
        if (w.col > 14 || w.row > 14 || w.col < 0 || w.row < 0)
            return false;

        if (w.vertical == true && w.row + w.tiles.length > 14 || w.vertical == false && w.col + w.tiles.length > 14)
            return false;

        return true;
    }

    private boolean isEmpty() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.board[i][j] != null)
                    return false;
            }
        }
        return true;
    }

    private boolean StartOnStar(Word w) {
        if (w.vertical == true && w.col == 7 && w.row <= 7 && (w.row + w.tiles.length >= 7) ||
                w.vertical == false && w.row == 7 && w.col <= 7 && (w.col + w.tiles.length >= 7))
            return true;
        return false;
    }

    private boolean isOk(Word w) {
        Tile[] wordTiles = w.tiles;
        int startRow = w.row;
        int startCol = w.col;
        boolean isVertical = w.vertical;

        if (isVertical) {
            for (int i = 0; i < wordTiles.length; i++) {
                if (board[startRow + i][startCol] != null ||
                        board[startRow + i][startCol + 1] != null ||
                        board[startRow + i][startCol - 1] != null ||
                        board[startRow + i + 1][startCol] != null ||
                        board[startRow + i - 1][startCol] != null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < wordTiles.length; i++) {
                if (board[startRow][startCol + i] != null ||
                        board[startRow][startCol + i + 1] != null ||
                        board[startRow][startCol + i - 1] != null ||
                        board[startRow + 1][startCol + i] != null ||
                        board[startRow - 1][startCol + i] != null) {
                    return true;
                }
            }
        }
        return false;
    }
}

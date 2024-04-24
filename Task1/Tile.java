package test;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + letter;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (letter != other.letter)
            return false;
        if (score != other.score)
            return false;
        return true;
    }

    public static class Bag {
        private final int[] quantity;
        private final Tile[] tiles;

        private static Bag b;

        private Bag() {
            this.quantity = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };
            this.tiles = new Tile[] { new Tile('A', 1), new Tile('B', 3), new Tile('C', 3),
                    new Tile('D', 2), new Tile('E', 1), new Tile('F', 4),
                    new Tile('G', 2), new Tile('H', 4), new Tile('I', 1),
                    new Tile('J', 8), new Tile('K', 5), new Tile('L', 1),
                    new Tile('M', 3), new Tile('N', 1), new Tile('O', 1),
                    new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1),
                    new Tile('S', 1), new Tile('T', 1), new Tile('U', 1),
                    new Tile('V', 4), new Tile('W', 4), new Tile('X', 8),
                    new Tile('Y', 4), new Tile('Z', 10) };
        }

        public static Bag getBag() {
            if (b == null)
                b = new Bag();
            return b;
        }

        public Tile getRand() {
            if (size() == 0) {
                return null;
            }

            int num = (int) ((Math.random() * 26 + 1) - 1);
            if (this.quantity[num] > 0) {
                this.quantity[num]--;
                return this.tiles[num];
            }

            return getRand();
        }

        public Tile getTile(char c) {
            if (size() == 0 || !isValidChar(c)) {
                return null;
            }

            int index = getIndex(c);

            if (index < 0 || index >= this.quantity.length || this.quantity[index] == 0) {
                return null;
            }

            this.quantity[index]--;
            return this.tiles[index];
        }

        public int getIndex(char c) {
            return c - 'A';
        }

        private boolean isValidChar(char c) {
            return c >= 'A' && c <= 'Z';
        }

        public int size() {
            int sum = 0;
            for (int i = 0; i < quantity.length; i++) {
                sum += quantity[i];
            }
            return sum;
        }

        public int[] getQuantities() {
            return this.quantity.clone();
        }

        public void put(Tile t) {
            int[] max_quantities = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };
            int index = getIndex(t.letter);
            if (this.quantity[index] < max_quantities[index]) {
                this.quantity[index]++;
            }
        }
    }
}

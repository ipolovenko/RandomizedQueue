public class Subset {
    public static void main(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException();
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rQueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.print(rQueue.dequeue() + "\n");
        }
    }
}
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int load, index;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return load == 0;
    }

    // return the number of items on the queue
    public int size() {
        return load;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (index == array.length) resizeArray(array.length * 2);
        array[index++] = item;
        load++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item;
        int i;
        do {
            i = StdRandom.uniform(array.length);
            item = array[i];
        } while (item == null);
        array[i] = null;
        if (--load <= array.length / 4) resizeArray(array.length / 2);
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item;
        do {
            item = array[StdRandom.uniform(array.length)];
        } while (item == null);
        return item;
    }

    private void resizeArray(int newLength) {
        Item[] oldArray = array;
        array = (Item[]) new Object[newLength];
        load = 0;
        index = 0;
        for (Item i : oldArray) {
            if (i == null) continue;
            array[index++] = i;
            load++;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Item[] randomized;
            private int current = 0;

            {
                randomized = (Item[]) new Object[load];
                for (Item i : array) {
                    if (i == null) continue;
                    randomized[current++] = i;
                }
                current = 0;
                for (int i = 0, n = randomized.length; i < n; i++) {
                    exchangeItems(i, StdRandom.uniform(i + 1));
                }
            }

            @Override
            public boolean hasNext() {
                return current < randomized.length;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return randomized[current++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void exchangeItems(int i, int j) {
                if (i == j) return;
                Item exch = randomized[i];
                randomized[i] = randomized[j];
                randomized[j] = exch;
            }
        };
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> d = new RandomizedQueue<>();
        d.enqueue("first");
        StdOut.print("added first. size=" + d.size() + "\n");
        d.enqueue("last");
        StdOut.print("added last. size=" + d.size() + "\n");
        StdOut.print("dequeue: " + d.dequeue() + " size=" + d.size() + "\n");
        StdOut.print("dequeue: " + d.dequeue() + " size=" + d.size() + "\n");
        String a[] = {"one", "two", "three", "four", "five"};
        for (String s : a) {
            d.enqueue(s);
        }
        StdOut.print("Iterating over the queue randomly.\n");
        for (String s : d) {
            StdOut.print(s + "\n");
        }
        StdOut.print("Iterating over the queue randomly.\n");
        for (String s : d) {
            StdOut.print(s + "\n");
        }
        d.enqueue("last1");
        StdOut.print("added last1. size=" + d.size() + "\n");
        d.enqueue("last2");
        StdOut.print("added last2. size=" + d.size() + "\n");
        StdOut.print("sample: " + d.sample() + " size=" + d.size() + "\n");
        StdOut.print("sample: " + d.sample() + " size=" + d.size() + "\n");
        while (!d.isEmpty()) {
            StdOut.print(d.dequeue() + "\n");
        }
        StdOut.print("size=" + d.size() + "\n");
    }
}
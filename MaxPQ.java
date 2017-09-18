//from Algs 4 Sedgewick book
public class MaxPQ  <Key  extends Comparable <Key>>{
    private Key[] pq; //heap-ordered complete binary tree
    private int N = 0;//    In pq[1..N] with pq[0] unused
    
    public MaxPQ (int maxN) {
        pq = (Key[]) new Comparable [maxN+1]; 
    
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void insert(Key v) {
        pq[++N] = v;
        swim(N);
    }
    
    public Key delMax() {
        Key max = pq[1]; //retrieve max key from top
        exch(1,N--);     //Exchange with last item
        pq[N+1] = null;  //Avoid loitering
        sink(1);         //Restore heap property
        return max; 
    }
    
    private boolean more(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }
    
    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    
    private void swim(int k) {
        while (k > 1 && more(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }
    
    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && more(j, j+1)) j++;
            if (!more(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}

class BinHeap<P extends Comparable<? super P>, D>
{
    private BinHeap.Entry<P, D>[] heap;
    private int size;
    
    public BinHeap() {
        this.heap = (BinHeap.Entry<P, D>[])new BinHeap.Entry[100];
        this.size = 0;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean contains(final BinHeap.Entry<P, D> entry) {
        return entry != null && this.heap[BinHeap.Entry.access$000((BinHeap.Entry)entry)] == entry;
    }
    
    private int parent(final int n) {
        return (n + 1) / 2 - 1;
    }
    
    private int left(final int n) {
        return (n + 1) * 2 - 1;
    }
    
    private int right(final int n) {
        return (n + 1) * 2 + 1 - 1;
    }
    
    private void swap(final int n, final int n2) {
        final BinHeap.Entry<P, D> entry = this.heap[n];
        this.heap[n] = this.heap[n2];
        this.heap[n2] = entry;
        BinHeap.Entry.access$002((BinHeap.Entry)this.heap[n], n);
        BinHeap.Entry.access$002((BinHeap.Entry)this.heap[n2], n2);
    }
    
    private void lift(int n) {
        int parent;
        while (n > 0 && ((Comparable)BinHeap.Entry.access$100((BinHeap.Entry)this.heap[n])).compareTo(BinHeap.Entry.access$100((BinHeap.Entry)this.heap[parent = this.parent(n)])) < 0) {
            this.swap(n, parent);
            n = parent;
        }
    }
    
    private void sink(int n) {
        while (true) {
            int n2 = n;
            final int left = this.left(n);
            final int right = this.right(n);
            if (left < this.size && ((Comparable)BinHeap.Entry.access$100((BinHeap.Entry)this.heap[left])).compareTo(BinHeap.Entry.access$100((BinHeap.Entry)this.heap[n2])) < 0) {
                n2 = left;
            }
            if (right < this.size && ((Comparable)BinHeap.Entry.access$100((BinHeap.Entry)this.heap[right])).compareTo(BinHeap.Entry.access$100((BinHeap.Entry)this.heap[n2])) < 0) {
                n2 = right;
            }
            if (n2 == n) {
                break;
            }
            this.swap(n, n2);
            n = n2;
        }
    }
    
    private void move(final int n) {
        final BinHeap.Entry<P, D> entry = this.heap[n];
        this.lift(n);
        if (this.heap[n] == entry) {
            this.sink(n);
        }
    }
    
    public BinHeap.Entry<P, D> insert(final P p2, final D n) {
        if (p2 == null) {
            return null;
        }
        if (this.size == this.heap.length) {
            return null;
        }
        final BinHeap.Entry<P, D>[] heap = this.heap;
        final int size = this.size;
        final BinHeap.Entry entry = new BinHeap.Entry((Object)p2, (Object)n, this.size, (BinHeap.BinHeap$1)null);
        heap[size] = (BinHeap.Entry<P, D>)entry;
        final BinHeap.Entry<P, D> entry2 = (BinHeap.Entry<P, D>)entry;
        this.lift(this.size);
        ++this.size;
        return entry2;
    }
    
    public BinHeap.Entry<P, D> minimum() {
        if (this.size == 0) {
            return null;
        }
        return this.heap[0];
    }
    
    public BinHeap.Entry<P, D> extractMin() {
        if (this.size == 0) {
            return null;
        }
        this.swap(0, --this.size);
        this.sink(0);
        try {
            return this.heap[this.size];
        }
        finally {
            this.heap[this.size] = null;
        }
    }
    
    public boolean changePrio(final BinHeap.Entry<P, D> entry, final P p2) {
        if (!this.contains(entry)) {
            return false;
        }
        if (p2 == null) {
            return false;
        }
        BinHeap.Entry.access$102((BinHeap.Entry)entry, (Object)p2);
        this.move(BinHeap.Entry.access$000((BinHeap.Entry)entry));
        return true;
    }
    
    public boolean remove(final BinHeap.Entry<P, D> entry) {
        if (!this.contains(entry)) {
            return false;
        }
        --this.size;
        final int access$000 = BinHeap.Entry.access$000((BinHeap.Entry)entry);
        this.swap(access$000, this.size);
        this.move(access$000);
        this.heap[this.size] = null;
        return true;
    }
    
    private void dump(final int n, String string) {
        final BinHeap.Entry<P, D> entry = this.heap[n];
        System.out.println(string + entry.prio() + " " + entry.data());
        string += "  ";
        final int left = this.left(n);
        final int right = this.right(n);
        if (left < this.size) {
            this.dump(left, string);
            if (right < this.size) {
                this.dump(right, string);
            }
        }
    }
    
    public void dump() {
        if (this.size > 0) {
            this.dump(0, "");
        }
    }
}

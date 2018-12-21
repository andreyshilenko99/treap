package treap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TreapIterator<K extends Comparable> implements Iterator<K> {

    private Stack<Node<K>> stack;
    private Treap<K> treap;

    public TreapIterator(Treap<K> treap) {

        this.treap = treap;
        stack = new Stack<>();
        Node<K> node = this.treap.getRoot();

        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public K next() {
        if (!hasNext()) throw new NoSuchElementException();

        Node<K> smallest = stack.pop();
        if (smallest.right != null) {
            Node<K> node = smallest.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return smallest.key;
    }

    @Override
    public void remove() {
        treap.delete(next());
    }
}

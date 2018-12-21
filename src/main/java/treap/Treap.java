package treap;

import java.util.Iterator;
import java.util.function.Consumer;

public class Treap<K extends Comparable> implements Iterable<K> {

    private Node<K> root;
    private int size;

    public Treap() {
        size = 0;
        root = null;
    }

    public void insert(K key) {
        root = insert(root, key);
        size++;
    }

    private Node<K> insert(Node<K> node, K key) {
        if (node == null)
            return new Node<>(key);

        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = insert(node.left, key);
            if (node.priority > node.left.priority)
                return rotateRight(node);
        } else if (compare > 0) {
            node.right = insert(node.right, key);
            if (node.priority > node.right.priority)
                return rotateLeft(node);
        }
        return node;
    }

    private Node<K> rotateRight(Node<K> node) {
        Node<K> leftNode = node.left;
        node.left = leftNode.right;
        leftNode.right = node;
        return leftNode;
    }

    private Node<K> rotateLeft(Node<K> node) {
        Node<K> rightNode = node.right;
        node.right = rightNode.left;
        rightNode.left = node;
        return rightNode;
    }

    public void delete(K key) {
        root = delete(root, key);
        if (size != 0) size--;
    }

    private Node<K> delete(Node<K> node, K key) {
        if (node != null) {
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                node.left = delete(node.left, key);
            } else if (compare > 0) {
                node.right = delete(node.right, key);
            } else {
                if (node.left == null || node.right == null) {
                    return node.left == null ? node.right : node.left;
                } else {
                    node.key = findSmall(node.right);
                    node.right = delete(node.right, node.key);
                }
            }
        }
        return node;
    }

    public boolean contains(K key) {
        Node<K> node = root;
        while (node != null) {
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                node = node.left;
            } else if (compare > 0) {
                node = node.right;
            } else return true;
        }
        return false;
    }

    public K findSmall() {
        return findSmall(root);
    }

    private K findSmall(Node<K> searchNode) {
        Node<K> node = searchNode;
        while (node.left != null) node = node.left;
        return node.key;
    }

    public Node<K> getRoot() {
        return root;
    }

    private String toString(StringBuilder sb, Node<K> node) {
        if (node != null) {
            sb.append(node).append(", ");
            toString(sb, node.right);
            toString(sb, node.left);
        }

        return "[" + sb.toString().substring(0, sb.length() - 2) + "]";
    }

    @Override
    public String toString() {
        return toString(new StringBuilder(), root);
    }

    @Override
    public Iterator<K> iterator() {
        return new TreapIterator<>(this);
    }

    @Override
    public void forEach(Consumer<? super K> action) {
        for (K k : this) {
            action.accept(k);
        }
    }
}
package treap;

import java.util.Objects;
import java.util.Random;

class Node<K> {

    private static final Random RANDOM = new Random();

    Node<K> left;
    Node<K> right;
    K key;
    int priority;

    Node(K key) {
        this.key = key;
        priority = RANDOM.nextInt();
        left = null;
        right = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return priority == node.priority &&
                Objects.equals(left, node.left) &&
                Objects.equals(right, node.right) &&
                Objects.equals(key, node.key);
    }

    @Override
    public int hashCode() {

        return Objects.hash(left, right, key, priority);
    }

    @Override
    public String toString() {
        return "priority: " + priority + ", value: " + key;
    }
}
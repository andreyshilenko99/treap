package tests;

import org.junit.jupiter.api.Test;
import treap.Treap;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TreapTests {

    private static final int SIZE = 10;
    private Random random = new Random();

    private Set<Integer> generateValues(int size) {
        Set<Integer> set = new HashSet<>();
        IntStream.range(0, size).map(i -> random.nextInt()).forEach(set::add);
        return set;
    }

    private int getDifferentValue(Set<Integer> set) {
        int value = random.nextInt();
        while (set.contains(value)) {
            value = random.nextInt();
        }
        return value;
    }

    private Treap createTreap(Set<Integer> values) {
        Treap<Integer> integerTreap = new Treap<>();
        values.forEach(integerTreap::insert);
        return integerTreap;
    }

    private Integer getMin(Set<Integer> values) {
        return values.stream().min(Integer::compareTo).get();
    }

    @Test
    void containsTest() {
        Set<Integer> values = generateValues(SIZE);
        Treap<Integer> treap = createTreap(values);

        values.forEach(value -> assertTrue(treap.contains(value)));
        values.forEach(value -> assertFalse(treap.contains(getDifferentValue(values))));
    }

    @Test
    void insertTest() {
        Set<Integer> values = generateValues(SIZE);
        Treap<Integer> treap = createTreap(values);

        for (int i = 0; i < SIZE; i++) {
            int value = getDifferentValue(values);

            assertFalse(treap.contains(value));
            treap.insert(value);
            assertTrue(treap.contains(value));
        }
    }

    @Test
    void deleteTest() {
        Set<Integer> values = generateValues(SIZE);
        Treap<Integer> treap = createTreap(values);

        values.forEach(value -> {
            treap.delete(value);
            assertFalse(treap.contains(value));
        });
    }

    @Test
    void findSmall() {
        Set<Integer> values = generateValues(SIZE);
        Treap<Integer> treap = createTreap(values);

        for (int i = 0; i < values.size(); i++) {
            Integer min = getMin(values);
            assertEquals(min, treap.findSmall());

            treap.delete(min);
            values.remove(min);
        }
    }

    @Test
    void iteratorTest() {
        Set<Integer> values = generateValues(SIZE);
        Treap<Integer> treap = createTreap(values);

        treap.forEach(key -> assertTrue(values.contains(key)));
    }
}

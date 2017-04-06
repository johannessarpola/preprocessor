package fi.johannes.Utilities.Hashing;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Johannes on 6.4.2017.
 */
public class HashStoreTest {

    @Test
    public void storeKey() throws Exception {
        String line = "Abc abc abc";
        String line2 = "Cba cba cba";

        HashStore hashStore = new HashStore(Integer.MAX_VALUE);
        hashStore.storeKey(line, 1);
        hashStore.storeKey(line2, 2);

        assertThat(hashStore.getIndex(line), is(1));
        assertThat(hashStore.getIndex(line2), is(2));
    }

    @Test
    public void storeKey1() throws Exception {

        String line = "Abc abc abc";
        String line2 = "Abc abc abc";

        HashStore hashStore = new HashStore(Integer.MAX_VALUE);
        hashStore.storeKey(line, 1);
        hashStore.storeKey(line2, 2);

        assertThat(hashStore.getIndex("Abc abc abc"), is(1));
    }

}
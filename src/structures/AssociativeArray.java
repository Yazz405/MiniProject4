package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Alma Ordaz
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
    // +-----------+---------------------------------------------------
    // | Constants |
    // +-----------+

    /**
     * The default capacity of the initial array.
     */
    static final int DEFAULT_CAPACITY = 16;

    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The size of the associative array (the number of key/value pairs).
     */
    int size;

    /**
     * The array of key/value pairs.
     */
    KVPair<K, V> pairs[];

    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new, empty associative array.
     */
    @SuppressWarnings({ "unchecked" })
    public AssociativeArray() {
        // Creating new arrays is sometimes a PITN.
        this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
                DEFAULT_CAPACITY);
        this.size = 0;
    } // AssociativeArray()

    // +------------------+--------------------------------------------
    // | Standard Methods |
    // +------------------+

    /**
     * Create a copy of this AssociativeArray.
     */
    public AssociativeArray<K, V> clone() {
        AssociativeArray<K,V> result = new AssociativeArray<K,V>();
        
        for(int i = 0; i < this.pairs.length; i++){
            result.pairs[i] = this.pairs[i];
        }//for

        result.size = this.size;
        return result;
    } // clone()

    /**
     * Convert the array to a string.
     */
    public String toString() {
        String help = "";

        for(int i = 0; i < this.pairs.length; i++){
            if(this.pairs[i] != null){
                help += this.pairs[i].toString() + ", ";
            }//if
        }//for

        return "{" + help + "}";
    } // toString()

    // +----------------+----------------------------------------------
    // | Public Methods |
    // +----------------+

    /**
     * Set the value associated with key to value. Future calls to
     * get(key) will return value.
     */
    public void set(K key, V value) {

        // if there is already a key with a value
        if (this.hasKey(key)) {
            try {
                this.pairs[this.find(key)].value = value;
            } catch (Exception e) {
                new KeyNotFoundException();
            }
        } // if

        // check to see if there a null pair in the middle of the array, if so
        // set the new pair to that location
        else if (this.hasKey(null)) {
            for (int i = 0; i < this.size; i++) {
                if (this.hasKey(null)) {
                    this.pairs[i] = new KVPair<K, V>(key, value);
                } // if
            } // for
            this.size++;
        } // else if

        // if there is still space left in the array
        else if (this.size < this.pairs.length) {
            this.pairs[this.size] = new KVPair<K, V>(key, value);
            this.size++;
        } // else if

        // if there is no space left in the array
        else {
            this.expand();
            this.pairs[this.size] = new KVPair<K, V>(key, value);
            this.size++;
        } // else

    } // set(K,V)

    /**
     * Get the value associated with key.
     *
     * @throws KeyNotFoundException
     *                              when the key does not appear in the associative
     *                              array.
     */
    public V get(K key) throws KeyNotFoundException {

        if(this.hasKey(key)){
            for (int i = 0; i < this.pairs.length; i++){
                if(this.pairs[i].key.equals(key)){
                    return this.pairs[i].value;
                }//if
            }//for
        }//if

            throw new KeyNotFoundException();

    } // get(K)

    /**
     * Determine if key appears in the associative array.
     */
    public boolean hasKey(K key) {

        // Assuming that KVPair() does not equal null
        for (int i = 0; i < this.pairs.length; i++) {
            if ((this.pairs[i] != null) && (this.pairs[i].key.equals(key))) {
                return true;
            } // if
        } // for

        return false;
    } // hasKey(K)

    /**
     * Remove the key/value pair associated with a key. Future calls
     * to get(key) will throw an exception. If the key does not appear
     * in the associative array, does nothing.
     */
    public void remove(K key) {

        for (int i = 0; i < this.pairs.length; i++) {

            if (this.pairs[i] == null){
                continue;
            }//if
            else if (this.pairs[i].equals(key)){
                this.pairs[i] = null;
                this.size--;
            }//else if
        } // for
    } // remove(K)

    /**
     * Determine how many values are in the associative array.
     */
    public int size() {
        return this.size;
    } // size()

    // +-----------------+---------------------------------------------
    // | Private Methods |
    // +-----------------+

    /**
     * Expand the underlying array.
     */
    public void expand() {
        this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
    } // expand()

    /**
     * Find the index of the first entry in `pairs` that contains key.
     * If no such entry is found, throws an exception.
     */
    public int find(K key) throws KeyNotFoundException {

        for(int i = 0; i < this.pairs.length; i++){
            if (this.pairs[i].key == key){
                return i;
            }//if
        }//for

        throw new KeyNotFoundException();
    } // find(K)

} // class AssociativeArray
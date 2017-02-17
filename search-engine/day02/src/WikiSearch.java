import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;

public class WikiSearch {

    // map from URLs that contain the term(s) to relevance score
    private Map<String, Integer> map;

    public WikiSearch(Map<String, Integer> map) {
        this.map = map;
    }

    public Integer getRelevance(String url) {
        return map.get(url);
    }

    // Prints the contents in order of term frequency.
    private void print() {
        List<Entry<String, Integer>> entries = sort();
        for (Entry<String, Integer> entry: entries) {
            System.out.println(entry);
        }
    }

    // Computes the union of two search results.
    public WikiSearch or(WikiSearch that) {
        for (String url: that.map.keySet()) {
            if (this.map.containsKey(url)){
                this.map.put(url, this.map.get(url) + that.map.get(url));
            }
        }
        return this;
    }

    // Computes the intersection of two search results.
    public WikiSearch and(WikiSearch that) {
        Map<String, Integer> andMap = new HashMap<>();
        for (String url: that.map.keySet()) {
            if (this.map.containsKey(url)){
                andMap.put(url, this.map.get(url) + that.map.get(url));
            }
        }
        return new WikiSearch(andMap);
    }

    // Computes the difference of two search results.
    public WikiSearch minus(WikiSearch that) {
        for (String url: that.map.keySet()) {
            if (this.map.containsKey(url)){
                this.map.remove(url);
            }
        }
        return this;
    }

    // Computes the relevance of a search with multiple terms.
    protected int totalRelevance(Integer rel1, Integer rel2) {
        return rel1 + rel2;
    }

    // Sort the results by relevance.
    public List<Entry<String, Integer>> sort() {
//        List<Entry<String, Integer>> sortedMap = new ArrayList<>();
//        Collections.sort(sortedMap);
        //FROM BEGINNERSBOOK.COM
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        return null;
    }


    // Performs a search and makes a WikiSearch object.
    public static WikiSearch search(String term, Index index) {
        // TODO: Use the index to get a map from URL to count

        // Fix this
        Map<String, Integer> map = index.get(term);

        // Store the map locally in the WikiSearch
        return new WikiSearch(map);
    }

    // TODO: Choose an extension and add your methods here

    public static void main(String[] args) throws IOException {

        // make a Index
        Jedis jedis = JedisMaker.make();
        Index index = new Index(jedis); // You might need to change this, depending on how your constructor works.

        // search for the first term
        String term1 = "java";
        System.out.println("Query: " + term1);
        WikiSearch search1 = search(term1, index);
        search1.print();

        // search for the second term
        String term2 = "programming";
        System.out.println("Query: " + term2);
        WikiSearch search2 = search(term2, index);
        search2.print();

        // compute the intersection of the searches
        System.out.println("Query: " + term1 + " AND " + term2);
        WikiSearch intersection = search1.and(search2);
        intersection.print();
    }
}
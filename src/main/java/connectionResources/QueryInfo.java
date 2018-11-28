package connectionResources;

import java.util.Map;

public class QueryInfo {
    private String query;
    private Map<Integer, String> placeholders = null;

    public QueryInfo(String query, Map<Integer, String> placeholders) {
        this.query = query;
        this.placeholders = placeholders;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<Integer, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<Integer, String> placeholders) {
        this.placeholders = placeholders;
    }

    /*@Override
    public String toString(){
        for (Integer integer : placeholders.keySet()) {
            query = query.replaceFirst("\\?",placeholders.get(integer));
        }
        return query;
    }*/
}

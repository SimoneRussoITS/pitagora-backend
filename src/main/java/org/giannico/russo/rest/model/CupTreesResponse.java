package org.giannico.russo.rest.model;

import java.util.List;
import java.util.Map;

public class CupTreesResponse {
    private List<Map<String, Object>> cupTrees;

    public List<Map<String, Object>> getCupTrees() {
        return cupTrees;
    }

    public void setCupTrees(List<Map<String, Object>> cupTrees) {
        this.cupTrees = cupTrees;
    }
}

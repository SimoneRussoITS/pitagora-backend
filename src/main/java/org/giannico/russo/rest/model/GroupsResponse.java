package org.giannico.russo.rest.model;

import java.util.List;
import java.util.Map;

public class GroupsResponse {
    private List<Map<String, Object>> groups;

    public List<Map<String, Object>> getGroups() {
        return groups;
    }

    public void setGroups(List<Map<String, Object>> groups) {
        this.groups = groups;
    }
}

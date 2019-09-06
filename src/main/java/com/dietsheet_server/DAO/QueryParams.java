package com.dietsheet_server.DAO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QueryParams {
    private final int DEFAULT_MAX_RESULTS = 100;

    private  int firstResult = 0;
    private int maxResults = DEFAULT_MAX_RESULTS;
    private String nameLike = "";

    public QueryParams(Map<String, String> params) {
        if(params.containsKey("pageNum") && params.containsKey("pageSize")) {
            int pageNum = Integer.parseInt(params.get("pageNum"));
            int pageSize = Integer.parseInt(params.get("pageSize"));
            firstResult = Math.max(pageNum * pageSize - pageSize, 0);
            maxResults = Math.max(pageSize, 0);
        }
        nameLike = params.getOrDefault("nameLike", "");
    }
}

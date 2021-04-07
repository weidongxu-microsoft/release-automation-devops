package com.azure.dev.main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CheckRunListResult {

    @JsonProperty(value = "check_runs")
    private List<CheckRun> checkRuns;

    public List<CheckRun> getCheckRuns() {
        return checkRuns;
    }
}

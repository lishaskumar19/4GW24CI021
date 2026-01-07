package com.browsersimulator;

import java.util.List;
import java.util.Map;

public class BrowserState {
    private String currentURL;
    private List<String> backStack;
    private List<String> forwardStack;
    private Map<String, Integer> visitCount;
    private int totalVisits;
    private int backOperations;
    private int forwardOperations;
    
    public BrowserState(String currentURL, List<String> backStack, List<String> forwardStack, 
                       Map<String, Integer> visitCount, int totalVisits, int backOperations, int forwardOperations) {
        this.currentURL = currentURL;
        this.backStack = backStack;
        this.forwardStack = forwardStack;
        this.visitCount = visitCount;
        this.totalVisits = totalVisits;
        this.backOperations = backOperations;
        this.forwardOperations = forwardOperations;
    }
    
    // Getters
    public String getCurrentURL() { return currentURL; }
    public List<String> getBackStack() { return backStack; }
    public List<String> getForwardStack() { return forwardStack; }
    public Map<String, Integer> getVisitCount() { return visitCount; }
    public int getTotalVisits() { return totalVisits; }
    public int getBackOperations() { return backOperations; }
    public int getForwardOperations() { return forwardOperations; }
    
    // Setters
    public void setCurrentURL(String currentURL) { this.currentURL = currentURL; }
    public void setBackStack(List<String> backStack) { this.backStack = backStack; }
    public void setForwardStack(List<String> forwardStack) { this.forwardStack = forwardStack; }
    public void setVisitCount(Map<String, Integer> visitCount) { this.visitCount = visitCount; }
    public void setTotalVisits(int totalVisits) { this.totalVisits = totalVisits; }
    public void setBackOperations(int backOperations) { this.backOperations = backOperations; }
    public void setForwardOperations(int forwardOperations) { this.forwardOperations = forwardOperations; }
}
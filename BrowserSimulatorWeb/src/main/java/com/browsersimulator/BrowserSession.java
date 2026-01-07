package com.browsersimulator;

import java.util.*;

public class BrowserSession {
    private Stack<String> backStack = new Stack<>();
    private Stack<String> forwardStack = new Stack<>();
    private Map<String, Integer> visitCount = new HashMap<>();
    private String currentURL = "https://www.google.com";
    private int totalVisits = 1;
    private int backOperations = 0;
    private int forwardOperations = 0;
    
    public BrowserSession() {
        visitCount.put(currentURL, 1);
    }
    
    public BrowserState visitURL(String url) {
        if (url == null || url.trim().isEmpty()) {
            return getCurrentState();
        }
        
        String cleanUrl = url.trim();
        if (!cleanUrl.startsWith("http://") && !cleanUrl.startsWith("https://")) {
            cleanUrl = "https://" + cleanUrl;
        }
        
        // Push current URL to back stack
        if (!currentURL.isEmpty()) {
            backStack.push(currentURL);
        }
        
        // Set new URL as current
        currentURL = cleanUrl;
        
        // Update visit count
        visitCount.put(currentURL, visitCount.getOrDefault(currentURL, 0) + 1);
        totalVisits++;
        
        // Clear forward stack since we're navigating to a new page
        forwardStack.clear();
        
        return getCurrentState();
    }
    
    public BrowserState goBack() {
        if (!backStack.isEmpty()) {
            // Move current URL to forward stack
            forwardStack.push(currentURL);
            
            // Pop from back stack to current
            currentURL = backStack.pop();
            
            backOperations++;
        }
        
        return getCurrentState();
    }
    
    public BrowserState goForward() {
        if (!forwardStack.isEmpty()) {
            // Move current URL to back stack
            backStack.push(currentURL);
            
            // Pop from forward stack to current
            currentURL = forwardStack.pop();
            
            forwardOperations++;
        }
        
        return getCurrentState();
    }
    
    public BrowserState goHome() {
        // Reset everything to initial state
        backStack.clear();
        forwardStack.clear();
        visitCount.clear();
        currentURL = "https://www.google.com";
        visitCount.put(currentURL, 1);
        totalVisits = 1;
        backOperations = 0;
        forwardOperations = 0;
        
        return getCurrentState();
    }
    
    public BrowserState getCurrentState() {
        return new BrowserState(
            currentURL,
            new ArrayList<>(backStack),
            new ArrayList<>(forwardStack),
            new HashMap<>(visitCount),
            totalVisits,
            backOperations,
            forwardOperations
        );
    }
}
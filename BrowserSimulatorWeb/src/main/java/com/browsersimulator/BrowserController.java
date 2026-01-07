package com.browsersimulator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class BrowserController {
    
    // In-memory storage for session (in a real app, you'd use proper session management)
    private Map<String, BrowserSession> sessions = new ConcurrentHashMap<>();
    
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
    
    @PostMapping("/visit")
    @ResponseBody
    @CrossOrigin
    public BrowserState visit(@RequestParam String url, @RequestParam String sessionId) {
        BrowserSession session = getSession(sessionId);
        return session.visitURL(url);
    }
    
    @PostMapping("/back")
    @ResponseBody
    @CrossOrigin
    public BrowserState goBack(@RequestParam String sessionId) {
        BrowserSession session = getSession(sessionId);
        return session.goBack();
    }
    
    @PostMapping("/forward")
    @ResponseBody
    @CrossOrigin
    public BrowserState goForward(@RequestParam String sessionId) {
        BrowserSession session = getSession(sessionId);
        return session.goForward();
    }
    
    @PostMapping("/home")
    @ResponseBody
    @CrossOrigin
    public BrowserState goHome(@RequestParam String sessionId) {
        BrowserSession session = getSession(sessionId);
        return session.goHome();
    }
    
    @PostMapping("/get-state")
    @ResponseBody
    @CrossOrigin
    public BrowserState getState(@RequestParam String sessionId) {
        BrowserSession session = getSession(sessionId);
        return session.getCurrentState();
    }
    
    private BrowserSession getSession(String sessionId) {
        return sessions.computeIfAbsent(sessionId, k -> new BrowserSession());
    }
}
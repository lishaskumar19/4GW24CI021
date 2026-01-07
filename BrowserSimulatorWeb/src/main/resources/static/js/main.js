// Browser Simulator Frontend JavaScript
class BrowserSimulator {
    constructor() {
        this.sessionId = this.generateSessionId();
        this.initElements();
        this.bindEvents();
        this.loadInitialState();
        this.updateStats();
    }

    generateSessionId() {
        return 'session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
    }

    initElements() {
        this.backBtn = document.getElementById('backBtn');
        this.forwardBtn = document.getElementById('forwardBtn');
        this.homeBtn = document.getElementById('homeBtn');
        this.visitBtn = document.getElementById('visitBtn');
        this.urlInput = document.getElementById('urlInput');
        this.browserDisplay = document.getElementById('browserDisplay');
        this.currentTitle = document.getElementById('currentTitle');
        this.totalVisits = document.getElementById('totalVisits');
        this.backCount = document.getElementById('backCount');
        this.forwardCount = document.getElementById('forwardCount');
        this.backStack = document.getElementById('backStack');
        this.forwardStack = document.getElementById('forwardStack');
        this.visitCounts = document.getElementById('visitCounts');
    }

    bindEvents() {
        this.backBtn.addEventListener('click', () => this.goBack());
        this.forwardBtn.addEventListener('click', () => this.goForward());
        this.homeBtn.addEventListener('click', () => this.goHome());
        this.visitBtn.addEventListener('click', () => this.visitCurrentURL());
        
        this.urlInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.visitCurrentURL();
            }
        });
    }

    async loadInitialState() {
        try {
            const response = await fetch('/get-state', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `sessionId=${this.sessionId}`
            });
            
            const state = await response.json();
            this.updateUI(state);
        } catch (error) {
            console.error('Error loading initial state:', error);
        }
    }

    async visitCurrentURL() {
        const url = this.urlInput.value.trim();
        if (!url) return;

        this.setButtonLoading(this.visitBtn, true);
        
        try {
            const response = await fetch('/visit', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `url=${encodeURIComponent(url)}&sessionId=${this.sessionId}`
            });
            
            const state = await response.json();
            this.updateUI(state);
            this.urlInput.value = '';
        } catch (error) {
            console.error('Error visiting URL:', error);
            alert('Error visiting URL. Please try again.');
        } finally {
            this.setButtonLoading(this.visitBtn, false);
        }
    }

    async goBack() {
        this.setButtonLoading(this.backBtn, true);
        
        try {
            const response = await fetch('/back', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `sessionId=${this.sessionId}`
            });
            
            const state = await response.json();
            this.updateUI(state);
        } catch (error) {
            console.error('Error going back:', error);
            alert('Error going back. Please try again.');
        } finally {
            this.setButtonLoading(this.backBtn, false);
        }
    }

    async goForward() {
        this.setButtonLoading(this.forwardBtn, true);
        
        try {
            const response = await fetch('/forward', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `sessionId=${this.sessionId}`
            });
            
            const state = await response.json();
            this.updateUI(state);
        } catch (error) {
            console.error('Error going forward:', error);
            alert('Error going forward. Please try again.');
        } finally {
            this.setButtonLoading(this.forwardBtn, false);
        }
    }

    async goHome() {
        this.setButtonLoading(this.homeBtn, true);
        
        try {
            const response = await fetch('/home', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `sessionId=${this.sessionId}`
            });
            
            const state = await response.json();
            this.updateUI(state);
        } catch (error) {
            console.error('Error going home:', error);
            alert('Error going home. Please try again.');
        } finally {
            this.setButtonLoading(this.homeBtn, false);
        }
    }

    updateUI(state) {
        // Update current URL display
        this.currentTitle.textContent = this.extractDomain(state.currentURL);
        document.getElementById('currentTitle').textContent = this.extractDomain(state.currentURL);
        
        // Update stats
        this.totalVisits.textContent = state.totalVisits;
        this.backCount.textContent = state.backOperations;
        this.forwardCount.textContent = state.forwardOperations;
        
        // Update back stack
        this.backStack.innerHTML = '';
        state.backStack.slice().reverse().forEach(url => {
            const item = document.createElement('div');
            item.className = 'history-item';
            item.innerHTML = `
                <span>${this.truncateUrl(url)}</span>
                <small>${this.extractDomain(url)}</small>
            `;
            this.backStack.appendChild(item);
        });
        
        // Update forward stack
        this.forwardStack.innerHTML = '';
        state.forwardStack.forEach(url => {
            const item = document.createElement('div');
            item.className = 'history-item';
            item.innerHTML = `
                <span>${this.truncateUrl(url)}</span>
                <small>${this.extractDomain(url)}</small>
            `;
            this.forwardStack.appendChild(item);
        });
        
        // Update visit counts
        this.visitCounts.innerHTML = '';
        Object.entries(state.visitCount).forEach(([url, count]) => {
            const item = document.createElement('div');
            item.className = 'history-item visit-count';
            item.innerHTML = `
                <span>${this.truncateUrl(url)}</span>
                <span>Visited: ${count}</span>
            `;
            this.visitCounts.appendChild(item);
        });
        
        // Update button states
        this.backBtn.disabled = state.backStack.length === 0;
        this.forwardBtn.disabled = state.forwardStack.length === 0;
    }

    extractDomain(url) {
        try {
            const urlObj = new URL(url);
            return urlObj.hostname;
        } catch {
            return url.substring(0, 30) + (url.length > 30 ? '...' : '');
        }
    }

    truncateUrl(url) {
        return url.length > 40 ? url.substring(0, 40) + '...' : url;
    }

    setButtonLoading(button, loading) {
        if (loading) {
            button.classList.add('btn-loading');
            button.disabled = true;
        } else {
            button.classList.remove('btn-loading');
            button.disabled = false;
        }
    }
}

// Initialize the browser simulator when the page loads
document.addEventListener('DOMContentLoaded', () => {
    new BrowserSimulator();
});

// Additional utility functions for enhanced user experience
document.addEventListener('DOMContentLoaded', function() {
    // Add ripple effect to buttons
    const buttons = document.querySelectorAll('.nav-btn, .visit-btn');
    buttons.forEach(button => {
        button.addEventListener('click', function(e) {
            // Create ripple element
            const ripple = document.createElement('span');
            ripple.classList.add('ripple');
            
            // Position ripple
            const rect = button.getBoundingClientRect();
            const size = Math.max(rect.width, rect.height);
            const x = e.clientX - rect.left - size / 2;
            const y = e.clientY - rect.top - size / 2;
            
            ripple.style.width = ripple.style.height = size + 'px';
            ripple.style.left = x + 'px';
            ripple.style.top = y + 'px';
            
            // Add and remove ripple
            this.appendChild(ripple);
            setTimeout(() => {
                ripple.remove();
            }, 600);
        });
    });
});
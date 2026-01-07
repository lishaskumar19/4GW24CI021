import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.net.URI;

public class BrowserSimulatorDSA {

    JFrame frame;
    JTextField urlField;
    JTextArea currentArea, backArea, forwardArea, countArea;
    JLabel visitLabel, backLabel, forwardLabel;

    Stack<String> backStack = new Stack<>();
    Stack<String> forwardStack = new Stack<>();
    Map<String, Integer> visitCount = new HashMap<>();

    String currentURL = "https://google.com";
    int visits = 1, backOps = 0, forwardOps = 0;

    public BrowserSimulatorDSA() {
        visitCount.put(currentURL, 1);
        setupUI();
        updateDisplay();
    }

    void setupUI() {
        frame = new JFrame("DSA Lab: Browser History Simulator");
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("DSA Lab: Browser History (Stack Implementation)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(Color.BLUE);
        title.setForeground(Color.WHITE);
        frame.add(title, BorderLayout.NORTH);

        JPanel stats = new JPanel();
        visitLabel = new JLabel("Total Visits: 1");
        backLabel = new JLabel("Back: 0");
        forwardLabel = new JLabel("Forward: 0");
        stats.add(visitLabel);
        stats.add(backLabel);
        stats.add(forwardLabel);
        frame.add(stats, BorderLayout.SOUTH);

        JPanel center = new JPanel(new GridLayout(2, 2, 10, 10));

        currentArea = createBox("Current Page");
        countArea = createBox("Visit Counts");
        backArea = createBox("Back Stack");
        forwardArea = createBox("Forward Stack");

        center.add(new JScrollPane(currentArea));
        center.add(new JScrollPane(countArea));
        center.add(new JScrollPane(backArea));
        center.add(new JScrollPane(forwardArea));

        frame.add(center, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        urlField = new JTextField(25);
        JButton visit = new JButton("Visit");
        JButton back = new JButton("Back");
        JButton forward = new JButton("Forward");
        JButton home = new JButton("Home");
        JButton report = new JButton("Report");

        visit.addActionListener(e -> visitURL());
        back.addActionListener(e -> goBack());
        forward.addActionListener(e -> goForward());
        home.addActionListener(e -> goHome());
        report.addActionListener(e -> generateReport());

        controls.add(new JLabel("URL:"));
        controls.add(urlField);
        controls.add(visit);
        controls.add(back);
        controls.add(forward);
        controls.add(home);
        controls.add(report);

        frame.add(controls, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    JTextArea createBox(String title) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));
        return area;
    }

    void visitURL() {
        String url = urlField.getText().trim();
        if (url.isEmpty()) return;

        if (!url.startsWith("http"))
            url = "https://" + url;

        backStack.push(currentURL);
        currentURL = url;

        visitCount.put(url, visitCount.getOrDefault(url, 0) + 1);
        visits++;
        visitLabel.setText("Total Visits: " + visits);
        forwardStack.clear();

        updateDisplay();
        openBrowser(url);
    }

    void goBack() {
        if (!backStack.isEmpty()) {
            forwardStack.push(currentURL);
            currentURL = backStack.pop();
            backOps++;
            backLabel.setText("Back: " + backOps);
            updateDisplay();
        }
    }

    void goForward() {
        if (!forwardStack.isEmpty()) {
            backStack.push(currentURL);
            currentURL = forwardStack.pop();
            forwardOps++;
            forwardLabel.setText("Forward: " + forwardOps);
            updateDisplay();
        }
    }

    void goHome() {
        backStack.clear();
        forwardStack.clear();
        visitCount.clear();
        currentURL = "https://google.com";
        visitCount.put(currentURL, 1);
        visits = backOps = forwardOps = 0;
        updateDisplay();
    }

    void updateDisplay() {
        currentArea.setText(currentURL);

        countArea.setText("");
        visitCount.forEach((k, v) -> countArea.append(k + " → " + v + "\n"));

        backArea.setText(backStack.toString());
        forwardArea.setText(forwardStack.toString());
    }

    void openBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception ignored) {}
    }

    void generateReport() {
        try {
            FileWriter fw = new FileWriter("DSA_Report.html");
            fw.write("<h1>Browser History Report</h1>");
            fw.write("<p>Current Page: " + currentURL + "</p>");
            fw.write("<p>Total Visits: " + visits + "</p>");
            fw.close();
            Desktop.getDesktop().browse(new java.io.File("DSA_Report.html").toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error creating report");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BrowserSimulatorDSA::new);
    }
}

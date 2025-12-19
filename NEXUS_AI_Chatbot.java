import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;

public class NEXUS_AI_Chatbot {

    JFrame frame;
    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton;

    LinkedHashMap<String, String> faq = new LinkedHashMap<>();

    // 🎨 COLORS
    Color bgColor = new Color(246, 245, 255);
    Color titleColor = new Color(63, 81, 181);
    Color subtitleColor = new Color(120, 120, 120);
    Color chatTextColor = new Color(40, 40, 40);
    Color buttonColor = new Color(63, 81, 181);

    public NEXUS_AI_Chatbot() {

        frame = new JFrame("NEXUS – AI Chatbot");
        frame.setSize(740, 580);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(bgColor);

        // 🌟 HEADER
        JLabel title = new JLabel("Hi, I am NEXUS", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(titleColor);
        title.setBorder(new EmptyBorder(25, 0, 5, 0));

        JLabel subtitle = new JLabel("Your intelligent AI assistant", JLabel.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(subtitleColor);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(bgColor);
        header.add(title);
        header.add(subtitle);

        // 💬 CHAT AREA
        chatArea = new JTextArea();
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(bgColor);
        chatArea.setForeground(chatTextColor);
        chatArea.setBorder(new EmptyBorder(20, 80, 20, 80));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(bgColor);

        // ✍ INPUT FIELD
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        inputField.setBackground(Color.WHITE);
        inputField.setBorder(new EmptyBorder(12, 15, 12, 15));

        // 🚀 SEND BUTTON
        sendButton = new JButton("SEND");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBackground(buttonColor);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel inputPanel = new JPanel(new BorderLayout(15, 10));
        inputPanel.setBorder(new EmptyBorder(20, 90, 30, 90));
        inputPanel.setBackground(bgColor);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(header, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        trainBot();

        sendButton.addActionListener(e -> respond());
        inputField.addActionListener(e -> respond());

        frame.setVisible(true);
    }

    // 🧠 TRAINING
    void trainBot() {
        faq.put("what is machine learning",
                "Machine Learning is a subset of AI that allows systems to learn from data.");

        faq.put("what is artificial intelligence",
                "Artificial Intelligence enables machines to think and act like humans.");

        faq.put("what is ai",
                "Artificial Intelligence enables machines to think and act like humans.");

        faq.put("who are you",
                "I am NEXUS, a Java-based AI Chatbot.");

        faq.put("what can you do",
                "I can answer AI-related questions using trained knowledge.");

        faq.put("hello", "Hello! How can I help you today?");
        faq.put("hi", "Hi there! 😊");
        faq.put("bye", "Goodbye! Have a lovely day 🌸");
    }

    // 🧠 NLP + RESPONSE
    void respond() {
        String userText = inputField.getText().trim();
        if (userText.isEmpty()) return;

        chatArea.append("You: " + userText + "\n\n");

        String processed = userText.toLowerCase();
        String response = "I'm still learning. Please ask something else.";

        for (String key : faq.keySet()) {
            if (processed.contains(key)) {
                response = faq.get(key);
                break;
            }
        }

        chatArea.append("NEXUS: " + response + "\n\n");
        inputField.setText("");
    }

    public static void main(String[] args) {
        new NEXUS_AI_Chatbot();
    }
}

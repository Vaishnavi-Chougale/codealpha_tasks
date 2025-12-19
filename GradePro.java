import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GradePro extends JFrame {

    private final Color COLOR_BG = new Color(18, 22, 31);
    private final Color COLOR_SIDEBAR = new Color(28, 35, 49);
    private final Color COLOR_ACCENT = new Color(0, 153, 255);
    private final Color COLOR_CARD = new Color(38, 46, 62);
    private final Color COLOR_TEXT = new Color(230, 230, 230);

    private DefaultTableModel model;
    private JLabel lblAvg, lblHigh, lblLow, lblCount;
    private JTextField nameInput;
    private JTextField[] subjectFields = new JTextField[5];
    private String[] subNames = {"Math", "Science", "English", "History", "CS"};

    public GradePro() {
        setTitle("GradePro - Student Performance Analytics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setBackground(COLOR_BG);
        mainContent.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainContent.add(createHeaderCards(), BorderLayout.NORTH);
        mainContent.add(createTableSection(), BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel logo = new JLabel("GradePro Analytics");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(COLOR_ACCENT);
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 30, 0);
        sidebar.add(logo, gbc);

        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 5, 0);
        sidebar.add(createLabel("Student Name:"), gbc);
        nameInput = createShortField();
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 20, 0);
        sidebar.add(nameInput, gbc);

        nameInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) subjectFields[0].requestFocus();
            }
        });

        gbc.gridy = 3; gbc.insets = new Insets(10, 0, 10, 0);
        sidebar.add(createLabel("Subject Grades (Max 100):"), gbc);

        for (int i = 0; i < 5; i++) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setOpaque(false);
            JLabel sLabel = new JLabel(subNames[i] + ":");
            sLabel.setForeground(Color.GRAY);
            sLabel.setPreferredSize(new Dimension(60, 30));
            
            subjectFields[i] = createShortField();
            final int index = i;
            
            subjectFields[i].addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (index < 4) {
                            subjectFields[index + 1].requestFocus();
                        } else {
                            processStudentData();
                        }
                    }
                }
            });

            row.add(sLabel, BorderLayout.WEST);
            row.add(subjectFields[i], BorderLayout.CENTER);
            gbc.gridy = 4 + i; gbc.insets = new Insets(2, 0, 2, 0);
            sidebar.add(row, gbc);
        }

        JButton addBtn = new JButton("Calculate Mean");
        styleButton(addBtn);
        gbc.gridy = 10; gbc.insets = new Insets(30, 0, 0, 0);
        sidebar.add(addBtn, gbc);
        addBtn.addActionListener(e -> processStudentData());

        return sidebar;
    }

    private JPanel createTableSection() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        // Column "DATA TYPE" has been removed here
        String[] cols = {"STUDENT NAME", "MEAN SCORE", "REMARKS"};
        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getTableHeader().setDefaultRenderer(centerRenderer);
        
        table.setBackground(COLOR_CARD);
        table.setForeground(COLOR_TEXT);
        table.setRowHeight(45);
        table.getTableHeader().setBackground(COLOR_SIDEBAR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(COLOR_BG);
        p.add(scroll);
        return p;
    }

    private void processStudentData() {
        try {
            String name = nameInput.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a student name.");
                return;
            }

            double sum = 0;
            for (int i = 0; i < 5; i++) {
                double val = Double.parseDouble(subjectFields[i].getText().trim());
                
                // VALIDATION: Check if mark is above 100
                if (val < 0 || val > 100) {
                    JOptionPane.showMessageDialog(this, "Error: " + subNames[i] + " score must be between 0 and 100!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    subjectFields[i].requestFocus();
                    return;
                }
                sum += val;
            }
            
            double mean = sum / 5.0;
            String remark = mean >= 50 ? "PASS" : "FAIL";

            // Row added without the Data Type column
            model.addRow(new Object[]{name.toUpperCase(), String.format("%.2f", mean), remark});
            updateStats();
            
            nameInput.setText("");
            for (JTextField f : subjectFields) f.setText("");
            nameInput.requestFocus();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric scores for all subjects.");
        }
    }

    private JTextField createShortField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 32));
        field.setBackground(new Color(20, 25, 35));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 90)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(COLOR_TEXT);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return l;
    }

    private void styleButton(JButton btn) {
        btn.setPreferredSize(new Dimension(0, 45));
        btn.setBackground(COLOR_ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createHeaderCards() {
        JPanel wrapper = new JPanel(new GridLayout(1, 4, 15, 0));
        wrapper.setOpaque(false);
        lblCount = addStatCard(wrapper, "STUDENTS", "0", COLOR_TEXT);
        lblAvg = addStatCard(wrapper, "CLASS MEAN", "0.0", Color.GREEN);
        lblHigh = addStatCard(wrapper, "TOP MEAN", "0", COLOR_ACCENT);
        lblLow = addStatCard(wrapper, "LOW MEAN", "0", Color.RED);
        return wrapper;
    }

    private JLabel addStatCard(JPanel parent, String title, String val, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COLOR_CARD);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel t = new JLabel(title); t.setForeground(Color.GRAY);
        JLabel v = new JLabel(val); v.setFont(new Font("Segoe UI", Font.BOLD, 24)); v.setForeground(color);
        card.add(t, BorderLayout.NORTH); card.add(v, BorderLayout.SOUTH);
        parent.add(card);
        return v;
    }

    private void updateStats() {
        double totalMeans = 0, max = -1, min = 101;
        int rows = model.getRowCount();
        for(int i=0; i<rows; i++) {
            double m = Double.parseDouble(model.getValueAt(i, 1).toString());
            totalMeans += m;
            if(m > max) max = m;
            if(m < min) min = m;
        }
        lblCount.setText(String.valueOf(rows));
        lblAvg.setText(String.format("%.1f", totalMeans/rows));
        lblHigh.setText(String.format("%.1f", max));
        lblLow.setText(String.format("%.1f", min));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GradePro::new);
    }
}
package lab3;

import Fuzzy.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.Map;


//Adder Substractor interface
public class FuzzyCalculator extends JFrame implements ActionListener {

    private final JTextField x1Field, x2Field,w1Field, w2Field, resultX3, resultX4;
    private final TwoXTwoTable fuzzyTable;

    public static TwoXTwoTable createAdderSubstractor() {
        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> RuleTableAdder = new EnumMap<>(FuzzyValue.class);

        Map<FuzzyValue, FuzzyValue> nlLine = new EnumMap<>(FuzzyValue.class);
        RuleTableAdder.put(FuzzyValue.NL, nlLine);
        nlLine.put(FuzzyValue.NL, FuzzyValue.NL);
        nlLine.put(FuzzyValue.NM, FuzzyValue.NL);
        nlLine.put(FuzzyValue.ZR, FuzzyValue.NL);
        nlLine.put(FuzzyValue.PM, FuzzyValue.NM);
        nlLine.put(FuzzyValue.PL, FuzzyValue.ZR);

        Map<FuzzyValue, FuzzyValue> nmLine = new EnumMap<>(FuzzyValue.class);
        RuleTableAdder.put(FuzzyValue.NM, nmLine);
        nmLine.put(FuzzyValue.NL, FuzzyValue.NL);
        nmLine.put(FuzzyValue.NM, FuzzyValue.NL);
        nmLine.put(FuzzyValue.ZR, FuzzyValue.NM);
        nmLine.put(FuzzyValue.PM, FuzzyValue.ZR);
        nmLine.put(FuzzyValue.PL, FuzzyValue.PM);

        Map<FuzzyValue, FuzzyValue> zrLine = new EnumMap<>(FuzzyValue.class);
        RuleTableAdder.put(FuzzyValue.ZR, zrLine);
        zrLine.put(FuzzyValue.NL, FuzzyValue.NL);
        zrLine.put(FuzzyValue.NM, FuzzyValue.NM);
        zrLine.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine.put(FuzzyValue.PM, FuzzyValue.PM);
        zrLine.put(FuzzyValue.PL, FuzzyValue.PL);

        Map<FuzzyValue, FuzzyValue> pmLine = new EnumMap<>(FuzzyValue.class);
        RuleTableAdder.put(FuzzyValue.PM, pmLine);
        pmLine.put(FuzzyValue.NL, FuzzyValue.NM);
        pmLine.put(FuzzyValue.NM, FuzzyValue.ZR);
        pmLine.put(FuzzyValue.ZR, FuzzyValue.PM);
        pmLine.put(FuzzyValue.PM, FuzzyValue.PL);
        pmLine.put(FuzzyValue.PL, FuzzyValue.PL);

        Map<FuzzyValue, FuzzyValue> plLine = new EnumMap<>(FuzzyValue.class);
        RuleTableAdder.put(FuzzyValue.PL, plLine);
        plLine.put(FuzzyValue.NL, FuzzyValue.ZR);
        plLine.put(FuzzyValue.NM, FuzzyValue.PM);
        plLine.put(FuzzyValue.ZR, FuzzyValue.PL);
        plLine.put(FuzzyValue.PM, FuzzyValue.PL);
        plLine.put(FuzzyValue.PL, FuzzyValue.PL);

        Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> RuleTableSubstractor = new EnumMap<>(FuzzyValue.class);
        Map<FuzzyValue, FuzzyValue> nlLine1 = new EnumMap<>(FuzzyValue.class);
        RuleTableSubstractor.put(FuzzyValue.NL, nlLine1);
        nlLine1.put(FuzzyValue.NL, FuzzyValue.ZR);
        nlLine1.put(FuzzyValue.NM, FuzzyValue.NM);
        nlLine1.put(FuzzyValue.ZR, FuzzyValue.NL);
        nlLine1.put(FuzzyValue.PM, FuzzyValue.NL);
        nlLine1.put(FuzzyValue.PL, FuzzyValue.NL);

        Map<FuzzyValue, FuzzyValue> nmLine1 = new EnumMap<>(FuzzyValue.class);
        RuleTableSubstractor.put(FuzzyValue.NM, nmLine1);
        nmLine1.put(FuzzyValue.NL, FuzzyValue.PM);
        nmLine1.put(FuzzyValue.NM, FuzzyValue.ZR);
        nmLine1.put(FuzzyValue.ZR, FuzzyValue.NM);
        nmLine1.put(FuzzyValue.PM, FuzzyValue.NL);
        nmLine1.put(FuzzyValue.PL, FuzzyValue.NL);

        Map<FuzzyValue, FuzzyValue> zrLine1 = new EnumMap<>(FuzzyValue.class);
        RuleTableSubstractor.put(FuzzyValue.ZR, zrLine1);
        zrLine1.put(FuzzyValue.NL, FuzzyValue.PL);
        zrLine1.put(FuzzyValue.NM, FuzzyValue.PM);
        zrLine1.put(FuzzyValue.ZR, FuzzyValue.ZR);
        zrLine1.put(FuzzyValue.PM, FuzzyValue.NM);
        zrLine1.put(FuzzyValue.PL, FuzzyValue.NL);

        Map<FuzzyValue, FuzzyValue> pmLine1 = new EnumMap<>(FuzzyValue.class);
        RuleTableSubstractor.put(FuzzyValue.PM, pmLine1);
        pmLine1.put(FuzzyValue.NL, FuzzyValue.PL);
        pmLine1.put(FuzzyValue.NM, FuzzyValue.PL);
        pmLine1.put(FuzzyValue.ZR, FuzzyValue.PM);
        pmLine1.put(FuzzyValue.PM, FuzzyValue.ZR);
        pmLine1.put(FuzzyValue.PL, FuzzyValue.NM);

        Map<FuzzyValue, FuzzyValue> plLine1 = new EnumMap<>(FuzzyValue.class);
        RuleTableSubstractor.put(FuzzyValue.PL, plLine1);
        plLine1.put(FuzzyValue.NL, FuzzyValue.PL);
        plLine1.put(FuzzyValue.NM, FuzzyValue.PL);
        plLine1.put(FuzzyValue.ZR, FuzzyValue.PL);
        plLine1.put(FuzzyValue.PM, FuzzyValue.PM);
        plLine1.put(FuzzyValue.PL, FuzzyValue.ZR);

        return new TwoXTwoTable(RuleTableAdder, RuleTableSubstractor);
    }

    public FuzzyCalculator() {

        fuzzyTable = createAdderSubstractor();

        // Create UI components
        x1Field = new JTextField(10);
        x2Field = new JTextField(10);
        w1Field = new JTextField(10);
        w2Field = new JTextField(10);
        resultX3 = new JTextField(10);
        resultX4 = new JTextField(10);
        resultX3.setEditable(false);
        resultX4.setEditable(false);

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(this);

        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(105, 40, 22));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some spacing

        // Make labels bold using the default font
        Font boldFont = new Font(Font.DIALOG, Font.BOLD, 12);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel x1Label = new JLabel("x1:");
        x1Label.setFont(boldFont);
        add(x1Label, gbc);

        gbc.gridx = 1;
        x1Field.setFont(boldFont);
        add(x1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel x2Label = new JLabel("x2:");
        x2Label.setFont(boldFont);
        add(x2Label, gbc);

        gbc.gridx = 1;
        x2Field.setFont(boldFont);
        add(x2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel w1Label = new JLabel("w1:");
        w1Label.setFont(boldFont);
        add(w1Label, gbc);

        gbc.gridx = 1;
        w1Field.setFont(boldFont);
        add(w1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel w2Label = new JLabel("w2:");
        w2Label.setFont(boldFont);
        add(w2Label, gbc);

        gbc.gridx = 1;
        w2Field.setFont(boldFont);
        add(w2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span two columns
        executeButton.setBackground(new Color(19, 88, 101));
        executeButton.setForeground(Color.WHITE);
        add(executeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1; // Reset grid width
        JLabel resultX3Label = new JLabel("Result x3:");
        resultX3Label.setFont(boldFont);
        add(resultX3Label, gbc);

        gbc.gridx = 1;
        resultX3.setFont(boldFont);
        add(resultX3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel resultX4Label = new JLabel("Result x4:");
        resultX4Label.setFont(boldFont);
        add(resultX4Label, gbc);

        gbc.gridx = 1;
        resultX4.setFont(boldFont);
        add(resultX4, gbc);

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setLookAndFeel();
        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double w1 = Double.parseDouble(w1Field.getText());
            double w2 = Double.parseDouble(w2Field.getText());

            FuzzyDefuzz fuzDefuz = new FuzzyDefuzz(-1.0, -0.5, 0.0, 0.5, 1.0);

            FuzzyToken inpToken1 = fuzDefuz.fuzzyfie(x1 * w1);
            FuzzyToken inpToken2 = fuzDefuz.fuzzyfie(x2 * w2);

            FuzzyToken[] out = fuzzyTable.execute(inpToken1, inpToken2);

            double defuzzifiedX3 = fuzDefuz.defuzzify(out[0]);
            double defuzzifiedX4 = fuzDefuz.defuzzify(out[1]);

            resultX3.setText(String.format("%.2f", defuzzifiedX3));
            resultX4.setText(String.format("%.2f", defuzzifiedX4));


        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FuzzyCalculator::new);
    }
}
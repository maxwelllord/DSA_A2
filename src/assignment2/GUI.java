/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author hayae
 */
public class GUI extends JFrame {
    
    public GUI() {
        setTitle("InventorySystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        // Create components
        JLabel label = new JLabel("I love inventories!");
        JButton button = new JButton("Click Me");
        JTextField textField = new JTextField(20);

        // Create a panel and add components
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button);
        panel.add(textField);

        // Add the panel to the frame
        add(panel);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
        
        Database db = new Database();
        
        System.out.println(db.getProducts());
    }
    
}


import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

public class ComboboxCustom {

    public ComboboxCustom() {
        JComboBox<Integer> comboBox = new JComboBox<Integer>();
        for (int i = 1; i <= 20; i++) {
            comboBox.addItem(i);
        }

        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        scroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                            @Override
                            protected JButton createDecreaseButton(int orientation) {
                                return createZeroButton();
                            }

                            @Override
                            protected JButton createIncreaseButton(int orientation) {
                                return createZeroButton();
                            }

                            @Override
                            public Dimension getPreferredSize(JComponent c) {
                                return new Dimension(4, super.getPreferredSize(c).height);
                            }

                            private JButton createZeroButton() {
                                return new JButton() {
                                    @Override
                                    public Dimension getMinimumSize() {
                                        return new Dimension(new Dimension(0, 0));
                                    }

                                    @Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(new Dimension(0, 0));
                                    }

                                    @Override
                                    public Dimension getMaximumSize() {
                                        return new Dimension(new Dimension(0, 0));
                                    }
                                };
                            }
                        });
                        return scroller;
                    }
                };
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.add(comboBox);

        JFrame frame = new JFrame("Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ComboboxCustom();
            }
        });
    }

}

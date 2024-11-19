/*Advanced Study Timer*/

package advancedstudytimer;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudyTimerGUI gui = new StudyTimerGUI();
            gui.setVisible(true);
        });
    }
}

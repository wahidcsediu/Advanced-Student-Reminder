package advancedstudytimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class StudyTimerGUI extends JFrame {
    private TaskManager taskManager;
    private PomodoroTimer pomodoroTimer;

    private DefaultListModel<TaskManager.Task> taskListModel;
    private JList<TaskManager.Task> taskList;
    private JTextField taskInputField;
    private JTextField timeInputField;
    private JButton addTaskButton;
    private JButton startTimerButton;
    private JLabel timerLabel;

    private final String[] motivationalQuotes = {
            "Great job! Keep pushing forward.",
            "Take a short break, you deserve it!",
            "Success comes one step at a time.",
            "You're doing amazing!",
            "Believe in yourself and all that you are.",
            "Every step you take gets you closer to your goal."
    };

    public StudyTimerGUI() {
        taskManager = new TaskManager();
        pomodoroTimer = new PomodoroTimer();

        // Frame setup
        setTitle("Advanced Study Timer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(224, 255, 255));
        setLayout(new BorderLayout());

        // Top Panel: Task Input
        JPanel taskInputPanel = new JPanel(new GridLayout(2, 1));
        taskInputPanel.setBackground(new Color(255, 228, 225));

        JPanel inputRow = new JPanel(new BorderLayout());
        inputRow.setBackground(new Color(255, 240, 245));

        taskInputField = new JTextField();
        timeInputField = new JTextField("00:00:00", 8); // Default time input updated
        addTaskButton = new JButton("Add Task");
        addTaskButton.setBackground(new Color(144, 238, 144));

        inputRow.add(new JLabel("Task: "), BorderLayout.WEST);
        inputRow.add(taskInputField, BorderLayout.CENTER);
        inputRow.add(new JLabel(" Time (HH:MM:SS): "), BorderLayout.EAST);
        inputRow.add(timeInputField, BorderLayout.SOUTH);

        taskInputPanel.add(inputRow);
        taskInputPanel.add(addTaskButton);

        // Center Panel: Task List
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane taskListScrollPane = new JScrollPane(taskList);

        // Add Keyboard Listener for Deleting Tasks
        taskList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    ArrayList<TaskManager.Task> selectedTasks = new ArrayList<>(taskList.getSelectedValuesList());
                    taskManager.removeTasks(selectedTasks);
                    selectedTasks.forEach(taskListModel::removeElement);
                }
            }
        });

        // Bottom Panel: Timer Controls
        JPanel timerPanel = new JPanel(new GridLayout(2, 1));
        timerPanel.setBackground(new Color(240, 255, 240));

        startTimerButton = new JButton("Start Timer");
        startTimerButton.setBackground(new Color(173, 216, 230));
        timerLabel = new JLabel("Time Remaining: --:--:--", JLabel.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 18));

        timerPanel.add(timerLabel);
        timerPanel.add(startTimerButton);

        // Add Panels to Frame
        add(taskInputPanel, BorderLayout.NORTH);
        add(taskListScrollPane, BorderLayout.CENTER);
        add(timerPanel, BorderLayout.SOUTH);

        // Action Listeners
        addTaskButton.addActionListener((ActionEvent e) -> {
            String taskName = taskInputField.getText().trim();
            String time = timeInputField.getText().trim();
            int durationSeconds = parseTimeToSeconds(time);

            if (!taskName.isEmpty() && durationSeconds > 0) {
                TaskManager.Task task = new TaskManager.Task(taskName, durationSeconds);
                taskManager.addTask(taskName, durationSeconds);
                taskListModel.addElement(task); // Add task to list
                taskInputField.setText(""); // Clear task input after adding
                timeInputField.setText("00:00:00"); // Reset time input
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid task name and time!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        startTimerButton.addActionListener((ActionEvent e) -> {
            TaskManager.Task selectedTask = taskList.getSelectedValue();
            if (selectedTask == null) {
                JOptionPane.showMessageDialog(this, "Please select a task to start the timer!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int duration = selectedTask.getDurationSeconds();
            pomodoroTimer.startTimer(duration, () -> {
                // Show random motivational quote
                String quote = motivationalQuotes[new Random().nextInt(motivationalQuotes.length)];
                JOptionPane.showMessageDialog(this, quote, "Break Time!", JOptionPane.INFORMATION_MESSAGE);
                timerLabel.setText("Time Remaining: --:--:--");
            }, () -> {
                int hours = pomodoroTimer.getSecondsRemaining() / 3600;
                int minutes = (pomodoroTimer.getSecondsRemaining() % 3600) / 60;
                int seconds = pomodoroTimer.getSecondsRemaining() % 60;
                SwingUtilities.invokeLater(() -> timerLabel.setText(
                    String.format("Time Remaining: %02d:%02d:%02d", hours, minutes, seconds)));
            });
        });
    }

    private int parseTimeToSeconds(String time) {
        try {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            return hours * 3600 + minutes * 60 + seconds;
        } catch (Exception e) {
            return -1;
        }
    }
}

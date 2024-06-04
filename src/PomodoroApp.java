import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PomodoroApp {
    private JFrame frame;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private Timer timer;
    private int workDuration = 25 * 60; // 25 minutes in seconds
    private int breakDuration = 5 * 60; // 5 minutes in seconds
    private boolean isWorking = true;

    public PomodoroApp() {
        frame = new JFrame("Pomodoro Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        timerLabel = new JLabel();
        frame.add(timerLabel);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });
        frame.add(startButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseTimer();
            }
        });
        frame.add(pauseButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });
        frame.add(resetButton);

        frame.setVisible(true);
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isWorking) {
                        workDuration--;
                        if (workDuration == 0) {
                            isWorking = false;
                            timer.stop();
                            JOptionPane.showMessageDialog(frame, "Take a break!");
                        }
                    } else {
                        breakDuration--;
                        if (breakDuration == 0) {
                            isWorking = true;
                            timer.stop();
                            JOptionPane.showMessageDialog(frame, "Back to work!");
                        }
                    }
                    updateTimerLabel();
                }
            });
            timer.start();
        }
    }

    private void pauseTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
            workDuration = 25 * 60;
            breakDuration = 5 * 60;
            isWorking = true;
            updateTimerLabel();
        }
    }

    private void updateTimerLabel() {
        int duration = isWorking ? workDuration : breakDuration;
        int minutes = duration / 60;
        int seconds = duration % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PomodoroApp();
            }
        });
    }
}

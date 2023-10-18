import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.time.Instant;
import java.time.Duration;

public class AAQuiz {
    private static String[] letterCode = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I",
            "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"};
    private static String[] aminoAcids = {"alanine", "arginine", "asparagine", "aspartic acid", "cysteine",
            "glutamine", "glutamic acid", "glycine", "histidine", "isoleucine",
            "leucine", "lysine", "methionine", "phenylalanine", "proline",
            "serine", "threonine", "tryptophan", "tyrosine", "valine"};

    private JFrame frame;
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton startButton;
    private JButton cancelButton;
    private JLabel timerLabel;
    private JLabel scoreLabel;

    private int score = 0;
    private Instant startTime;
    private Timer quizTimer;

    public AAQuiz() {
        frame = new JFrame("Amino Acid Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(5, 1));

        questionLabel = new JLabel();
        answerField = new JTextField();
        startButton = new JButton("Start Quiz");
        cancelButton = new JButton("Cancel");
        timerLabel = new JLabel("Time Left: 30 seconds");
        scoreLabel = new JLabel("Score: 0/20");

        frame.add(questionLabel);
        frame.add(answerField);
        frame.add(startButton);
        frame.add(cancelButton);
        frame.add(timerLabel);
        frame.add(scoreLabel);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startQuiz();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endQuiz();
            }
        });

        frame.setVisible(true);
    }

    private void startQuiz() {
        score = 0;
        startTime = Instant.now();
        answerField.setText("");
        answerField.setEditable(true);
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        scoreLabel.setText("Score: 0/20");
        quizTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        quizTimer.start();
        nextQuestion();
    }

    private void nextQuestion() {
        if (Duration.between(startTime, Instant.now()).getSeconds() < 30) {
            int randomIndex = getRandomIndex();
            String aminoAcid = aminoAcids[randomIndex];
            questionLabel.setText("What is the one-letter code for the amino acid: " + aminoAcid + "?");

            answerField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkAnswer(randomIndex);
                }
            });
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(int randomIndex) {
        String userInput = answerField.getText().trim().toUpperCase();

        if (userInput.equals(letterCode[randomIndex])) {
            score++;
        }

        scoreLabel.setText("Score: " + score + "/20");
        answerField.setText("");
        nextQuestion();
    }

    private void updateTimer() {
        long timeLeft = 30 - Duration.between(startTime, Instant.now()).getSeconds();
        timerLabel.setText("Time Left: " + timeLeft + " seconds");
        if (timeLeft <= 0) {
            endQuiz();
        }
    }

    private void endQuiz() {
        answerField.setEditable(false);
        startButton.setEnabled(true);
        cancelButton.setEnabled(false);
        quizTimer.stop();
        questionLabel.setText("Quiz ended. Your score: " + score + "/20");
    }

    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(aminoAcids.length);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AAQuiz();
            }
        });
    }
}
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

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

    private int correctResponses = 0;
    private int incorrectResponses = 0;
    private Instant startTime;
    private Timer quizTimer;
    private int answer = getRandomIndex();

    public AAQuiz() {
        frame = new JFrame("Amino Acid Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("background.png");
        JLabel imageLabel = new JLabel(backgroundIcon);
        frame.add(imageLabel, BorderLayout.NORTH);

        Font scientificFont = new Font("Serif", Font.PLAIN, 14);
        Color mintGreen = new Color(183, 236, 191);
        Color navy = new Color(0, 0, 128);

        questionLabel = new JLabel();
        answerField = new JTextField();
        startButton = new JButton("Start Quiz");
        cancelButton = new JButton("Cancel");
        timerLabel = new JLabel("Time Left: 30 seconds");
        scoreLabel = new JLabel("Score: 0/0");

        questionLabel.setFont(scientificFont);
        answerField.setFont(scientificFont);
        answerField.setBackground(mintGreen);
        answerField.setForeground(navy);
        startButton.setFont(scientificFont);
        startButton.setBackground(mintGreen);
        startButton.setForeground(navy);
        cancelButton.setFont(scientificFont);
        cancelButton.setBackground(mintGreen);
        cancelButton.setForeground(navy);
        timerLabel.setFont(scientificFont);
        scoreLabel.setFont(scientificFont);

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridLayout(4, 2));

        quizPanel.add(questionLabel);
        quizPanel.add(answerField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(timerLabel);
        buttonPanel.add(scoreLabel);

        frame.add(quizPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startQuiz());
        cancelButton.addActionListener(e -> endQuiz());

        frame.setVisible(true);
    }

    private void startQuiz() {
        answerField.setEditable(true);
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        answerField.addActionListener(e -> checkAnswer());
        updateScoreLabel();
        startTime = Instant.now();
        quizTimer = new Timer(1000, e -> updateTimer());
        quizTimer.start();

        nextQuestion();
    }

    private void nextQuestion() {
        if (Duration.between(startTime, Instant.now()).getSeconds() < 30) {
            answer = getRandomIndex();
            String aminoAcid = aminoAcids[answer];
            questionLabel.setText("What is the one-letter code for the amino acid: " + aminoAcid + "?");
            answerField.setText("");
            answerField.requestFocus();
        } else {
            endQuiz();
        }
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
        answerField.removeActionListener(answerField.getActionListeners()[0]);
        startButton.setEnabled(true);
        cancelButton.setEnabled(false);
        quizTimer.stop();
        questionLabel.setText("Great effort! Your score: " + correctResponses + "/" + (correctResponses + incorrectResponses));
    }

    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(aminoAcids.length);
    }

    private boolean contentEquals(String userInput, String correctAnswer) {
        return userInput.equalsIgnoreCase(correctAnswer);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + correctResponses + "/" + (correctResponses + incorrectResponses));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AAQuiz());
    }

    private void checkAnswer() {
        String userInput = answerField.getText().trim().toUpperCase();
        String correctAnswer = letterCode[answer];

        if (contentEquals(userInput, correctAnswer)) {
            correctResponses++;
        } else {
            incorrectResponses++;
        }
        updateScoreLabel();
        nextQuestion();
    }
}
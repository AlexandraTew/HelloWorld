import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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

    private int score = 0;
    private int questionsAsked = 0;
    private Instant startTime;
    private Timer quizTimer;
    private boolean answeringQuestion = false;
    private ActionListener answerFieldListener;

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

        answerFieldListener = e -> checkAnswer();

        frame.setVisible(true);
    }

    private void startQuiz() {
        score = 0;
        questionsAsked = 0;
        answerField.setEditable(true);
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        updateScoreLabel();
        startTime = Instant.now();
        quizTimer = new Timer(1000, e -> updateTimer());
        quizTimer.start();
        nextQuestion();
    }

    private void nextQuestion() {
        if (Duration.between(startTime, Instant.now()).getSeconds() < 30) {
            int randomIndex = getRandomIndex();
            String aminoAcid = aminoAcids[randomIndex];
            questionLabel.setText("What is the one-letter code for the amino acid: " + aminoAcid + "?");
            answerField.setText("");
            answerField.removeActionListener(answerFieldListener);
            answerField.addActionListener(answerFieldListener);
            answeringQuestion = true;
        } else {
            endQuiz();
        }
        questionsAsked++;
    }

    private void checkAnswer() {
        if (answeringQuestion) {
            answeringQuestion = false;
            answerField.removeActionListener(answerFieldListener);
            String userInput = answerField.getText().trim().toUpperCase();
            int randomIndex = getRandomIndex();
            String correctAnswer = letterCode[randomIndex];

            if (userInput.equals(correctAnswer)) {
                score++;
            }

            updateScoreLabel();
            nextQuestion();
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
        startButton.setEnabled(true);
        cancelButton.setEnabled(false);
        quizTimer.stop();
        questionLabel.setText("Great effort! Your score: " + score + "/" + questionsAsked);
    }

    private int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(aminoAcids.length);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score + "/" + questionsAsked);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AAQuiz());
    }
}
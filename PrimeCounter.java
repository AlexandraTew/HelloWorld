import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimeCounter extends JFrame {
    private final JTextArea resultTextArea;
    private final JButton startButton;
    private final JButton cancelButton;
    private final JLabel speedLabel;
    private final JTextField threadCountTextField;

    private PrimeFinderModel model;

    public PrimeCounter() {
        setTitle("Prime Finder");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });

        resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);

        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        speedLabel = new JLabel("Speed: 0 primes/second");

        // Added text field for user to input the number of threads
        threadCountTextField = new JTextField("1", 3);
        JLabel threadCountLabel = new JLabel("Threads:");

        JPanel topPanel = new JPanel();
        topPanel.add(startButton);
        topPanel.add(cancelButton);
        topPanel.add(speedLabel);
        topPanel.add(threadCountLabel);
        topPanel.add(threadCountTextField);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        add(mainPanel);

        model = new PrimeFinderModel(speedLabel, resultTextArea);
        model.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                int value = (int) evt.getNewValue();
                resultTextArea.append("Primes found: " + value + "\n");
            } else if ("state".equals(evt.getPropertyName())) {
                if (model.getState() == SwingWorker.StateValue.DONE) {
                    startButton.setEnabled(true);
                    cancelButton.setEnabled(false);
                }
            }
        });

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            cancelButton.setEnabled(true);
            resultTextArea.setText(""); // Clear the text area

            // Modified to get the number of threads from the text field
            int threadCount = Integer.parseInt(threadCountTextField.getText());
            model.startCalculation(threadCount);
        });

        cancelButton.addActionListener(e -> {
            model.cancelCalculation();
            cancelButton.setEnabled(false);
        });
    }

    private void onWindowClosing() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrimeCounter gui = new PrimeCounter();
            gui.setVisible(true);
        });
    }
}

class PrimeFinderModel extends SwingWorker<Integer, Integer> {
    private AtomicBoolean isCancelled = new AtomicBoolean(false);
    private AtomicInteger count = new AtomicInteger(0);
    private long startTime;
    private JLabel speedLabel;
    private JTextArea resultTextArea;
    private int reportingInterval;
    private ExecutorService executorService;

    public PrimeFinderModel(JLabel speedLabel, JTextArea resultTextArea) {
        this.speedLabel = speedLabel;
        this.resultTextArea = resultTextArea;
        this.reportingInterval = 1000; // Default reporting interval to 1 second
    }

    public long getStartTime() {
        return startTime;
    }

    public int getCount() {
        return count.get();
    }

    @Override
    protected Integer doInBackground() {
        int updateInterval = reportingInterval;
        startTime = System.currentTimeMillis();
        int primeLimit = 2000000;

        for (int i = 2; i < Integer.MAX_VALUE && !isCancelled.get() && count.get() < primeLimit; i++) {
            if (isPrime(i)) {
                count.incrementAndGet();
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= updateInterval || count.get() == primeLimit) {
                    publish(count.get());
                    startTime = System.currentTimeMillis();
                }
            }
        }

        return count.get();
    }

    @Override
    protected void process(java.util.List<Integer> chunks) {
        int currentCount = count.get();
        double elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0;
        double speed = currentCount / elapsedTime;
        speedLabel.setText(String.format("Speed: %.2f primes/second", speed));

        for (int value : chunks) {
            resultTextArea.append("Primes found: " + value + "\n");

            // Display additional information when 2 million primes are found
            if (value >= 2000000) {
                displayFinalResults();
            }
        }
    }

    private void displayFinalResults() {
        long totalTime = System.currentTimeMillis() - startTime;
        int totalPrimes = count.get();
        double primesPerMillisecond = (double) totalPrimes / totalTime;

        resultTextArea.append("Total primes found: " + totalPrimes + "\n");
        resultTextArea.append("Total time: " + totalTime + " milliseconds\n");
        resultTextArea.append("Primes per millisecond: " + primesPerMillisecond + "\n");
    }

    public void startCalculation(int threadCount) {
        if (getState() == StateValue.PENDING || getState() == StateValue.DONE) {
            isCancelled.set(false);
            count.set(0);

            // Create a new thread pool with the specified number of threads
            executorService = Executors.newCachedThreadPool();

            // Modified to use a thread pool with the specified number of threads
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> doInBackground());
            }
        }
    }

    public void cancelCalculation() {
        isCancelled.set(true);
        if (executorService != null) {
            executorService.shutdownNow(); // Shut down the thread pool
        }
        cancel(true);
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}


/* This one was a little tougher and I struggled with several issues to eventually end up with the code above.
 * The current issue is the failure to track time correctly. I think I need the time to be tracked outside of the loop
 * but wasn't quite sure how to make this happen in the time I had left before the assignmnet was due! Any hints or
 * suggestions would be much appreciated! 
 */
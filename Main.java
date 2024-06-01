import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame {
    private JTextField directoryPathField;
    private JTextField searchField;
    private JTextArea resultArea;

    public Main() {
        setTitle("File Browser and Search");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        directoryPathField = new JTextField();
        directoryPathField.setColumns(20);
        directoryPathField.setText("Enter directory path");

        searchField = new JTextField();
        searchField.setColumns(20);
        searchField.setText("Enter search phrase");

        resultArea = new JTextArea();
        resultArea.setRows(10);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(580, 200));

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseDirectory();
            }
        });

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFiles();
            }
        });

        JPanel hBox = new JPanel();
        hBox.setLayout(new FlowLayout(FlowLayout.LEFT));
        hBox.add(directoryPathField);
        hBox.add(browseButton);

        JPanel vBox = new JPanel();
        vBox.setLayout(new BoxLayout(vBox, BoxLayout.Y_AXIS));
        vBox.add(hBox);
        vBox.add(searchField);
        vBox.add(searchButton);
        vBox.add(scrollPane);

        add(vBox, BorderLayout.CENTER);
    }

    private void browseDirectory() {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = directoryChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = directoryChooser.getSelectedFile();
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void searchFiles() {
        String directoryPath = directoryPathField.getText();
        if (directoryPath.isEmpty()) {
            resultArea.setText("Please provide a directory path.");
            return;
        }

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            resultArea.setText("The provided path is not a directory.");
            return;
        }

        StringBuilder results = new StringBuilder();
        listFilesInDirectory(directory, results);
        resultArea.setText(results.toString());
    }

    private void listFilesInDirectory(File directory, StringBuilder results) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFilesInDirectory(file, results);
                } else {
                    if (file.getName().contains(searchField.getText())) {
                        results.append(file.getAbsolutePath()).append("\n");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}

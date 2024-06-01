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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}
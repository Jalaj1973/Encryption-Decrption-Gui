import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptionGUI extends JFrame {

    private JButton encryptButton;
    private JButton decryptButton;
    private JTextField inputFileField;
    private JTextField keyField;
    private JLabel inputFileLabel;
    private JLabel keyLabel;

    public FileEncryptionGUI() {
        setTitle("File Encryption and Decryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        inputFileLabel = new JLabel("File to Encrypt/Decrypt:");
        inputFileField = new JTextField(30);
        keyLabel = new JLabel("Key (Password):");
        keyField = new JTextField(30);
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(inputFileLabel, constraints);

        constraints.gridx = 1;
        add(inputFileField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(keyLabel, constraints);

        constraints.gridx = 1;
        add(keyField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFile = inputFileField.getText();
                String key = keyField.getText();
                if (inputFile.isEmpty() || key.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid input file path and key!");
                    return;
                }
                try {
                    encryptFile(inputFile, key);
                    JOptionPane.showMessageDialog(null, "File encrypted successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Encryption failed!");
                }
                keyField.setText("");
            }
        });
        add(encryptButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFile = inputFileField.getText();
                String key = keyField.getText();
                if (inputFile.isEmpty() || key.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid input file path and key!");
                    return;
                }
                try {
                    decryptFile(inputFile, key);
                    JOptionPane.showMessageDialog(null, "File decrypted successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Decryption failed!");
                }
                keyField.setText("");
            }
        });
        add(decryptButton, constraints);

        pack();
        setLocationRelativeTo(null); // Center the window on the screen
    }

    public void encryptFile(String inputFile, String key) throws Exception {
        SecretKeySpec secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        try (RandomAccessFile file = new RandomAccessFile(inputFile, "rw");
             FileChannel channel = file.getChannel()) {
            FileLock lock = channel.lock();
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            buffer.flip();
            byte[] encryptedData = cipher.doFinal(buffer.array());
            channel.position(0);
            channel.write(ByteBuffer.wrap(encryptedData));
            channel.truncate(encryptedData.length);
            lock.release();
        }
    }

    public void decryptFile(String inputFile, String key) throws Exception {
        SecretKeySpec secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        try (RandomAccessFile file = new RandomAccessFile(inputFile, "rw");
             FileChannel channel = file.getChannel()) {
            FileLock lock = channel.lock();
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            buffer.flip();
            byte[] decryptedData = cipher.doFinal(buffer.array());
            channel.position(0);
            channel.write(ByteBuffer.wrap(decryptedData));
            channel.truncate(decryptedData.length);
            lock.release();
        }
    }

    public SecretKeySpec generateKey(String key) throws Exception {
        byte[] keyBytes = new byte[16]; // AES key length is 128 bits (16 bytes)
        byte[] passwordBytes = key.getBytes();
        int length = Math.min(passwordBytes.length, keyBytes.length);
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new FileEncryptionGUI().setVisible(true);
            }
        });
    }
}

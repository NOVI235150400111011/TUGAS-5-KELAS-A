import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabelDataBaseAkademik extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public TabelDataBaseAkademik() {
        setTitle("TABEL DATA BASE AKADEMIK");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Membuat label
        JLabel label = new JLabel("Tabel Data Base Akademik", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(label, BorderLayout.NORTH);

        // Membuat tabel
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Membuat tombol
        JButton button = new JButton("TAMPILKAN TABEL");
        panel.add(button, BorderLayout.SOUTH);

        // Menambahkan action listener untuk tombol
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData();
            }
        });

        add(panel);
    }

    private void loadTableData() {
        String filePath = "C:\\Users\\ASUS\\Downloads\\DB AKADEMIK\\Akademik.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            tableModel.setRowCount(0); // Mengosongkan data yang ada
            tableModel.setColumnCount(0); // Mengosongkan kolom yang ada
            boolean isFirstRow = true;
            boolean isSecondRow = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("|")) {
                    // Menghilangkan karakter '|' dan memotong whitespace di kedua ujung
                    String[] data = line.split("\\|");
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim();
                    }
                    if (isFirstRow) {
                        // Menambahkan kolom ke tabel, mengabaikan elemen kosong pertama dan terakhir
                        for (int i = 1; i < data.length - 1; i++) {
                            tableModel.addColumn(data[i]);
                        }
                        isFirstRow = false;
                        isSecondRow = true;
                    } else if (isSecondRow) {
                        // Abaikan baris kedua yang berisi garis pemisah
                        isSecondRow = false;
                    } else {
                        // Menambahkan baris ke tabel, mengabaikan elemen kosong pertama dan terakhir
                        if (data.length > 1) {
                            Object[] rowData = new Object[data.length - 2];
                            System.arraycopy(data, 1, rowData, 0, data.length - 2);
                            tableModel.addRow(rowData);
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TabelDataBaseAkademik().setVisible(true);
            }
        });
    }
}

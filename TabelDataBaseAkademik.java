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

    // Konstruktor untuk membuat GUI
    public TabelDataBaseAkademik() {
        setTitle("TABEL DATA BASE AKADEMIK");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Membuat label judul
        JLabel label = new JLabel("Tabel Data Base Akademik", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(label, BorderLayout.NORTH);

        // Membuat tabel
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Membuat tombol untuk menampilkan tabel
        JButton button = new JButton("TAMPILKAN TABEL");
        panel.add(button, BorderLayout.SOUTH);

        // Menambahkan action listener untuk tombol
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData(); // Memuat data tabel saat tombol ditekan
            }
        });

        add(panel);
    }

    // Method untuk memuat data tabel dari file
    private void loadTableData() {
        String filePath = "C:\\Users\\ASUS\\Downloads\\DB AKADEMIK\\Akademik.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            tableModel.setRowCount(0); // Mengosongkan data yang ada
            tableModel.setColumnCount(0); // Mengosongkan kolom yang ada
            boolean isFirstRow = true; // Menandai apakah ini baris pertama (header)
            boolean isSecondRow = false; // Menandai apakah ini baris kedua (garis pemisah)

            // Membaca baris demi baris dari file
            while ((line = br.readLine()) != null) {
                if (line.startsWith("|")) {
                    // Memecah baris menjadi data dengan delimiter '|'
                    String[] data = line.split("\\|");
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim(); // Menghilangkan spasi di sekitar data
                    }
                    if (isFirstRow) {
                        // Menambahkan kolom ke tabel dari baris pertama
                        for (int i = 1; i < data.length - 1; i++) {
                            tableModel.addColumn(data[i]);
                        }
                        isFirstRow = false;
                        isSecondRow = true;
                    } else if (isSecondRow) {
                        // Mengabaikan baris kedua yang berisi garis pemisah
                        isSecondRow = false;
                    } else {
                        // Menambahkan baris data ke tabel
                        if (data.length > 1) {
                            Object[] rowData = new Object[data.length - 2];
                            System.arraycopy(data, 1, rowData, 0, data.length - 2);
                            tableModel.addRow(rowData);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Menampilkan pesan kesalahan jika terjadi error saat membaca file
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method untuk menjalankan aplikasi GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TabelDataBaseAkademik().setVisible(true);
            }
        });
    }
}

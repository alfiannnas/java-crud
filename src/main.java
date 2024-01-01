package src;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class main extends JFrame {
	private JLabel judul;
	private JLabel nama;
	private JLabel nisn;
	private JLabel alamat;
	private JLabel agama;
	private JLabel asalsekolah;

	private JTextField TextNama;
	private JTextField TextNisn;
	private JTextArea TextAlamat;
	private JComboBox TextAgama;
	private JTextField TextAsalSekolah;
	
	private DefaultTableModel data_tabel;
    private JTable tabelPanel;
    private JScrollPane scrollTable;
	
	private JButton ButtonShow;
	private JButton ButtonInput;
	private JButton ButtonUpdate;
	private JButton ButtonExit;
	private JButton ButtonDelete;

	private String id_mhs;
	private String query;
    private DatabaseConnection db;
    private PreparedStatement pst;
    private Statement stat;
    private ResultSet rs;

	public main(){
        this.setTitle("Pendaftaran Siswa");
        this.setSize(540, 600);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(520, 600));
        contentPane.setBackground(new Color(255, 255, 240));
        judul = new JLabel();
        judul.setBounds(40, 20, 275, 40);
        judul.setFont(new Font("SansSerif", Font.BOLD, 24));
        judul.setText("PENDAFTARAN SISWA");

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(40, 80, 460, 280);
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Form Pendaftaran Siswa", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 16), Color.WHITE));
        formPanel.setBackground(new Color(44, 62, 80));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBounds(40, 370, 460, 50);
        buttonPanel.setBackground(new Color(52, 73, 94));
  
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
		
		nama = new JLabel();
		nama.setBounds(40,90,90,35);
		nama.setText("Nama");
		nisn = new JLabel();
		nisn.setBounds(40,130,90,35);
		nisn.setText("NISN");
		alamat = new JLabel();
		alamat.setBounds(40,170,90,35);
		alamat.setText("Alamat");
		agama = new JLabel();
		agama.setBounds(40,280,120,35);
		agama.setText("Agama");
		asalsekolah = new JLabel();
		asalsekolah.setBounds(40,330,150,35);
		asalsekolah.setText("Sekolah");
		

		TextNama = new JTextField();
		TextNama.setBounds(100,90,350,35);
		TextNisn = new JTextField();
		TextNisn.setBounds(100,128,350,35);
		TextAlamat = new JTextArea();
		TextAlamat.setBounds(100,170,350,100);
		TextAgama = new JComboBox();
		TextAgama.setBounds(100,280,350,35);
		TextAgama.addItem("Islam");
		TextAgama.addItem("Kristen Katolik");
		TextAgama.addItem("Kristen Protestan");
		TextAgama.addItem("Hindu");
		TextAgama.addItem("Budha");
		TextAgama.addItem("Konguchu");
		TextAsalSekolah = new JTextField();
		TextAsalSekolah.setBounds(100,330,350,35);

		String tableTitle[] = {"No","ID","Nama","NISN","Alamat","Agama", "Asal Sekolah"};
        data_tabel  = new DefaultTableModel(null,tableTitle);

        tabelPanel = new JTable();
        tabelPanel.setModel(data_tabel);
        tabelPanel.setEnabled(true);
        tabelPanel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scrollTable = new JScrollPane();
        scrollTable.getViewport().add(tabelPanel);
        scrollTable.setBounds(10,380,500,150);
        getDataTabel();

		ButtonShow = new JButton();
		ButtonShow.setBounds(40,540,90,35);
		ButtonShow.setText("Tampil");
		ButtonShow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                getDataTabel();
            }
        });

		ButtonInput = new JButton("Tambah");
		ButtonInput.setBounds(150, 540, 90, 35);
		ButtonInput.setBackground(new Color(46, 204, 113));
		ButtonInput.setForeground(Color.WHITE);
		ButtonInput.setFocusPainted(false);
		
		ButtonInput.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				ButtonInput.setBackground(new Color(39, 174, 96));
			}
		
			public void mouseExited(java.awt.event.MouseEvent evt) {
				ButtonInput.setBackground(new Color(46, 204, 113));
			}
			
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				save_data(evt);
				getDataTabel();
			}
		});
		

        ButtonUpdate = new JButton("Ubah");
        ButtonUpdate.setBounds(260, 540, 90, 35);
        ButtonUpdate.setBackground(new Color(255, 255, 85));
        ButtonUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                update_data(evt);
                getDataTabel();
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                ButtonUpdate.setBackground(new Color(255, 255, 0));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                ButtonUpdate.setBackground(new Color(255, 255, 85));
            }
        });

		ButtonExit = new JButton("Exit");
		ButtonExit.setBounds(370, 540, 90, 35);
		ButtonExit.setBackground(new Color(231, 76, 60));
		ButtonExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				dispose();
			}
		
			@Override
			public void mouseEntered(MouseEvent evt) {
				ButtonExit.setBackground(new Color(192, 57, 43));
			}
		
			@Override
			public void mouseExited(MouseEvent evt) {
				ButtonExit.setBackground(new Color(231, 76, 60));
			}
		});

		ButtonDelete = new JButton("Delete");
		ButtonDelete.setBounds(370, 480, 90, 35);
		ButtonDelete.setBackground(new Color(231, 76, 60));
		ButtonDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				delete_data(evt);
				getDataTabel();
			}
		
			@Override
			public void mouseEntered(MouseEvent evt) {
				ButtonDelete.setBackground(new Color(192, 57, 43));
			}
		
			@Override
			public void mouseExited(MouseEvent evt) {
				ButtonDelete.setBackground(new Color(231, 76, 60));
			}
		});

		contentPane.add(judul);
		contentPane.add(nama);
		contentPane.add(nisn);
		contentPane.add(alamat);
		contentPane.add(agama);
		contentPane.add(asalsekolah);
		contentPane.add(TextNama);
		contentPane.add(TextNisn);
		contentPane.add(TextAlamat);
		contentPane.add(TextAgama);
		contentPane.add(TextAsalSekolah);

		contentPane.add(scrollTable, BorderLayout.CENTER);

		contentPane.add(ButtonShow);
		contentPane.add(ButtonInput);
		contentPane.add(ButtonUpdate);
		contentPane.add(ButtonDelete);

		contentPane.add(ButtonExit);
		
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private void getDataTabel(){
        try{
            db   = new DatabaseConnection();
            stat = db.koneksi.createStatement();
            rs   = stat.executeQuery("SELECT * FROM data_siswa");
            int no = 1;
            data_tabel.setRowCount(0);
            while(rs.next()){
                String t_no 	= Integer.toString(no);
                String t_id 	= rs.getString("id");
                String t_nama   = rs.getString("nama");
                String t_nisn  	= rs.getString("nisn");
                String t_alamat = rs.getString("alamat");
                String t_agama  = rs.getString("agama");
				String t_asalsekolah  = rs.getString("asalsekolah");

                String[] t_data = {t_no, t_id, t_nama, t_nisn, t_alamat, t_agama, t_asalsekolah};
                data_tabel.addRow(t_data);
                no++;
            }
            rs.close();
            stat.close();
            db.koneksi.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void update_data(MouseEvent evt) {
		id_mhs = JOptionPane.showInputDialog(null, "Input ID Mahasiswa ?");
		if (
				TextNama.getText().isEmpty() &&
						TextNisn.getText().isEmpty() &&
						TextAlamat.getText().isEmpty()
		) {
			JOptionPane.showMessageDialog(null, "Please Input All Fields");
		} else {
			try {
				query = "UPDATE data_siswa SET `nama` = ?, `nisn` = ?, `alamat` = ?, `agama` = ?, `asalsekolah` = ? WHERE `id` = ?";
				db = new DatabaseConnection();
				pst = db.koneksi.prepareStatement(query);
	
				pst.setString(1, TextNama.getText());
				pst.setString(2, TextNisn.getText());
				pst.setString(3, TextAlamat.getText());
				pst.setString(4, TextAgama.getSelectedItem().toString());
				pst.setString(5, TextAsalSekolah.getText());
				pst.setString(6, id_mhs);
	
				pst.execute();
				db.koneksi.close();
				JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	

    private void save_data(MouseEvent evt) {
		if (
				TextNama.getText().isEmpty() &&
						TextNisn.getText().isEmpty() &&
						TextAlamat.getText().isEmpty()
		) {
			JOptionPane.showMessageDialog(null, "Tolong Inputkan Data Keseluruhan!");
		} else {
			try {
				query = "INSERT INTO data_siswa (`nama`, `nisn`, `alamat`, `agama`, `asalsekolah`) VALUES(?,?,?,?,?)";
				db = new DatabaseConnection();
				pst = db.koneksi.prepareStatement(query);
	
				pst.setString(1, TextNama.getText());
				pst.setString(2, TextNisn.getText());
				pst.setString(3, TextAlamat.getText());
				pst.setString(4, TextAgama.getSelectedItem().toString());
				pst.setString(5, TextAsalSekolah.getText());
	
				pst.execute();
				db.koneksi.close();
				JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	

    private void delete_data (MouseEvent evt) {
        id_mhs = JOptionPane.showInputDialog(null, "Masukkan ID Mahasiswa");
	    try{
	        query = "DELETE FROM data_siswa WHERE id = ?";
	        db   = new DatabaseConnection();
	        pst = db.koneksi.prepareStatement(query);
	        pst.setString(1,id_mhs);
	        pst.execute();
	        db.koneksi.close();
	        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
	    }
	    catch(Exception e)
	    {
	        JOptionPane.showMessageDialog(null, e);
	    }
    }

	public static void main(String[] args){
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new main();
			}
		});
	}
}
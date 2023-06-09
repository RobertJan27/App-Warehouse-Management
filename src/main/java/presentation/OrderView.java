/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentation;

import bll.Clientbll;
import bll.OrderBll;
import bll.ProductBll;
import connection.ConnectionFactory;
import dao.ClientDao;
import dao.OrderDao;
import dao.ProductDao;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class OrderView extends javax.swing.JFrame {
    OrderDao orderDao;
    OrderBll orderBll;

    /**
     * Creates new form OrderView
     */
    public OrderView() {
        initComponents();
        LoadClient();
        LoadProduct();
        tabel();
    }

    public void LoadClient() {
        ClientDao clientDao = new ClientDao();
        List<Client> tabel = clientDao.findAll();
        for (int i = 0; i < tabel.size(); i++) {
            client.addItem(String.valueOf(tabel.get(i).getId()));
        }
    }

    public void LoadProduct() {
        ProductDao productDao = new ProductDao();
        List<Product> tabel = productDao.findAll();
        for (int i = 0; i < tabel.size(); i++) {
            produs.addItem(String.valueOf(tabel.get(i).getId()));
        }
    }

    public boolean updatecantitate(int noua_cantitate, int id_produs, int id_order) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int cantitate_veche = 0;
        int cantitate_depozit = 0;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("select * from product where id=?");
            statement.setInt(1, id_produs);
            resultSet = statement.executeQuery();
            resultSet.next();
            cantitate_depozit = resultSet.getInt(4);

        } catch (SQLException e) {

        }

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("select *  from `order` where id=?");
            statement.setInt(1, id_order);
            resultSet = statement.executeQuery();
            resultSet.next();
            cantitate_veche = resultSet.getInt(5);
        } catch (SQLException e) {

        }

        if ((noua_cantitate - cantitate_veche) > cantitate_depozit)
            return false;
        else {
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement("update product set cantitate=? where id=?");
                statement.setInt(1, cantitate_depozit - (noua_cantitate - cantitate_veche));
                statement.setInt(2, id_produs);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public void table_Cautare(int id) {
        orderDao = new OrderDao();
        Order tabel = orderDao.findById(id);
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        df.setRowCount(0);
        Object rowData[] = new Object[5];
        if (tabel != null) {
            rowData[0] = tabel.getId();
            rowData[1] = tabel.getId_client();
            rowData[2] = tabel.getId_produs();
            rowData[3] = tabel.getData();
            rowData[4] = tabel.getCantitate();
            df.addRow(rowData);
        } else {
            rowData[0] = null;
            rowData[1] = null;
            rowData[2] = null;
            rowData[3] = null;
            rowData[4] = null;
            df.addRow(rowData);
        }
    }

    public void table_User() {
        orderDao = new OrderDao();
        List<Order> tabel = orderDao.findAll();
        DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
        df.setRowCount(0);
        Object rowData[] = new Object[5];

        for (int i = 0; i < tabel.size(); i++) {
            rowData[0] = tabel.get(i).getId();
            rowData[1] = tabel.get(i).getId_client();
            rowData[2] = tabel.get(i).getId_produs();
            rowData[3] = tabel.get(i).getData();
            rowData[4] = tabel.get(i).getCantitate();
            df.addRow(rowData);
        }


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        data = new javax.swing.JTextField();
        cantitate = new javax.swing.JTextField();
        produs = new javax.swing.JComboBox<>();
        client = new javax.swing.JComboBox<>();
        Insert = new javax.swing.JButton();
        Update = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        Back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Orders");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Id", "Id Client", "Id Produs", "Data", "Cantitate"
                }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Id client");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Id produs");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Data");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Cantitate");

        Insert.setText("Insert");
        Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertActionPerformed(evt);
            }
        });

        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(299, 299, 299)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(Search, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                        .addComponent(Back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Update, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                        .addComponent(data)
                                        .addComponent(produs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(id)
                                        .addComponent(client, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cantitate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(client, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(produs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel6)
                                                        .addComponent(cantitate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(27, 27, 27)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(Search, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                                        .addComponent(Insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(Back, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                                                .addGap(196, 196, 196))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void InsertActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        orderBll = new OrderBll();
        int id1 = Integer.parseInt(id.getText());
        int client1 = Integer.parseInt(client.getSelectedItem().toString());
        int produs1 = Integer.parseInt(produs.getSelectedItem().toString());
        String data1 = data.getText();
        int cantitate1 = Integer.parseInt(cantitate.getText());
        if (updatecantitate(cantitate1, produs1, 0) == false) {
            JOptionPane.showMessageDialog(this, "Nu sunt destule produse in depozit");
        } else {
            Order ord = new Order(id1, client1, produs1, data1, cantitate1);
            FileWriter Log = null;
            try {
                Log = new FileWriter(ord.getId()+".txt", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Log.write(ord.toString());}
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Log.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            orderBll.Insert(ord);
            JOptionPane.showMessageDialog(this, "Comanda introdusa cu succes");
            tabel();
        }
    }

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        orderBll = new OrderBll();
        int id1 = Integer.parseInt(id.getText());
        int client1 = Integer.parseInt(client.getSelectedItem().toString());
        int produs1 = Integer.parseInt(produs.getSelectedItem().toString());
        String data1 = data.getText();
        int cantitate1 = Integer.parseInt(cantitate.getText());

        if (updatecantitate(cantitate1, produs1, id1) == false) {
            JOptionPane.showMessageDialog(this, "Nu sunt destule produse in depozit");
        } else {
            Order ord = new Order(id1, client1, produs1, data1, cantitate1);
            orderBll.Update(ord);
            JOptionPane.showMessageDialog(this, "Comanda reinnoita cu succes");
            tabel();
        }
    }

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        orderBll = new OrderBll();
        int id1 = Integer.parseInt(id.getText());
        int client1 = Integer.parseInt(client.getSelectedItem().toString());
        int produs1 = Integer.parseInt(produs.getSelectedItem().toString());
        String data1 = "da";
        int cantitate1 = 0;
        Order ord = new Order(id1, client1, produs1, data1, cantitate1);
        if (updatecantitate(cantitate1, produs1, id1) == false) {
        }
        orderBll.Delete(ord);

        JOptionPane.showMessageDialog(this, "Comanda stearsa cu succes");
        tabel();
    }

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.setVisible(false);

    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        int id1 = Integer.parseInt(id.getText());
        tabelid(id1);
    }

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        tabel();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int SelectIndex = jTable1.getSelectedRow();
        id.setText(d1.getValueAt(SelectIndex, 0).toString());
        data.setText(d1.getValueAt(SelectIndex, 3).toString());
        cantitate.setText(d1.getValueAt(SelectIndex, 4).toString());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton Back;
    private javax.swing.JButton Delete;
    private javax.swing.JButton Exit;
    private javax.swing.JButton Insert;
    private javax.swing.JButton Search;
    private javax.swing.JButton Update;
    private javax.swing.JTextField cantitate;
    private javax.swing.JComboBox<String> client;
    private javax.swing.JTextField data;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> produs;
    // End of variables declaration





















    public void tabel() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("select * from `order`");
            resultSet = statement.executeQuery();
            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
            df.setRowCount(0);
            Object rowData[] = new Object[5];
            while (resultSet.next()) {
                rowData[0] = resultSet.getInt(1);
                rowData[1] = resultSet.getInt(2);
                rowData[2] = resultSet.getInt(3);
                rowData[3] = resultSet.getString(4);
                rowData[4] = resultSet.getInt(5);
                df.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void tabelid(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("select * from `order` where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            DefaultTableModel df = (DefaultTableModel) jTable1.getModel();
            df.setRowCount(0);
            Object rowData[] = new Object[5];
            while (resultSet.next()) {
                rowData[0] = resultSet.getInt(1);
                rowData[1] = resultSet.getInt(2);
                rowData[2] = resultSet.getInt(3);
                rowData[3] = resultSet.getString(4);
                rowData[4] = resultSet.getInt(5);
                df.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
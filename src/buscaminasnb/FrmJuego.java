package buscaminasnb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FrmJuego extends javax.swing.JFrame {
    int numFilas = 10, numColumnas = 10, numMinas = 10;
    JButton[][] botonesTablero;
    TableroBuscaminas tableroBuscaminas;

    public FrmJuego() {
        initComponents();
        juegoNuevo();
    }

    private void juegoNuevo() {
        descargarControles();
        cargarControles();
        crearTableroBuscaminas();
        repaint();
    }

    void descargarControles() {
        if(botonesTablero != null) {
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (botonesTablero[i][j] != null) {
                        getContentPane().remove(botonesTablero[i][j]);
                    }
                }
            }
        }
    }

    private void crearTableroBuscaminas() {
        tableroBuscaminas = new TableroBuscaminas(numFilas, numColumnas, numMinas);

        tableroBuscaminas.setEventoPartidaPerdida(new Consumer<List<Casilla>>() {
            @Override public void accept(List<Casilla> t) {
                for (Casilla casillaConMina : t) {
                    botonesTablero[casillaConMina.getPostFila()][casillaConMina.getPosColumna()].setText("*");
                }
                JOptionPane.showMessageDialog(null,
                        "Has perdido el juego!", "Fin del juego",
                        JOptionPane.INFORMATION_MESSAGE
                );
                juegoNuevo(); // Reinicia el juego
            }
        });

        tableroBuscaminas.setEventoPartidaGanada(new Consumer<List<Casilla>>() {
            @Override public void accept(List<Casilla> t) {
                for (Casilla casillaConMina : t) {
                    botonesTablero[casillaConMina.getPostFila()][casillaConMina.getPosColumna()].setText(":)");
                }
                JOptionPane.showMessageDialog(null,
                        "Has ganado el juego!", "Fin del juego",
                        JOptionPane.INFORMATION_MESSAGE
                );
                juegoNuevo(); // Reinicia el juego
            }
        });

        tableroBuscaminas.setEventoCasillaAbierta(new Consumer<Casilla>() {
            @Override public void accept(Casilla t) {
                botonesTablero[t.getPostFila()][t.getPosColumna()].setEnabled(false);
                botonesTablero[t.getPostFila()][t.getPosColumna()].setText(
                        t.getNumMinasAlrededor() == 0 ? "" : t.getNumMinasAlrededor() + ""
                );
            }
        });
        tableroBuscaminas.imprimirTablero();
    }

    private void cargarControles() {
        int posXReferencia = 25, posYReferencia = 25, anchoControl = 30, altoControl = 30;
        botonesTablero = new JButton[numFilas][numColumnas];

        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setName(i + "," + j);
                botonesTablero[i][j].setBorder(null);

                if (i == 0 && j == 0) {
                    botonesTablero[i][j].setBounds(posXReferencia, posYReferencia, anchoControl, altoControl);
                }
                else if (i == 0 && j != 0) {
                    botonesTablero[i][j].setBounds(botonesTablero[i][j - 1].getX() +
                            botonesTablero[i][j - 1].getWidth(), posYReferencia, anchoControl, altoControl
                    );
                }
                else {
                    botonesTablero[i][j].setBounds(botonesTablero[i - 1][j].getX(), botonesTablero[i - 1][j].getY()
                            + botonesTablero[i - 1][j].getHeight(), anchoControl, altoControl
                    );
                }
                botonesTablero[i][j].addActionListener(new ActionListener() {
                    @Override public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });
                getContentPane().add(botonesTablero[i][j]);
            }
        }
        this.setSize(botonesTablero[numFilas - 1][numColumnas - 1].getX() +
                        botonesTablero [numFilas - 1][numColumnas - 1].getWidth() + 30,
                botonesTablero[numFilas - 1][numColumnas - 1].getY() +
                        botonesTablero [numFilas - 1][numColumnas - 1].getHeight() + 70
        );
    }

    private void btnClick(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String[] coordenada = btn.getName().split(",");
        int fila = Integer.parseInt(coordenada[0]);
        int columna = Integer.parseInt(coordenada[1]);
        //JOptionPane.showMessageDialog(rootPane, fila + "," + columna);
        tableroBuscaminas.seleccionarCasilla(fila, columna);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JMenuItem jMenuItem3 = new javax.swing.JMenuItem();
        javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu jMenu1 = new javax.swing.JMenu();
        javax.swing.JMenuItem juegoNuevo = new javax.swing.JMenuItem();
        javax.swing.JMenuItem tamannoJuego = new javax.swing.JMenuItem();
        javax.swing.JMenuItem numeroMinas = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Juego");

        juegoNuevo.setText("Nuevo");
        juegoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                juegoNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(juegoNuevo);

        tamannoJuego.setText("Tamaño");
        tamannoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tamannoJuegoActionPerformed(evt);
            }
        });
        jMenu1.add(tamannoJuego);

        numeroMinas.setText("Numero de minas");
        numeroMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroMinasActionPerformed(evt);
            }
        });
        jMenu1.add(numeroMinas);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void juegoNuevoActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_juegoNuevoActionPerformed
        juegoNuevo(); 
    }//GEN-LAST:event_juegoNuevoActionPerformed

    private void tamannoJuegoActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tamannoJuegoActionPerformed
        int num = Integer.parseInt(JOptionPane.showInputDialog("Digite tamaño de la matriz n x m")); 
        this.numFilas = num;
        this.numColumnas = num; 
        juegoNuevo(); 
    }//GEN-LAST:event_tamannoJuegoActionPerformed

    private void numeroMinasActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_numeroMinasActionPerformed
        int num = Integer.parseInt(JOptionPane.showInputDialog("Digite el numero de minas")); 
        this.numMinas = num;
        juegoNuevo(); 
    }//GEN-LAST:event_numeroMinasActionPerformed

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
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJuego().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
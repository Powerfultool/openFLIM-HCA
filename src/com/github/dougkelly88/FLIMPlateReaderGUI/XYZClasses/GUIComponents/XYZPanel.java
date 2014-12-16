/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.dougkelly88.FLIMPlateReaderGUI.XYZClasses.GUIComponents;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.PlateProperties;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.SeqAcqProps;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.InstrumentInterfaceClasses.XYZMotionInterface;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FOV;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;
import mmcorej.CMMCore;
import mmcorej.StrVector;
import org.micromanager.MMStudio;

/**
 *
 * @author dk1109
 */
public class XYZPanel extends javax.swing.JPanel {
    final static String um = "("+"\u00B5"+"m)";
    private WellMapDrawPanel dp_;
    private PlateProperties pp_;
    private double currentXUm_ = 128;
    private double currentYUm_ = 128;
    private Object parent_;
    private SeqAcqProps sap_;
    private XYZMotionInterface xyzmi_;
    private FOV currentFOV_;
    private CMMCore core_;
    
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;
    /**
     * Creates new form XYZPanel
     */
    public XYZPanel() {
        initComponents();
        setControlDefaults();
        
        // add micro symbols
        stepSizeLabel.setText("Step size " + um);
        zStepSizeLabel.setText("Step size " + um);
        afOffsetLabel.setText("AF offset "+ um);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        XYPanel = new javax.swing.JPanel();
        panningPanel = new javax.swing.JPanel();
        stepSizeLabel = new javax.swing.JLabel();
        stepSizeField = new javax.swing.JFormattedTextField();
        panningButtonPanel = new javax.swing.JPanel();
        wellMapPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        wellField = new javax.swing.JTextField();
        goToWellButton = new javax.swing.JButton();
        ZPanel = new javax.swing.JPanel();
        zPanel = new javax.swing.JPanel();
        zStepSizeLabel = new javax.swing.JLabel();
        zStepSizeField = new javax.swing.JFormattedTextField();
        zUButton = new javax.swing.JButton();
        zDButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        afObjectiveCombo = new javax.swing.JComboBox();
        afOffsetLabel = new javax.swing.JLabel();
        afOffsetField = new javax.swing.JFormattedTextField();
        afSearchLabel = new javax.swing.JLabel();
        afSearchField = new javax.swing.JFormattedTextField();
        afNowButton = new javax.swing.JButton();
        afInSequence = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        manUscopeCheck = new javax.swing.JCheckBox();
        manStageCheck = new javax.swing.JCheckBox();
        manFocusCheck = new javax.swing.JCheckBox();
        keyboardStageCheck = new javax.swing.JCheckBox();

        XYPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("XY control"));

        panningPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Panning"));

        stepSizeLabel.setText("Step size (um)");

        stepSizeField.setText("9000");
        stepSizeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepSizeFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panningButtonPanelLayout = new javax.swing.GroupLayout(panningButtonPanel);
        panningButtonPanel.setLayout(panningButtonPanelLayout);
        panningButtonPanelLayout.setHorizontalGroup(
            panningButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );
        panningButtonPanelLayout.setVerticalGroup(
            panningButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panningPanelLayout = new javax.swing.GroupLayout(panningPanel);
        panningPanel.setLayout(panningPanelLayout);
        panningPanelLayout.setHorizontalGroup(
            panningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panningPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panningButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panningPanelLayout.createSequentialGroup()
                        .addComponent(stepSizeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stepSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        panningPanelLayout.setVerticalGroup(
            panningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panningPanelLayout.createSequentialGroup()
                .addComponent(panningButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panningPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stepSizeLabel)
                    .addComponent(stepSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        wellMapPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Well map"));
        wellMapPanel.setEnabled(false);

        javax.swing.GroupLayout wellMapPanelLayout = new javax.swing.GroupLayout(wellMapPanel);
        wellMapPanel.setLayout(wellMapPanelLayout);
        wellMapPanelLayout.setHorizontalGroup(
            wellMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        wellMapPanelLayout.setVerticalGroup(
            wellMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setText("Go to well:");

        wellField.setText("C4");
        wellField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wellFieldActionPerformed(evt);
            }
        });

        goToWellButton.setText("Go");
        goToWellButton.setEnabled(false);
        goToWellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToWellButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout XYPanelLayout = new javax.swing.GroupLayout(XYPanel);
        XYPanel.setLayout(XYPanelLayout);
        XYPanelLayout.setHorizontalGroup(
            XYPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(XYPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wellMapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(XYPanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(wellField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(goToWellButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        XYPanelLayout.setVerticalGroup(
            XYPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(XYPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(XYPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(goToWellButton)
                    .addComponent(wellField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(XYPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wellMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        ZPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Z control"));

        zPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Z focus"));

        zStepSizeLabel.setText("Step size (um)");

        zStepSizeField.setText("1");
        zStepSizeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zStepSizeFieldActionPerformed(evt);
            }
        });

        zUButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/up.png"))); // NOI18N
        zUButton.setIconTextGap(0);
        zUButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        zUButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zUButtonActionPerformed(evt);
            }
        });

        zDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/down.png"))); // NOI18N
        zDButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        zDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zDButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout zPanelLayout = new javax.swing.GroupLayout(zPanel);
        zPanel.setLayout(zPanelLayout);
        zPanelLayout.setHorizontalGroup(
            zPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(zPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(zPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(zStepSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zStepSizeLabel)
                    .addComponent(zUButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(zPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(zPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(zDButton)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        zPanelLayout.setVerticalGroup(
            zPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(zPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(zUButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(zStepSizeLabel)
                .addGap(2, 2, 2)
                .addComponent(zStepSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(zPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(zPanelLayout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addComponent(zDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(77, Short.MAX_VALUE)))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Autofocus"));

        jLabel1.setText("Autofocus objective: ");

        afObjectiveCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        afObjectiveCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afObjectiveComboActionPerformed(evt);
            }
        });

        afOffsetLabel.setText("Autofocus offset (um):");

        afOffsetField.setText("0");
        afOffsetField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afOffsetFieldActionPerformed(evt);
            }
        });

        afSearchLabel.setText("Autofocus seach range (um): ");

        afSearchField.setText("1000");
        afSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afSearchFieldActionPerformed(evt);
            }
        });

        afNowButton.setText("AF now");
        afNowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afNowButtonActionPerformed(evt);
            }
        });

        afInSequence.setText("AF in sequenced acquisition?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(afInSequence)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(afNowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(afOffsetLabel))
                            .addGap(51, 51, 51)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(afOffsetField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(afObjectiveCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(afSearchLabel)
                            .addGap(18, 18, 18)
                            .addComponent(afSearchField))))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(afObjectiveCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afOffsetLabel)
                    .addComponent(afOffsetField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(afSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(afSearchLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afNowButton)
                    .addComponent(afInSequence))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ZPanelLayout = new javax.swing.GroupLayout(ZPanel);
        ZPanel.setLayout(ZPanelLayout);
        ZPanelLayout.setHorizontalGroup(
            ZPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ZPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(zPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ZPanelLayout.setVerticalGroup(
            ZPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ZPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ZPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        manUscopeCheck.setText("Return microscope controls to chassis?");
        manUscopeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manUscopeCheckActionPerformed(evt);
            }
        });

        manStageCheck.setText("Return stage controls to joystick?");
        manStageCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manStageCheckActionPerformed(evt);
            }
        });

        manFocusCheck.setText("Return focus control only?");
        manFocusCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manFocusCheckActionPerformed(evt);
            }
        });

        keyboardStageCheck.setText("Keyboard stage control?");
        keyboardStageCheck.setEnabled(false);
        keyboardStageCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyboardStageCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manUscopeCheck)
                    .addComponent(manFocusCheck))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(keyboardStageCheck)
                    .addComponent(manStageCheck))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manUscopeCheck)
                    .addComponent(manStageCheck))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manFocusCheck)
                    .addComponent(keyboardStageCheck))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(XYPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ZPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(XYPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(ZPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void stepSizeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepSizeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stepSizeFieldActionPerformed

    private void zStepSizeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zStepSizeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_zStepSizeFieldActionPerformed

    private void afOffsetFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afOffsetFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_afOffsetFieldActionPerformed

    private void wellFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wellFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wellFieldActionPerformed

    private void manStageCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manStageCheckActionPerformed
        xyzmi_.enableManualXYControls(manStageCheck.isSelected());
    }//GEN-LAST:event_manStageCheckActionPerformed

    private void manFocusCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manFocusCheckActionPerformed
        xyzmi_.enableManualZOnly(manFocusCheck.isSelected());
    }//GEN-LAST:event_manFocusCheckActionPerformed

    private void keyboardStageCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyboardStageCheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keyboardStageCheckActionPerformed

    private void zDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zDButtonActionPerformed
        Double step = -1 * Double.parseDouble(zStepSizeField.getText());
        xyzmi_.moveZRelative(step);
    }//GEN-LAST:event_zDButtonActionPerformed

    private void goToWellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToWellButtonActionPerformed
        // TODO: better to edit documentfilter for this field such that lowercase
        // leters are automatically replaced...
        // http://www.java2s.com/Code/Java/Swing-JFC/DocumentFilterthatmapslowercaseletterstouppercase.htm
        String upper = (wellField.getText()).toUpperCase();
        wellField.setText(upper);
        dp_.setCurrentWell(upper);
//        double currentZ = 1000; //TODO get current Z
        double currentZ = xyzmi_.getZAbsolute();
        FOV fov = new FOV(upper, pp_, currentZ);
        xyzmi_.gotoFOV(fov);
    }//GEN-LAST:event_goToWellButtonActionPerformed

    private void zUButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zUButtonActionPerformed
        Double step = Double.parseDouble(zStepSizeField.getText());
        xyzmi_.moveZRelative(step);
    }//GEN-LAST:event_zUButtonActionPerformed

    private void manUscopeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manUscopeCheckActionPerformed
        xyzmi_.enableManualZControls(manUscopeCheck.isSelected());
    }//GEN-LAST:event_manUscopeCheckActionPerformed

    private void afNowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afNowButtonActionPerformed
        // TODO: deal with potential plate slope, c.f. current uManager HCA tool
        xyzmi_.customAutofocus(Double.parseDouble(afOffsetField.getText()));
    }//GEN-LAST:event_afNowButtonActionPerformed

    private void afObjectiveComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afObjectiveComboActionPerformed
        try{
            core_.setProperty("AutoFocusZDC", "ObjectiveTypeSetting", (String) afObjectiveCombo.getSelectedItem());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_afObjectiveComboActionPerformed

    private void afSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afSearchFieldActionPerformed
        try {
            core_.setProperty("AutoFocusZDC", "SearchRange", Double.parseDouble(afSearchField.getText()));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_afSearchFieldActionPerformed

    private void setControlDefaults(){
        
        sap_ = SeqAcqProps.getInstance();
        dp_ = new WellMapDrawPanel(this);
        wellMapPanel.setLayout(new BorderLayout());
        wellMapPanel.add(dp_, BorderLayout.CENTER);
        
        panningButtonPanel.setLayout(new GridLayout(5,5));
//        oneXPanningPanel.setPreferredSize(new Dimension(4*37, 4*37));
        JPanel[][] buttonHolders = new JPanel[5][5];
        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++){
                buttonHolders[x][y] = new JPanel();
                panningButtonPanel.add(buttonHolders[x][y]);
            }
        }
        JButton uButton = new JButton();
        uButton.setPreferredSize(new Dimension(37,37));
        uButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/up.png")));
        uButton.setMargin(new Insets(0,0,0,0));
        uButton.setBorder(null);
        buttonHolders[1][2].add(uButton);
        uButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(Y_AXIS,-1);
            }
            
        });

        JButton dButton = new JButton();
        dButton.setPreferredSize(new Dimension(37,37));
        dButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/down.png")));
        dButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[3][2].add(dButton);
        dButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(Y_AXIS,1);
            }
            
        });
        
        JButton lButton = new JButton();
        lButton.setPreferredSize(new Dimension(37,37));
        lButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/left.png")));
        lButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[2][1].add(lButton);
        lButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(X_AXIS,1);
            }
            
        });
        
        JButton rButton = new JButton();
        rButton.setPreferredSize(new Dimension(37,37));
        rButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/right.png")));
        rButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[2][3].add(rButton);
        rButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(X_AXIS,-1);
            }
            
        });
        
        JButton uuButton = new JButton();
        uuButton.setPreferredSize(new Dimension(37,37));
        uuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/upup.png")));
        uuButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[0][2].add(uuButton);
        uuButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(Y_AXIS,-2);
            }
            
        });
                      
        JButton llButton = new JButton();
        llButton.setPreferredSize(new Dimension(37,37));
        llButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/leftleft.png")));
        llButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[2][0].add(llButton);
        llButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(X_AXIS,2);
            }
            
        });
        
        JButton rrButton = new JButton();
        rrButton.setPreferredSize(new Dimension(37,37));
        rrButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/rightright.png")));
        rrButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[2][4].add(rrButton);
        rrButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(X_AXIS,-2);
            }
            
        });
        
        JButton ddButton = new JButton();
        ddButton.setPreferredSize(new Dimension(37,37));
        ddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/github/dougkelly88/FLIMPlateReaderGUI/Resources/downdown.png")));
        ddButton.setMargin(new Insets(0,0,0,0));
        buttonHolders[4][2].add(ddButton);
        ddButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pan(Y_AXIS,2);
            }
            
        });
        
//        twoXPanningPanel.add(oneXPanningPanel, BorderLayout.CENTER);
        
        afOffsetLabel.setText("Autofocus offset " + um + ":");
        afSearchLabel.setText("Autofocus seach range " + um + ":");
        
        HCAFLIMPluginFrame hfa = (HCAFLIMPluginFrame) parent_;
//        core_ = hfa.getCore();
        core_ = MMStudio.getInstance().getCore();
        
        // SETUP OBJECTIVE/AF PARAMS
        try{
            core_.setProperty("ManualFocus", "NearLimit", 9300);
            core_.setProperty("ManualFocus", "FarLimit", 10);
            core_.setProperty("Objective", "Safe Position", 3000); //TODO: check that this is a good compromise between clearance and speed
            StrVector afObj = core_.getAllowedPropertyValues("AutoFocusZDC", "ObjectiveTypeSetting");
            String[] installedObjStr = (core_.getAllowedPropertyValues("Objective", "Label")).toArray();
            ArrayList<String> installedObj = new ArrayList(Arrays.asList(installedObjStr));
            afObjectiveCombo.removeAllItems();
            for (String str : afObj){
                if (installedObj.contains(str))
                    afObjectiveCombo.addItem(str);
            }
            xyzmi_.moveZAbsolute(3000);
            core_.setProperty("AutoFocusZDC", "SearchRange", Double.parseDouble(afSearchField.getText()));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        

    }
    
    private void pan(int axis, int multiplier){
        Double step = multiplier * Double.parseDouble(stepSizeField.getText());
        if (axis == X_AXIS){
            xyzmi_.moveXYRelative(step,0);
            currentXUm_ = currentXUm_ + step;
            dp_.setCurrentX(currentXUm_);
        }
        else if (axis == Y_AXIS){
            xyzmi_.moveXYRelative(0,step);
            currentYUm_ = currentYUm_ + step;
            dp_.setCurrentY(currentYUm_);
        }
        
    }
    
    public FOV getCurrentFOV(){
        return this.currentFOV_;
    }
    
    public void setCurrentFOV(FOV fov){
        this.currentFOV_ = fov;
    }
    
    public XYZMotionInterface getXYZMotionInterface() {
        return xyzmi_;
    }

    public void setXYZMotionInterface(XYZMotionInterface xyzmi_) {
        this.xyzmi_ = xyzmi_;
    }
    
    public void setParent(Object o){
        parent_ = o;
        
    }
    
    public void onPlateConfigLoaded(boolean enable, PlateProperties pp){
        dp_.setEnabled(enable, pp);
        pp_ = pp;
        goToWellButton.setEnabled(enable);
    }
    
    public Double getSampleAFOffset(){
        return Double.parseDouble(afOffsetField.getText());
    }
    
    public boolean getAFInSequence(){
        return afInSequence.isSelected();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel XYPanel;
    private javax.swing.JPanel ZPanel;
    private javax.swing.JCheckBox afInSequence;
    private javax.swing.JButton afNowButton;
    private javax.swing.JComboBox afObjectiveCombo;
    private javax.swing.JFormattedTextField afOffsetField;
    private javax.swing.JLabel afOffsetLabel;
    private javax.swing.JFormattedTextField afSearchField;
    private javax.swing.JLabel afSearchLabel;
    private javax.swing.JButton goToWellButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JCheckBox keyboardStageCheck;
    private javax.swing.JCheckBox manFocusCheck;
    private javax.swing.JCheckBox manStageCheck;
    private javax.swing.JCheckBox manUscopeCheck;
    private javax.swing.JPanel panningButtonPanel;
    private javax.swing.JPanel panningPanel;
    private javax.swing.JFormattedTextField stepSizeField;
    private javax.swing.JLabel stepSizeLabel;
    private javax.swing.JTextField wellField;
    private javax.swing.JPanel wellMapPanel;
    private javax.swing.JButton zDButton;
    private javax.swing.JPanel zPanel;
    private javax.swing.JFormattedTextField zStepSizeField;
    private javax.swing.JLabel zStepSizeLabel;
    private javax.swing.JButton zUButton;
    // End of variables declaration//GEN-END:variables
}

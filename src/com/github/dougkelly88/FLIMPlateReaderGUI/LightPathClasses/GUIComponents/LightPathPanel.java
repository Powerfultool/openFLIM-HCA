/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.LightPathClasses.GUIComponents;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Arduino;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.ArduinoStepperMotor;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.SeqAcqProps;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Variable;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Objective_object;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Port_object;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.SliderControl;
import com.github.dougkelly88.FLIMPlateReaderGUI.LightPathClasses.Classes.CurrentLightPath;
import com.github.dougkelly88.FLIMPlateReaderGUI.XYZClasses.GUIComponents.XYZPanel;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import mmcorej.CMMCore;
import mmcorej.StrVector;
import org.micromanager.MMStudio;
import org.micromanager.api.events.PropertyChangedEvent;

import com.google.gson.Gson;
import java.nio.file.Files;

import java.nio.file.FileSystems;

/**
 *
 * @author dk1109
 */
public class LightPathPanel extends javax.swing.JPanel {

    MMStudio gui_;
    CMMCore core_;
    PropertyChangedEvent event_;
    private SeqAcqProps sap_;
    private Variable var_;
    HCAFLIMPluginFrame parent_;
    SliderControl powerSlider_;
    SliderControl continuouseFW_;
    CurrentLightPath currentLightPath_;
    private Arduino arduino_;
    private ArduinoStepperMotor arduinoSM_;
    private XYZPanel xYZPanel_;
    private static final LightPathPanel fINSTANCE =  new LightPathPanel();
    private String [] [] ObjectiveOffsetInfo;
    private String [] [] PortOffsetInfo;
    public boolean ObjOffsetsloaded;
    public boolean PortOffsetsloaded;
    double [] offsets = {0,0,0};
    
    // TODO: replace var_ stuff with currentLightPath_
//    private SequencedAcquisitionProperties sap_;
    // TODO: generate a method that checks for spectral overlap between
    // filters based on central wavelength and bandpass. This method would 
    // return a false if there was (significant?) spectral overlap between
    // excitation and emission, risking overloading the HRI, and would 
    // be used to prevent such a filter change occuring without the shutter
    // first being closed. 

    /**
     * Creates new form FLIMControls
     */
    public LightPathPanel() {
        initComponents();
        gui_ = MMStudio.getInstance();
        sap_ = SeqAcqProps.getInstance();
        var_ = Variable.getInstance();
        arduino_ = Arduino.getInstance();
        arduinoSM_ = ArduinoStepperMotor.getInstance();
        xYZPanel_ = XYZPanel.getInstance();
        ObjOffsetsloaded = false;
        PortOffsetsloaded = false;
        //Gson gson = new GsonBuilder().create();
        
        try {
            gui_.registerForEvents(this);
            core_ = gui_.getCore();
        } catch (Exception e) {
            //gui_.showMessage("Error in FLIMPanel constructor: " + e.getMessage());
        }
        currentLightPath_ = new CurrentLightPath();
        setControlDefaults();
    }
    
    private String [] interpretObjectiveInfo(String info){
        return info.split("##");
    }
    
    public double [] returnoffsets(){
        offsets[0] = Double.parseDouble(this.xOffset.getText());
        offsets[1] = Double.parseDouble(this.yOffset.getText());
        return offsets;
    }
    
    public void LoadJSONObjOffsets(){
        // Show a warning if the objectives loaded don't match the ones stored in Micro-manager?
        int num_props = 4; //Name, Xoff, Yoff, Zoff
//        ObjectiveOffsetInfo = new String [objectiveComboBox.getItemCount()] [num_props];
        
        Gson gson = new Gson();
        //Load the file with the stored offsets
        String directoryName = ij.IJ.getDirectory("ImageJ");
        String objFilepath = directoryName.concat("OPENFLIMHCA_JSONObjectiveOffsets.txt");
        String JSONInString = "";
        try{
            JSONInString = new String(Files.readAllBytes(FileSystems.getDefault().getPath(objFilepath)));
        } catch (Exception e) {
            JSONInString = "FAIL";
        }
//        Objective_object[] objs = gson.fromJson(JSONInString, Objective_object[].class);
//        for(int i=0;i<objs.length;i++){
//                ObjectiveOffsetInfo[i] [0] = objs[i].getObjectiveName();
//                ObjectiveOffsetInfo[i] [1] = objs[i].getObjectiveOffsets()[0].toString();
//                ObjectiveOffsetInfo[i] [2] = objs[i].getObjectiveOffsets()[1].toString();
//                ObjectiveOffsetInfo[i] [3] = objs[i].getObjectiveOffsets()[2].toString();
//                System.out.println(ObjectiveOffsetInfo[i]);
//        }
        String mismatchedObj = "";
        String explanationstring = "The following objective(s) listed in micro-manager offsets are not matched to the objectives listed in the saved Objective Offsets file - please fix this:\n\n";
//        for(int i=0;i<objectiveComboBox.getItemCount();i++){
//            if (ObjectiveOffsetInfo[i] [0].equals(objectiveComboBox.getItemAt(i).toString())){
//                mismatchedObj=mismatchedObj.concat("0");
//            } else {
//                mismatchedObj=mismatchedObj.concat("1");
//                explanationstring=explanationstring.concat(objectiveComboBox.getItemAt(i).toString()+" is stored in the file as "+ObjectiveOffsetInfo[i] [0]+"\n");
//            }
//        }
        if(Integer.parseInt((String)mismatchedObj)>0){
            // if we have a case where an objective name doesn't match the file...
            System.out.println(explanationstring);
        }
    }    
    
    public void getConfiguredCameras(){
        StrVector vals = new StrVector();
        try {
            vals = core_.getLoadedDevicesOfType(mmcorej.DeviceType.CameraDevice);
            camChooser.removeAllItems();
            for (String str : vals) {
                //System.out.println(str);
                camChooser.addItem(str);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void LoadJSONPortOffsets(){
        // Show a warning if the objectives loaded don't match the ones stored in Micro-manager?
        int num_props = 4; //Name, Xoff, Yoff, Zoff
        //PortOffsetInfo = new String [switchPortComboBox.getItemCount()] [num_props];
        
        Gson gson = new Gson();
        //Load the file with the stored offsets
        String directoryName = ij.IJ.getDirectory("ImageJ");
        String objFilepath = directoryName.concat("OPENFLIMHCA_JSONPortOffsets.txt");
        String JSONInString = "";
        try{
            JSONInString = new String(Files.readAllBytes(FileSystems.getDefault().getPath(objFilepath)));
        } catch (Exception e) {
            
        }
        Port_object[] ports = gson.fromJson(JSONInString, Port_object[].class);
        for(int i=0;i<ports.length;i++){
                PortOffsetInfo[i] [0] = ports[i].getPortName();
                PortOffsetInfo[i] [1] = ports[i].getPortOffsets()[0].toString();
                PortOffsetInfo[i] [2] = ports[i].getPortOffsets()[1].toString();
                PortOffsetInfo[i] [3] = ports[i].getPortOffsets()[2].toString();
        }
        String mismatchedPort = "";
        String explanationstring = "The following objective(s) listed in micro-manager offsets are not matched to the ports listed in the saved Port Offsets file - please fix this:\n\n";
//        for(int i=0;i<switchPortComboBox.getItemCount();i++){
//            if (PortOffsetInfo[i] [0].equals(switchPortComboBox.getItemAt(i).toString())){
//                mismatchedPort=mismatchedPort.concat("0");
//            } else {
//                mismatchedPort=mismatchedPort.concat("1");
//                explanationstring=explanationstring.concat(switchPortComboBox.getItemAt(i).toString()+" is stored in the file as: "+PortOffsetInfo[i] [0]+"\n");
//            }
//        }
        if(Integer.parseInt((String)mismatchedPort)>0){
            // if we have a case where an objective name doesn't match the file...
            System.out.println(explanationstring);
        }
    }    
    
//    public void LoadObjectiveOffsets(){
//        // Show a warning if the objectives loaded don't match the ones stored in Micro-manager?
//        int num_props = 4; //Name, Xoff, Yoff, Zoff
//        ObjectiveOffsetInfo = new String [objectiveComboBox.getItemCount()] [num_props];
//        //Load the file with the stored offsets
//        String directoryName = ij.IJ.getDirectory("ImageJ");
//        String objFilepath = directoryName.concat("OPENFLIMHCA_ObjectiveOffsets.txt");
//        //System.out.println(objFilepath);
//        //Compare the loaded data with the names of the objectives stored in ImageJ
//        try{
//            File objfile = new File (objFilepath);
//            FileReader fileReader = new FileReader(objfile);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            StringBuffer stringBuffer = new StringBuffer();
//            String line;
//            int i=0;
//            while ((line = bufferedReader.readLine()) != null) {
//                String thisObjectiveInfo = line;
//                for(int j=0;j<num_props;j++){
//                    String [] interpretedObjectiveInfo = interpretObjectiveInfo(thisObjectiveInfo);
//                    if(interpretedObjectiveInfo.length == num_props){
//                        ObjectiveOffsetInfo[i] [j] = interpretedObjectiveInfo[j];
//                    } else {
//                        if (j>0){
//                            ObjectiveOffsetInfo[i] [j] = "0";
//                        } else {
//                            ObjectiveOffsetInfo[i] [j] = "Unknown";
//                        }
//                    }
//                }
//                i++;
//            }            
//        } catch (IOException e) {
//            e.printStackTrace();
//	}
//        
//        String mismatchedObj = "";
//        String explanationstring = "The following objective(s) listed in micro-manager offsets are not matched to the objectives listed in the saved Objective Offsets file - please fix this:\n\n";
//        for(int i=0;i<objectiveComboBox.getItemCount();i++){
//            if (ObjectiveOffsetInfo[i] [0].equals(objectiveComboBox.getItemAt(i).toString())){
//                mismatchedObj=mismatchedObj.concat("0");
//            } else {
//                mismatchedObj=mismatchedObj.concat("1");
//                explanationstring=explanationstring.concat(objectiveComboBox.getItemAt(i).toString()+"\n");
//            }
//        }
//               
//        if(Integer.parseInt((String)mismatchedObj)>0){
//            // if we have a case where an objective name doesn't match the file...
//            System.out.println(explanationstring);
//        }
//        //Offsetsloaded=true;
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        excitationSource = new javax.swing.JPanel();
        laserToggle = new javax.swing.JToggleButton();
        outputPowerPanel = new javax.swing.JPanel();
        laserRepRateLabel = new javax.swing.JLabel();
        laserTemperatureLabel = new javax.swing.JLabel();
        laserSerialNumberLabel = new javax.swing.JLabel();
        laserRunTimeLabel = new javax.swing.JLabel();
        ledToggle = new javax.swing.JToggleButton();
        Camera = new javax.swing.JPanel();
        cameraPixelSize = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        camChooser = new javax.swing.JComboBox<>();
        ContinusouseFilterWheel = new javax.swing.JPanel();
        continuouseFWPanel = new javax.swing.JPanel();
        wakeButton = new javax.swing.JButton();
        sleepButton = new javax.swing.JButton();
        stepLeftButton = new javax.swing.JButton();
        steprightButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        xOffset = new javax.swing.JTextField();
        yOffset = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        excitationSource.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Excitation source"));

        laserToggle.setText("Turn laser ON");
        laserToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laserToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout outputPowerPanelLayout = new javax.swing.GroupLayout(outputPowerPanel);
        outputPowerPanel.setLayout(outputPowerPanelLayout);
        outputPowerPanelLayout.setHorizontalGroup(
            outputPowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        outputPowerPanelLayout.setVerticalGroup(
            outputPowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );

        laserRepRateLabel.setText("Laser repetition rate (MHz):");

        laserTemperatureLabel.setText("Laser temperature (oC): ");

        laserSerialNumberLabel.setText("Laser serial number:");

        laserRunTimeLabel.setText("Laser run time (mins):");

        ledToggle.setText("Turn brightfield light ON");
        ledToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ledToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout excitationSourceLayout = new javax.swing.GroupLayout(excitationSource);
        excitationSource.setLayout(excitationSourceLayout);
        excitationSourceLayout.setHorizontalGroup(
            excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(excitationSourceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ledToggle, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(laserToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPowerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(laserTemperatureLabel)
                    .addComponent(laserRepRateLabel)
                    .addComponent(laserSerialNumberLabel)
                    .addComponent(laserRunTimeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        excitationSourceLayout.setVerticalGroup(
            excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(excitationSourceLayout.createSequentialGroup()
                .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(excitationSourceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(excitationSourceLayout.createSequentialGroup()
                                .addComponent(laserRepRateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(laserTemperatureLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(laserSerialNumberLabel))
                            .addComponent(outputPowerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(laserRunTimeLabel))
                    .addGroup(excitationSourceLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(laserToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ledToggle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Camera.setBorder(javax.swing.BorderFactory.createTitledBorder("Camera"));

        cameraPixelSize.setText("6.45");
        cameraPixelSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraPixelSizeActionPerformed(evt);
            }
        });

        jLabel5.setText("Camera pixel size");

        jLabel8.setText("um");

        camChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        camChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CameraLayout = new javax.swing.GroupLayout(Camera);
        Camera.setLayout(CameraLayout);
        CameraLayout.setHorizontalGroup(
            CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(camChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(CameraLayout.createSequentialGroup()
                        .addComponent(cameraPixelSize, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        CameraLayout.setVerticalGroup(
            CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraPixelSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(camChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        ContinusouseFilterWheel.setBorder(javax.swing.BorderFactory.createTitledBorder("Continuouse Filter Wheel"));
        ContinusouseFilterWheel.setEnabled(false);

        continuouseFWPanel.setEnabled(false);

        javax.swing.GroupLayout continuouseFWPanelLayout = new javax.swing.GroupLayout(continuouseFWPanel);
        continuouseFWPanel.setLayout(continuouseFWPanelLayout);
        continuouseFWPanelLayout.setHorizontalGroup(
            continuouseFWPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        continuouseFWPanelLayout.setVerticalGroup(
            continuouseFWPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        wakeButton.setText("wake");
        wakeButton.setEnabled(false);
        wakeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wakeButtonActionPerformed(evt);
            }
        });

        sleepButton.setText("sleep");
        sleepButton.setEnabled(false);
        sleepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sleepButtonActionPerformed(evt);
            }
        });

        stepLeftButton.setText("stepLeftt");
        stepLeftButton.setEnabled(false);
        stepLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepLeftButtonActionPerformed(evt);
            }
        });

        steprightButton.setText("stepRight");
        steprightButton.setEnabled(false);
        steprightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                steprightButtonActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ContinusouseFilterWheelLayout = new javax.swing.GroupLayout(ContinusouseFilterWheel);
        ContinusouseFilterWheel.setLayout(ContinusouseFilterWheelLayout);
        ContinusouseFilterWheelLayout.setHorizontalGroup(
            ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContinusouseFilterWheelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContinusouseFilterWheelLayout.createSequentialGroup()
                        .addComponent(continuouseFWPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(steprightButton))
                    .addGroup(ContinusouseFilterWheelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(stepLeftButton)))
                .addGap(18, 18, 18)
                .addGroup(ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContinusouseFilterWheelLayout.createSequentialGroup()
                        .addGroup(ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wakeButton)
                            .addComponent(sleepButton))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContinusouseFilterWheelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        ContinusouseFilterWheelLayout.setVerticalGroup(
            ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContinusouseFilterWheelLayout.createSequentialGroup()
                .addComponent(wakeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sleepButton)
                    .addComponent(stepLeftButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContinusouseFilterWheelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(steprightButton)
                    .addComponent(jButton1))
                .addGap(0, 21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContinusouseFilterWheelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(continuouseFWPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        xOffset.setText("-3000");
        xOffset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xOffsetActionPerformed(evt);
            }
        });

        yOffset.setText("1000");
        yOffset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yOffsetActionPerformed(evt);
            }
        });

        jLabel1.setText("x offset");

        jLabel2.setText("y offset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(excitationSource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Camera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ContinusouseFilterWheel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(xOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(yOffset, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(excitationSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101)
                .addComponent(Camera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContinusouseFilterWheel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void laserToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laserToggleActionPerformed
            
        if (laserToggle.isSelected()) {
            laserToggle.setText("Turn laser OFF");
            try{
                boolean abort=arduino_.checkSafety();
                
                if (abort==false){
                    arduino_.setMode("shutter");
                    arduino_.setDigitalOutHigh();
                }
                } catch (Exception ex) {
            Logger.getLogger(HCAFLIMPluginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
//            if(switchPortComboBox.getSelectedIndex()==0){
//                setBy(switchPortComboBox, "LightPathPrism", "Side Port");
//                switchPortComboBox.setSelectedItem("Side Port");
//                currentLightPath_.setPortLabel((String) switchPortComboBox.getSelectedItem());
//                var_.SwitchPortComboBoxSelectedItem = (String) switchPortComboBox.getSelectedItem();
//                }
        } else {
            laserToggle.setText("Turn laser ON");
            
            try {
                arduino_.setDigitalOutLow();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        }

    }//GEN-LAST:event_laserToggleActionPerformed

//    public void setMag(String magString){
//        
//        int indX=0;
//        if(magString.contains("x")){
//            indX=magString.indexOf("x");
//        }else if(magString.contains("X")){
//            indX=magString.indexOf("X");
//        }else{
//            System.out.println("Is objective empty? Yes-> everything is fine. No-> cannot identify objective label in hardware configuration file. Make sure there is only one x included"
//                        + " and the magnification of the objective is in front of the x without a spacer. For example '40x oil' or similar.");
//        }
//        String newString=null;
//        newString= magString.substring(0,indX);
//        int indXX=0;
//        try{
//            var_.magnification=Double.parseDouble(magString.substring(0,indX));
//        }catch(Exception ex){
//            if(newString.contains(" ")){
//                indXX=newString.indexOf(" ");
//            }else if(magString.contains("XO")){
//                indXX=newString.indexOf("o")+1;
//            }else{
//            System.out.println("Is objective empty? Yes-> everything is fine. No-> cannot identify objective label in hardware configuration file. Make sure there is only one x included"
//                        + " and the magnification of the objective is in front of the x without a spacer. For example '40x oil' or similar.");
//            }
//            try{
//            var_.magnification=Double.parseDouble(magString.substring(indXX,indX));
//            }catch(Exception exx){
//                warningMessage("Cannot identify objective label in hardware configuration file. Make sure there is only one x included"
//                        + " and the magnification of the objective is in front of the x without a spacer. For example '40x oil' or similar.");
//            }
//        }
//        
//        System.out.println("Magnification changed to "+var_.magnification);
//    }
    
    public void setMag(String magString){
        String xtype="";
        boolean Empty=false;
        if(magString.indexOf("x")>=0){
            xtype="x";
        } else if (magString.indexOf("X")>=0){
            xtype="X";
        } else {
            System.out.println("Is objective empty? Yes-> everything is fine. No-> cannot identify objective label in hardware configuration file. Make sure there is only one x included"
                        + " and the magnification of the objective is in front of the x without a spacer. For example '40x oil' or similar.");            
            Empty=true;
        }
        //System.out.println("Hello World");
        if(Empty){
            //var_.magnification=0;
            System.out.println("NO OBJECTIVE FOUND!");
        } else {
            String[] parts = magString.split(xtype);
            String partbefore = parts[0];
            String partafter="";
            if(parts.length>1){
                partafter = parts[1];
            } else {
                partafter ="Air";
            }
            //System.out.println(xtype);
            //System.out.println(partbefore);
            char[] Beforearray = partbefore.toCharArray();
            String magvalue="";
            // Numbers and decimal point
            String matchstring="[0-9.]";
            String str="";
            for(char ch : Beforearray){
                str=Character.toString(ch);
                if(str.matches(matchstring)){
                    magvalue=magvalue.concat(str);
                }
            }
            System.out.println("Magnification: "+magvalue);
            String immersionfluid="";
            if(partafter.equals("O")||partafter.equals("Oil")){
                immersionfluid="Oil";
            } else if(partafter.equals("W")||partafter.equals("Water")){
                immersionfluid="Water";
            } else if(partafter.equals("G")||partafter.equals("Glycerol")){
                immersionfluid="Glycerol";
            } else {
                immersionfluid.equals("Air");
            }
            System.out.println("Immersion fluid: "+immersionfluid);

            try {
                var_.magnification=Double.parseDouble(magvalue);
            } catch(Exception exx) {
                    warningMessage("Cannot identify objective label in hardware configuration file. Make sure there is only one x included"
                            + " and the magnification of the objective is in front of the x without a spacer. For example '40x oil' or similar.");
                }
            System.out.println("Magnification changed to "+var_.magnification);
        }
    }
    
    private double getMag(String desc) {
        // TODO: CHECK THIS WORKS!
        // TODO: make this more general/robust...
        // assumes no "x"s other than as magnification...
        double mag = 0;
        double multiplier = 1;
        int index = desc.indexOf("x") - 1;
        if (index > 0) {
            boolean isNum = isNumeric(Character.toString(desc.charAt(index)));
            while (isNum) {
                mag += multiplier * Double.parseDouble(Character.toString(desc.charAt(index)));
                index--;
                multiplier = multiplier * 10;
            }
        }
        return mag;
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        // ref: http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-a-numeric-type-in-java
    }

    private void ledToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ledToggleActionPerformed
        
        if (ledToggle.isSelected()) {
            try {
//                core_.setProperty("LightPathPrism", "Label", (String) switchPortComboBox.getSelectedItem());
            } catch (Exception ex) {
                System.out.println("Not setting property for device LightPathPrism because combo hasn't yet been populated (setByLabel method)");
            }
            arduino_.setMode("led");
            ledToggle.setText("Turn brightfield light OFF");
            try{
                boolean abort=arduino_.checkSafety();
                if (abort==false){
                    arduino_.setDigitalOutHigh();
                }
                } catch (Exception ex) {
            Logger.getLogger(HCAFLIMPluginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else { 
            arduino_.setMode("shutter");
            ledToggle.setText("Turn brightfield light ON");
            try {
                boolean abort=arduino_.checkSafety();
                arduino_.setDigitalOutLow();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }   
       
    }//GEN-LAST:event_ledToggleActionPerformed

    private void setCameraPath(double camerapixelsize, boolean FLIM){
            var_.camerapixelsize = 6.45;
            this.cameraPixelSize.setText(Double.toString(var_.camerapixelsize));
            if(FLIM){
                parent_.setRelayMag(parent_.getRelayMag());
                var_.relay=parent_.getRelayMag();
            } else {
                // No extra magnification from relay
                var_.relay=1;
            }
    }
    
    private void wakeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wakeButtonActionPerformed
        arduinoSM_.wake();
    }//GEN-LAST:event_wakeButtonActionPerformed

    private void sleepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sleepButtonActionPerformed
       arduinoSM_.sleep();
    }//GEN-LAST:event_sleepButtonActionPerformed

    private void stepLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepLeftButtonActionPerformed
        arduinoSM_.stepLeft();
    }//GEN-LAST:event_stepLeftButtonActionPerformed

    private void steprightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_steprightButtonActionPerformed
        arduinoSM_.turnLeft();
    }//GEN-LAST:event_steprightButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            core_.setProperty("SyringePump", "Liquid Dispersion?", "Go");
        } catch (Exception ex) {
            System.out.println("noope");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cameraPixelSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraPixelSizeActionPerformed
        // TODO add your handling code here:
        // Fire off something to reassess actual pixel size?
    }//GEN-LAST:event_cameraPixelSizeActionPerformed

    private void camChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_camChooserActionPerformed
        try {
            if (gui_.isLiveModeOn()){
                gui_.enableLiveMode(false);
                core_.setCameraDevice(camChooser.getSelectedItem().toString());
                gui_.enableLiveMode(true);
            } else{
                core_.setCameraDevice(camChooser.getSelectedItem().toString());
            }
            var_.camerapixelsize = 6.45;
            this.cameraPixelSize.setText(Double.toString(var_.camerapixelsize));
        } catch (Exception ex) {
            System.out.println("Error: Couldn't set default camera");
        }
        gui_.refreshGUI();
    }//GEN-LAST:event_camChooserActionPerformed

    private void xOffsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xOffsetActionPerformed
        parent_.xyzmi_.calculateOffsets();
    }//GEN-LAST:event_xOffsetActionPerformed

    private void yOffsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yOffsetActionPerformed
        parent_.xyzmi_.calculateOffsets();
    }//GEN-LAST:event_yOffsetActionPerformed

    public void setByLabel(JComboBox combo, String device) {
        try {
            String setval = (String) combo.getSelectedItem();
            // only send command if combo has been properly populated
            if (setval != null) {
                core_.setProperty(device, "Label", setval);
            } else {
                System.out.println("Not setting property for device " + device
                        + " because combo hasn't yet been populated (setByLabel method)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Might want to revert objective offsets etc on this failing?
        }

    }
    
    public double getCameraPixelsize(){
        return Double.parseDouble(cameraPixelSize.getText());
    }
    
    public void setBy(JComboBox combo, String device, String port) {
        try {
            core_.setProperty(device, "Label", port);
        } catch (Exception ex) {
            System.out.println("Error: LightPathPanel->setBy:");
            System.out.println("Couldn't set property of"+ device);
        }
    }

    public void setLoadedSoftwareValues() {
        // searching label of prperty in SoftwareConfig and set values in LightPathControls
//        dichroicComboBox.setSelectedItem(var_.findLabelOfProperty("Dichroic"));
//        emissionComboBox.setSelectedItem(var_.findLabelOfProperty("Emission"));
//        ndFWComboBox.setSelectedItem(var_.findLabelOfProperty("Neutral Density Filter"));
//        excitationComboBox.setSelectedItem(var_.findLabelOfProperty("Excitation"));
//        objectiveComboBox.setSelectedItem(var_.findLabelOfProperty("Objective"));
//        filterCubeComboBox.setSelectedItem(var_.findLabelOfProperty("Filter Cube"));
//        switchPortComboBox.setSelectedItem(var_.findLabelOfProperty("Light Path Prism"));
    }

    public void setLoadedHardwareValues() {
        //Intensity (NDFW) Load
//        populateComboBoxes(ndFWComboBox, "NDFW");

        //Excitation (SpectralFW) Load
//        populateComboBoxes(excitationComboBox, "SpectralFW");

        //Dichroic Load
//        populateComboBoxes(dichroicComboBox, "CSUX-Dichroic Mirror");

        //Emission Load
//        populateComboBoxes(emissionComboBox, "CSUX-Filter Wheel");

        //FilterCube Load
//        populateComboBoxes(filterCubeComboBox, "FilterCube");

        //Objective Load
//        populateComboBoxes(objectiveComboBox, "Objective");
        //Then load the offset values
        //LoadJSONObjOffsets();
        
        //SwitchPort Load
//        populateComboBoxes(switchPortComboBox, "LightPathPrism");
        //LoadJSONPortOffsets();
        
        //GET CAMERAS
        getConfiguredCameras();
        
        // set defaults
        setDefaultLightPath();

        // if Fianium present, load up these controls:
        try {
            String sn = core_.getProperty("FianiumSC", "LaserSerialNumber");
            String temp = "TBA";    // TODO: implement temperature monitor in DeviceAdapter!
            String rr = core_.getProperty("FianiumSC", "RepRate");
            String ot = core_.getProperty("FianiumSC", "OperatingTime(Mins)");
            laserSerialNumberLabel.setText("Laser serial number: " + sn);
            laserRepRateLabel.setText("Laser repetition rate: " + rr + " MHz");
            laserRunTimeLabel.setText("Laser run time: " + ot + " mins");
            laserTemperatureLabel.setText("Laser temperature: " + temp + "\u00b0C");

            powerSlider_ = new SliderControl("Laser output power (%): ", 0, 100, 0);
            outputPowerPanel.setLayout(new BorderLayout());
            outputPowerPanel.add(powerSlider_, BorderLayout.SOUTH);
            powerSlider_.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    try {
                        if (laserToggle.isSelected()) {
                            int pcoutput = (int) powerSlider_.getValue().intValue();
                            core_.setProperty("FianiumSC", "Power output (%)", pcoutput);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });

            outputPowerPanel.revalidate();
            outputPowerPanel.repaint();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setDefaultLightPath() {
        // make configurable? for now, use indices
        // rather than labels to avoid breaking things...
        // REMEMBER THAT COMBO BOXES END UP ORDERED!
//        if (dichroicComboBox.isEnabled()) {
//            dichroicComboBox.setSelectedIndex(1);
//        }
//        if (emissionComboBox.isEnabled()) {
//            emissionComboBox.setSelectedIndex(2);
//        }
//        if (ndFWComboBox.isEnabled()) {
//            ndFWComboBox.setSelectedIndex(5);
//        }
//        if (excitationComboBox.isEnabled()) {
//            excitationComboBox.setSelectedIndex(1);
//        }
//        if (objectiveComboBox.isEnabled()) {
//            objectiveComboBox.setSelectedIndex(1);
//        }
//        if (filterCubeComboBox.isEnabled()) {
//            filterCubeComboBox.setSelectedIndex(1);
//        }
//        if (switchPortComboBox.isEnabled()) {
//            switchPortComboBox.setSelectedIndex(1);
//        }
        
    }

    private void populateComboBoxes(JComboBox combo, String device) {
        StrVector vals = new StrVector();
        try {
            vals = core_.getAllowedPropertyValues(device, "Label");
            System.out.println("Combo = " + combo);
            System.out.println("Item = " + (String) combo.getSelectedItem());

            combo.removeAllItems();
            for (String str : vals) {
                combo.addItem(str);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // if the combo can't be populated, chances are
            // the device isn't included in the .cfg, so disable 
            // the control. 
            // TODO: check whether this works if the device is in the .cfg, but
            // is not currently attached/accessible. 
            combo.setEnabled(false);
        }
    }

    public void setParent(Object o) {
        parent_ = (HCAFLIMPluginFrame) o;
    }
    
    public double[] getObjectiveOffsets(){
        return currentLightPath_.getObjectiveOffsets();
    }
    
    public void setObjectiveOffsets(double[] newOffsets){
        currentLightPath_.setObjectiveOffsets(newOffsets);
    }    
    
    public double[] getPortOffsets(){
        return currentLightPath_.getPortOffsets();
    }
    
    public void setPortOffsets(double[] newOffsets){
        currentLightPath_.setPortOffsets(newOffsets);
    }    
    
    public Object getFrameParent(){
        return parent_;
    }

    public CurrentLightPath getCurrentLightPath() {
        return this.currentLightPath_;
    }
    
    public static LightPathPanel getInstance() {
            return fINSTANCE;
    }
    
    public void updatePanel(){
         // do something when LightPathPanel is selected
//        objectiveComboBox.setSelectedItem(var_.ObjectiveComboBoxSelectedItem);
    }
    
    public void setLaserToggleFalse(){
        laserToggle.setSelected(false);
    }
    
    public void setLaserToggleText(String text){
        laserToggle.setText(text);
    }
    
    public void setControlDefaults(){
        var_.smPositionOld=0;
        continuouseFW_ = new SliderControl("Laser intensity [%]",0,100,0);
        continuouseFWPanel.setLayout(new BorderLayout());
        continuouseFWPanel.add(continuouseFW_, BorderLayout.SOUTH);
        continuouseFW_.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                int currentVal=continuouseFW_.getValue().intValue();
                continuouseFW_.setValue(currentVal);
                
                
            }
        });
    }
    
    public void warningMessage(String news){
        JOptionPane optionPane = new JOptionPane(news,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Camera;
    private javax.swing.JPanel ContinusouseFilterWheel;
    private javax.swing.JComboBox<String> camChooser;
    private javax.swing.JTextField cameraPixelSize;
    private javax.swing.JPanel continuouseFWPanel;
    private javax.swing.JPanel excitationSource;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JLabel laserRepRateLabel;
    private javax.swing.JLabel laserRunTimeLabel;
    private javax.swing.JLabel laserSerialNumberLabel;
    private javax.swing.JLabel laserTemperatureLabel;
    private javax.swing.JToggleButton laserToggle;
    private javax.swing.JToggleButton ledToggle;
    private javax.swing.JPanel outputPowerPanel;
    private javax.swing.JButton sleepButton;
    private javax.swing.JButton stepLeftButton;
    private javax.swing.JButton steprightButton;
    private javax.swing.JButton wakeButton;
    private javax.swing.JTextField xOffset;
    private javax.swing.JTextField yOffset;
    // End of variables declaration//GEN-END:variables

}

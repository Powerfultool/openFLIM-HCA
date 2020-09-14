/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.XYZClasses.Classes;

/**
 *
 * @author Fogim
 */
public class name_xyz {
    String name;
    double x_pos_ss;
    double y_pos_ss;
    double z_pos_ss;

    public name_xyz(){
        name = null;
        x_pos_ss = 0.0;
        y_pos_ss = 0.0;
        z_pos_ss = 0.0;
    }
    
    public name_xyz(String name_passed, double xpos, double ypos, double zpos){
        name = name_passed;
        x_pos_ss = xpos;
        y_pos_ss = ypos;
        z_pos_ss = zpos;
    }    
    
    public void set_name(String name){
        name = name;
    }
    public String get_name(){
        return name;
    }    

    public void set_x(double new_x){
        x_pos_ss = new_x;
    }
    public double get_x(){
        return x_pos_ss;
    }

    public void set_y(double new_y){
        y_pos_ss = new_y;
    }
    public double get_y(){
        return y_pos_ss;
    }
    
    public void set_z(double new_z){
        z_pos_ss = new_z;
    }    
    public double get_z(){
        return z_pos_ss;
    }    
}
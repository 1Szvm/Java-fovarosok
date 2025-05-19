package com.example.fovarosokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {
    @FXML ListView lsOrszagok;
    @FXML TextField infFovaros;
    @FXML TextField infFovarosLakossag;

    private FileChooser fc = new FileChooser();

    public void initialize(){
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Fájlok","*.csv"));
    }

    @FXML private void onMegnyitasClick(){
        File fbe=fc.showOpenDialog(lsOrszagok.getScene().getWindow());
        if(fbe!=null){
            varosok.clear();
            lsOrszagok.getItems().clear();
            betolt(fbe);
            for (Varos v: varosok){
                lsOrszagok.getItems().add(String.format("%s (%,d fő): %s)",v.oraszag,v.lakossag,v.rovidetes).replace(","," "));
            }
        }
    }

    @FXML private void renderInfo(){
        infFovaros.setText(varosok.get(lsOrszagok.getSelectionModel().getSelectedIndex()).fovaros);
        infFovarosLakossag.setText(String.format("%,d fö",varosok.get(lsOrszagok.getSelectionModel().getSelectedIndex()).fovaroslakossag).replace(","," "));

    }

    private void betolt(File Fajl){
        Scanner be = null;
        try{
            be= new Scanner(Fajl,"utf-8");
            be.nextLine();
            while (be.hasNextLine()){
                varosok.add(new Varos(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public class Varos{
        String oraszag;
        String rovidetes;
        Integer lakossag;
        String fovaros;
        Integer fovaroslakossag;

        public Varos(String sor){
            String[] s =sor.split(";");
            oraszag=s[0];
            rovidetes=s[1];
            lakossag=Integer.parseInt(s[2]);
            fovaros=s[3];
            fovaroslakossag= Integer.parseInt(s[4]);
        }
    }

    public ArrayList<Varos> varosok = new ArrayList<>();


    @FXML private void onKilépesClick(){
        Platform.exit();
    }

    @FXML private void onNevjegyzekClikc(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Fővárosok v1.0.0\n(C) Kandó");
        info.showAndWait();
    }
}
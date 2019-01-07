package sth.lista8;

import android.content.res.Resources;

public class PPM {

    //true for harris-benedict, false for mifflin
    private boolean whichOne = true;
    private double mass;
    private double height;
    private String gender;
    private double age;

    public PPM() { }

    void update(double mass, double height, String gender, double age, boolean whichOne){
        this.mass = mass;
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.whichOne = whichOne;
    }

    double calculatePPM(){
        return whichOne ? benedictHarris() : mifflin();
    }

    private double mifflin() {
        return gender.equals("kobieta") ? (655.1 + 9.563*mass + 1.85*height - 4.676*age) :
                (66.5 + 13.75*mass + 5.003*height - 6.775*age);
    }

    private double benedictHarris() {
        double result = gender.equals("kobieta") ? -161 : 5;
        return 10*mass + 6.25*height - 5*age + result;
    }


}

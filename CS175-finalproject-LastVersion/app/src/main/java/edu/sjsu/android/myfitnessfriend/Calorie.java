package edu.sjsu.android.myfitnessfriend;

public class Calorie {

    private int nCalories;
    private int nTotalFat;
    private int nTransFat;
    private int nProtein;
    private int nCholesterol;
    private int nFiber;
    private int nSodium;

    public Calorie(int nCalories, int nTotalFat, int nTransFat, int nProtein, int nCholesterol, int nFiber, int nSodium) {
        this.nCalories = nCalories;
        this.nTotalFat = nTotalFat;
        this.nTransFat = nTransFat;
        this.nProtein = nProtein;
        this.nCholesterol = nCholesterol;
        this.nFiber = nFiber;
        this.nSodium = nSodium;
    }

    public Calorie() {
    }

    public int getnCalories() {
        return nCalories;
    }

    public void setnCalories(int nCalories) {
        this.nCalories = nCalories;
    }

    public int getnTotalFat() {
        return nTotalFat;
    }

    public void setnTotalFat(int nTotalFat) {
        this.nTotalFat = nTotalFat;
    }

    public int getnTransFat() {
        return nTransFat;
    }

    public void setnTransFat(int nTransFat) {
        this.nTransFat = nTransFat;
    }

    public int getnProtein() {
        return nProtein;
    }

    public void setnProtein(int nProtein) {
        this.nProtein = nProtein;
    }

    public int getnCholesterol() {
        return nCholesterol;
    }

    public void setnCholesterol(int nCholesterol) {
        this.nCholesterol = nCholesterol;
    }

    public int getnFiber() {
        return nFiber;
    }

    public void setnFiber(int nFiber) {
        this.nFiber = nFiber;
    }

    public int getnSodium() {
        return nSodium;
    }

    public void setnSodium(int nSodium) {
        this.nSodium = nSodium;
    }
}

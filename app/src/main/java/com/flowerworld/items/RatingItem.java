package com.flowerworld.items;

public class RatingItem {
    private int one = 0;
    private int two = 0;
    private int tree = 0;
    private int four = 0;
    private int five = 0;
    private double general = 0;

    public void setOne(int one) {
        this.one = one;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public void setTree(int tree) {
        this.tree = tree;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public void setGeneral(double general) {
        this.general = general;
    }

    public int getOne() {
        return one;
    }

    public int getTwo() {
        return two;
    }

    public int getTree() {
        return tree;
    }

    public int getFour() {
        return four;
    }

    public int getFive() {
        return five;
    }

    public double getGeneral() {
        solveGeneral();
        return general;
    }

    public void solveGeneral() {
        System.out.println(one + two * 2 + tree * 3 + four * 4 + five * 5);
        System.out.println(getNumberOfRates());
        if(getNumberOfRates() != 0)
            general = (one + two * 2 + tree * 3 + four * 4 + five * 5) * 1.0 / getNumberOfRates();
        else general = 0;
    }

    public void minus(int rate) {
        switch (rate) {
            case 1:
                one--;
                break;
            case 2:
                two--;
                break;
            case 3:
                tree--;
                break;
            case 4:
                four--;
                break;
            case 5:
                five--;
                break;
        }
    }

    public void plus(int rate) {
        switch (rate) {
            case 1:
                one++;
                break;
            case 2:
                two++;
                break;
            case 3:
                tree++;
                break;
            case 4:
                four++;
                break;
            case 5:
                five++;
                break;
        }
    }

    public String getGeneralFormated() {
        solveGeneral();
        System.out.println(String.valueOf((int) general));
        System.out.println(String.format("%.1f", general));
        System.out.println(general == (int) general);
        if (general == (int) general) {
            return String.valueOf((int) general);
        } else
            return String.format("%.1f", general);
    }

    public int getNumberOfRates() {
        return one + two + tree + four + five;
    }



}

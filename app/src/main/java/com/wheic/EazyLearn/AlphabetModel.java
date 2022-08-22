package com.wheic.EazyLearn;

public class AlphabetModel {
    String name;
    int image;
    int modelRes;

    public AlphabetModel(String name, int image, int modelRes) {
        this.name = name;
        this.image = image;
        this.modelRes = modelRes;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getModelRes() {
        return modelRes;
    }

    public void setModelRes(int modelRes) {
        this.modelRes = modelRes;
    }
}

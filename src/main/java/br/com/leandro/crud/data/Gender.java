package br.com.leandro.crud.data;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private String text;

    Gender(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
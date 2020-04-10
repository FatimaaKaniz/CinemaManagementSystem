/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hallgato
 */
public class Student implements Serializable {

    private StringProperty name = new SimpleStringProperty();
    private int credits;
    private LocalDate birthDate;

    public Student(String name, int credits, LocalDate birthDate) {
        this.name.setValue(name);
        this.credits = credits;
        this.birthDate = birthDate;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeUTF(name.getValue());
        s.writeInt(credits);
        s.writeObject(birthDate);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        this.name = new SimpleStringProperty(s.readUTF());
        this.credits = s.readInt();
        this.birthDate = (LocalDate)s.readObject();
    }
}

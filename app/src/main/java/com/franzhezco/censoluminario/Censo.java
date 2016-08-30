package com.franzhezco.censoluminario;

/**
 * Created by Ryner on 13/07/2016.
 */
public class Censo {
    private int censoId;
    private String censoName;

    public Censo() {

    }

    public Censo(int censoId, String censoName) {
        this.censoId = censoId;
        this.censoName = censoName;
    }

    public Censo(String censoName) {
        this.censoName = censoName;
    }

    public void setID(int id) {
        censoId = id;
    }

    public int getID() {
        return censoId;
    }

    public void setName(String name) {
        censoName = name;
    }

    public String getName() {
        return censoName;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elencogenerics;

/**
 *
 * @author antonio
 */
public class Auto implements IElement{
    private String _id;
    private String _model;
    
    public Auto(String id, String model){
        this._id = id;
        this._model = model;
    }
    
     public String getKey(){
        return this._id;
    }
    
    @Override
    public String toString(){
        return this._id + " " + this._model;
    }
}

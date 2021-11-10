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
public class Person extends Element{//implements IElement{
    private String _name;
    private int _age;
    
    
    
    public Person(String name, int age){
        this._name = name;
        this._age = age;
    }
    
    public String getKey(){
        return this._name;
    }
    
    @Override
    public String toString(){
        return this._name + " " + this._age;
    }
}

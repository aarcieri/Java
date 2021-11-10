/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elencogenerics;

import java.util.ArrayList;

/**
 *
 * @author antonio
 */
public class Elenco<T extends IElement>  {
    
    protected ArrayList<T> _elenco = new ArrayList<T>();
    
    public void AddObject(T a){
        this._elenco.add(a);
    }
    
    public T Search(String key){
        for(T obj: this._elenco){
            //if(((IElement)obj).getKey().equals(key)) return obj;
            if(obj.getKey().equals(key)) return obj;
        }
        
        return null;
    }
     
    public void Print(){

        for(T obj: this._elenco){
            System.out.println(obj.toString());
        }
    }
}

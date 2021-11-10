/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elencogenerics;

import java.util.HashMap;

/**
 *
 * @author antonio
 */
public abstract class Element implements IElement{
    protected HashMap<String, Class> _fields = new HashMap<String, Class>();
    
    public void AddField(String name, Class c){
        this._fields.put(name, c);
    }
    
    public void GetField(String name) {
        
    }
    
    public void SetField(String name, Object value){
        
    }
}

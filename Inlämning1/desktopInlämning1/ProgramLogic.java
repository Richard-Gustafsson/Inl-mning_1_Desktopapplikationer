/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rille
 */
public class ProgramLogic {
    
    private static ProgramLogic instance; //Step 2 declare the instance variabel
    private List<Developer> developerArrayList;
    
    
    private ProgramLogic() //Step 1 declare the constructor and change it to private
    {
        this.developerArrayList = new ArrayList();
        developerArrayList.add(new Developer("Dice"));
        developerArrayList.add(new Developer("Massive Entertainment"));
        developerArrayList.add(new Developer("CD Project Red"));
    }
    
    public static ProgramLogic getInstance() //Step 3 write getInstance method
    {
        if (instance == null)
        {
            instance = new ProgramLogic();
        }
        return instance;
    }
    
    public List<Developer> getDeveloperList()
    {
        return developerArrayList;
    }

}

package com.idyria.osi.vui.core.properties

import com.idyria.osi.ooxoo.core.buffers.datatypes.BooleanBuffer
import com.idyria.osi.ooxoo.core.buffers.structural.DataUnit
import com.idyria.osi.tea.listeners.ListeningSupport

class BooleanProperty extends BooleanBuffer with ListeningSupport {

    // Push/Pull
    //-----------------
    override def pushRight(du: DataUnit) = {
        //println(s"Pushing right on property to ${getNextBuffer}")
        super.pushRight(du)

    }

    override def pushLeft(du: DataUnit) = {
       //println(s"Pushing left on property to ${getPreviousBuffer}")
        super.pushLeft(du)

    }

    override def importDataUnit(du:DataUnit) = {
        
        super.importDataUnit(du)
        
        this.@->("updated",this.data)
        
        
    }
    
    def onUpdated(f:PartialFunction[Any,Unit]) = {
        this.onMatch("updated")(f)
    }
    
}

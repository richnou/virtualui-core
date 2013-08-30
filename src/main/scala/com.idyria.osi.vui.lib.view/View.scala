package com.idyria.osi.vui.lib.view

import com.idyria.osi.vui.core._

/**
    Functional Trait mixed in Static view definition

    A View has per default an internal Scene Graph Group node

*/
class View extends VBuilder with ViewTrait {

    /**
        Base content of a view is a group
    */
    var content = group
        
    /**
        A Reference to the view Process currently running, if any
    */
    var process : ViewProcess = null

    def action(act : => Any) = {

        if (process!=null) {
            this.process.action({act})
        }
    }

}

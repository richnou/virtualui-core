package com.idyria.osi.vui.lib.view.wizard

import com.idyria.osi.vui.lib.view._

import com.idyria.osi.vui.core._

abstract class Wizard  extends ViewProcess with ViewBuilder {



}
class WizardDialog extends Wizard {

    // Prepare Frame
    //--------------------
    var dialog =  frame 

    /*
        Prepare the Dialog With the first View
    */
    def showDialog = {

        dialog {
            f =>
                f layout vbox
                f <= this.view.head.content 

        }

        dialog.show

    }

    def changeView(view: View) = {
        dialog.clear
        dialog <= view.content

        println("Changed View")
    }
}

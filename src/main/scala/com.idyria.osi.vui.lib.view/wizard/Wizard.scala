package com.idyria.osi.vui.lib.view.wizard

import com.idyria.osi.vui.lib.view._

import com.idyria.osi.vui.core._

abstract class Wizard  extends ViewProcess with ViewBuilder {



}
class WizardDialog extends Wizard {

    // Prepare Frame
    //--------------------
    var dialog =  frame 

    def initDialog() = {

        dialog {
            f =>
                f layout vbox
                f <= this.views.head.content 

        }

    }

    /*
        Prepare the Dialog With the first View
    */
    def showDialog = {

        dialog.show

    }

    def changeView(view: View) = {
        dialog.clear
        dialog <= view.content

        println("Changed View")
    }
}

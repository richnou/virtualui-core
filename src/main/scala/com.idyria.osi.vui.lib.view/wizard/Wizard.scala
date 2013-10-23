package com.idyria.osi.vui.lib.view.wizard

import com.idyria.osi.vui.lib.view._
import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.stdlib.node.SGCustomNode
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import scala.beans.BeanProperty

abstract class Wizard  extends ViewProcess with ViewBuilder {



}
class WizardDialog extends Wizard {

    // Prepare Frame
    //--------------------
    var dialog =  frame 

    def initDialog() = {

        dialog {
            f =>
                f layout = vbox
                f <= this.views.head.content 

        }

    }

    /*
        Prepare the Dialog With the first View
    */
    def showDialog = {

        dialog.show

    }

    override def changeView(view: View) = {
        dialog.clear
        dialog <= view.content

        println("Changed View")
    }
}




class WizardPanel extends SGCustomNode[Any] with GridBuilder {
  
  def apply(vp: ViewProcess) = {
    
  }
  
  /**
   * UI -> Add 
   */
  def createUI = grid {
    
    
  }
  
}

package com.idyria.osi.vui.lib.view.wizard.default

import com.idyria.osi.vui.lib.view.wizard._
import com.idyria.osi.vui.lib.view._

import com.idyria.osi.vui.core.stdlib.placeholder._

import com.idyria.osi.vui.core.components.graph._

class DefaultWizardDialog extends WizardDialog with PlaceHolder with GraphBuilder {

    // Place Holder
    //------------------------
    override def changeView (view:View) = {

        place("middle")(view.content)

    }

    // Default Design
    //-------------------
    

    override def initDialog() = {

        var expandConstraint = new Tuple2("expand",true)

        println("Preparing default wizard")
        this.dialog {
            dialog => 

                dialog layout vbox

                dialog <= graph {
 
                    "top" row {
                        |(label("Top"))
                    }
                    "middle" expandRow placeHolder("middle")

                    "bottom" row alignRight {

                        println("In bottom row")

                        |(button("Ok") , button("Finish") ,button("Cancel"))
                    }

                    /*row("top") {
                        |(label("Top"))
                    }
                    row(placeHolder("middle"),Seq[Tuple2[String,Any]](new Tuple2("expand",true)) ) 

                    row("bottom") {

                        |(button("Ok") , button("Finish") ,button("Cancel"))
                       // ->(  button("Ok") , button("Finish") ,button("Cancel") )

                    }*/

                }
                /*dialog <= group {
                    g => 
                        g <= label("Top")
                }

                dialog <=  placeHolder("middle") 

                dialog <= group {
                    g => 

                        g layout hbox

                        g <= button("Ok")

                        g <= button("Finish") {
                            b => b disable
                        }
                        g <= button("Cancel")
                }*/
        }

        this.dialog.revalidate

    }
    


}

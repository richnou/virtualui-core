package com.idyria.osi.vui.lib.view.wizard.default

import com.idyria.osi.vui.lib.view.wizard._
import com.idyria.osi.vui.lib.view._

import com.idyria.osi.vui.core.stdlib.placeholder._

import com.idyria.osi.vui.lib.gridbuilder._

class DefaultWizardDialog extends WizardDialog with PlaceHolder with GridBuilder {

    // Configuration
    //--------------------

    /**
        If set to true, a list of the steps with the currently highlighted steps will be displayed
        @group configuration
    */
    var displayListOfSteps = true

    // Place Holder
    //------------------------
    override def changeView (view:View) = {

        place("middle")(view.content)

    }

    // Default Design
    //-------------------
    override def showDialog = {

        // Init
        this.initDialog

        // Place first view
       /* this.view.headOption match {
            case Some(view) => ->(view.name)
            case None       => throw new RuntimeException("Showing Default Wizard with no defined views....")
        }*/

        // Show
        super.showDialog
    }

    /**
        Create the default wizard GUI
    */
    override def initDialog() = {

        var expandConstraint = new Tuple2("expand",true)

        println("Preparing default wizard")
        this.dialog {
            dialog => 

                dialog layout vbox

                dialog <= grid {
                    
                    // Top : Title
                    //----------------
                    "top" row alignLeft {

                        this.name match {
                            case name if(name==null) => (label("Unnamed Wizard") using ("rowspan" -> 2))
                            case name => (label(name) using ("rowspan" -> 2))
                        }
                        
                    }

                    // Middle placeholder for views
                    //-----------------------------------
                    this.displayListOfSteps match {
                        case true => 
                            "middle" row  {

                                (list { l => this.view.foreach(v => l.add(v.name))} using ("expandHeight"->true)) | ( placeHolder("middle") using("expand" -> true))

                            }
                        case false => 
                            "middle" expandRow placeHolder("middle")
                    }
                    

                    // Bottom: Controls
                    //-----------------------
                    /*"bottom" row group {

                       ( button("Cancel")) | button("Next") 
                    }*/
                    row(group {
                        g => 

                            g layout grid 

                            g <= button("Cancel") {
                                b => 
                            }
                            g <= button("Next")  {
                                b => 
                            }
                    }, "rowspan" -> 2)

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

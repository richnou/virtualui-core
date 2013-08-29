package com.idyria.osi.vui.lib.view.wizard.default

import com.idyria.osi.vui.lib.view.wizard._
import com.idyria.osi.vui.lib.view._

import com.idyria.osi.vui.core.components.form._
import com.idyria.osi.vui.core.stdlib.placeholder._

import com.idyria.osi.vui.lib.gridbuilder2._

class DefaultWizardDialog extends WizardDialog with PlaceHolder with GridBuilder {

    // Configuration
    //--------------------

    /**
        If set to true, a list of the steps with the currently highlighted steps will be displayed
        @group configuration
    */
    var displayListOfSteps = true
    var stepsList : VUIList[Any] = null

    // Place Holder
    //------------------------
    override def changeView (view:View) = {

        // Change Placeholder
        place("middle")(view.content)

        // Try to highlight in list
        if (stepsList!=null) {
            stepsList.clearSelection
            stepsList.select(view.name)
        }

    }

    // Default Design
    //-------------------
    override def showDialog = {

        // Init
        this.initDialog

        // Place first view
        this.view.headOption match {
            case Some(view) => ->(view.name)
            case None       => throw new RuntimeException("Showing Default Wizard with no defined views....")
        }

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
                            case name if(name==null) => (label("Unnamed Wizard") using ("spread" -> true))
                            case name => (label(name) using ("spread" -> true))
                        }
                        
                    }

                    // Middle placeholder for views
                    //-----------------------------------
                    this.displayListOfSteps match {

                        // With List on the left
                        case true => 
                            "middle" row  {

                                // Prepare list 
                                stepsList = list {
                                    l => this.view.foreach(v => l.add(v.name))
                                }

                                // Create Column
                                (stepsList using expandHeight) | (placeHolder("middle") using expand)
                                

                            }
                        case false => 
                            "middle" row using("expand"->true) { placeHolder("middle") }
                    }
                    

                    // Bottom: Controls
                    //-----------------------
                    "bottom" row using(List(spread,expandWidth)) { 

                                    subgrid {
                                            
                                            "-" row {
                    
                                                ( 
                                                    button("Cancel") { b => 



                                                    } using pushRight)  | (

                                                    button("Next") { b => 

                                                        b.onClicked {
                                                            nextView
                                                        }

                                                    }

                                                )
                    
                                            }
                        }
                            
                   
                    }
                    /*"bottom" row group {

                       ( button("Cancel")) | button("Next") 
                    }*/
                   /* row(group {
                        g => 

                            g layout grid 

                            g <= button("Cancel") {
                                b => 
                            }
                            g <= button("Next")  {
                                b => 
                            }
                    }, "rowspan" -> 2)*/

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

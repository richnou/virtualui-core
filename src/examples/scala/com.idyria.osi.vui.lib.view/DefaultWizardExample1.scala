package com.idyria.osi.vui.lib.view.wizard.default




object DefaultWizardExample1 extends App {


    println("Welcome to DefaultWizardExample1")


    // Let's Define a Wizard
    //-------------
    object wizard extends DefaultWizardDialog {

        // Parameters
        //----------------
        dialog.size(800,600)
        dialog.title("Default WizardExample1")

        // Views
        //----------
        
        /*"A" is {

            node  => 

                node layout vbox

                node <= label("View A")
                node <= button("Go to B") {
                    button => button.onClicked {

                        "B"

                    }
                }


        }*/

    }

    wizard.initDialog
    //wizard -> "A"
    wizard.showDialog

    println("End Of Dialog")

    /*
    var wizard = new Wizard() {

        "A" is {

            node : SGGroup[Any] => 



        }

        "B" is {

            node => 

        }

        "C" is {

            node => 

        }

        "error" is {
            node => 
            
        }

    }*/

}

package com.idyria.osi.vui.lib.view


import com.idyria.osi.ooxoo.model.out.scala._
import com.idyria.osi.ooxoo.model.producers
import com.idyria.osi.ooxoo.model.ModelBuilder
import com.idyria.osi.ooxoo.model.producer

@producers(Array(
    new producer(value=classOf[ScalaProducer])
))
object ViewModel extends ModelBuilder {
    

    parameter("scalaProducer.targetPackage"->"com.idyria.osi.vui.lib.view")


    // View Process
    //-------------------
    "ViewProcessTrait" is {

        //withTrait("com.idyria.osi.vui.lib.view.ViewProcessTrait")
        isTrait

        attribute("name") {
            "This is the name of the View Process. Useful for GUI wizard for example"
        }
    }


    // View
    //-------------------

    var view = "ViewTrait" is {
        isTrait

        attribute("id")
        attribute("name")
        
       // withTrait("com.idyria.osi.vui.lib.view.ViewTrait")

    }


    "ViewGroupTrait" is {
        isTrait
        
        importElement("View").maxOccurs = 10
        
        //"View" multipleOf(view)

       // withTrait("com.idyria.osi.vui.lib.view.ViewGroupTrait")

    }
   

    
}

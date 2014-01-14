package com.idyria.osi.vui.lib.view

import scala.language.implicitConversions

/**
    A Class to define a Process between views

    This View Process inherit ViewBuilder because it can build views
*/
trait ViewProcess extends ViewGroup with ViewProcessTrait {

    //var currentView : View = null

    // View Definitions
    //---------------------------
  
  
    onWith("view.current") {
        view : View => 

            //println(s"Added view with id : ${view.id}")

            view.process = this
    }
    onWith("view.progressTo") {
        view : View => 

            //println(s"Added view with id : ${view.id}")
            currentView = view
            
    }

    // Action
    //---------------

    /**
        Run the Closure action on the view process, and react depending based on the outcome
    */
    def action(act: => Any) = {

        //println("Running action from process")

        try {
            var res = act 
            if (res!=null && res!="")
                this.progressTo(res.toString)
        } catch {
            case e : Throwable => 
                println(s"Seen Error from View process: ${e}")
                e.printStackTrace()
                progressToErrorView(e)
        }
        

        //println(s"Result: ${res.isInstanceOf[String]}")
    }

    // Process
    //--------------------------

    // View Change and Progress
    //------------------

    /**
        Try to reach a view
    */
    def progressTo(nextView: String) : Unit = {

        // Search
        //----------
        println(s"Trying to go to $nextView")
        this.views.foreach {
            v => println(s"--> Available: ${v.id}")
        }
        this.views.find(nextView == _.id.toString) match {
            case Some(view) => 
              
                this.changeView(view)
                @->("view.progressTo",view)
                
                println("----> Found: "+this.hashCode())
                
            case None => throw new RuntimeException(s"Could not change to whished view: $nextView")
        }

    }
    def ->(nextView: String) = this.progressTo(nextView)

    /**
        Progress to first view
    */
    def resetProgress = {
        this.views.headOption match {
            case Some(view) => progressTo(view.id)
            case None => 
        }
    }

    /**
        Return String if of reset view, for usage inside action
    */
    def resetView : String = {
        this.views.headOption match {
            case Some(view) => view.id
            case None => null
        }
    }

    /**
        Return String if of the next view, for usage inside action
    */
    def nextView : String = {

        currentView match {
 
            case v if (v!=null && this.views.isDefinedAt(this.views.indexOf(v)+1)) => this.views.get(this.views.indexOf(v)+1).get.name
            case _ => this.resetView
        }
   

    }

    /**
        Progress to view named error
    */
    def progressToErrorView( e : Throwable) = {

        this.views.find("error" == _.id.toString) match {
            case Some(view) => 
                //println("Found error view with listeners: "+view.content.listeningPointsWith.size)
                view.content.@->("error",e)
                //this.changeView(view)
                this.progressTo(view.id.toString)
            case None => throw new RuntimeException(s"Could not change to any error view")
        }

    }

    /**
        To be Implemented by the class responsible for changing the view content
    */
    def changeView(view: View) = {
      
    }


    
    
}


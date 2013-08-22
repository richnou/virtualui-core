package com.idyria.osi.vui.core.components.layout


class LayoutConstraints {

    var constraints = Map[String,Any]()

    def apply(lc: (String,Any)) = {
         this.constraints = this.constraints + lc
    }

   

    def apply(lc: Seq[(String,Any)]) = {
         this.constraints = this.constraints ++ lc
    }

    def apply(lc: LayoutConstraints) = {

    
        this.constraints = this.constraints ++ lc.constraints
        /*lc.constraints.foreach {
            (key,value) => 
                this.constraints = this.constraints + (key -> value)
        }*/

    }

    def get(str: String) : Any = {

        this.constraints.get(str) match {
            case Some(res) =>

                    res

            case None => throw new RuntimeException(s"Layout constraints does not contain key $str")
        }
    }

    def getOption(str: String,whishedClass: Class[_]) : Option[Any] = this.constraints.get(str) match {
        case Some(element) if (whishedClass.isAssignableFrom(element.getClass)) => Option(element)
        case _ => None
    }
    def getOption(str: String) : Option[Any] = this.constraints.get(str)

    // Operators
    //----------------

    /**
        Returns a new LAyout constraints beeing this one + the input one
    */
    def +(input: LayoutConstraints) = {

        var result = LayoutConstraints()
        result(this)
        result(input)
        result
    }

}

object LayoutConstraints {

    def apply( tuples: Tuple2[String,Any]*) : LayoutConstraints = {

        var constraints = new LayoutConstraints
        tuples.foreach {
            t => constraints.constraints = constraints.constraints + t
        }
        constraints

    }

}

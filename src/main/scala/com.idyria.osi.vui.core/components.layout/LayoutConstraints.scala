package com.idyria.osi.vui.core.components.layout


class LayoutConstraints {

    var constraints = Map[String,Any]()

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

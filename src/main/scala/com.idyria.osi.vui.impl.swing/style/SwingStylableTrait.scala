/**
 *
 */
package com.idyria.osi.vui.core.swing.style

import java.awt.Component
import scala.util.parsing.combinator.RegexParsers
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.styling.StylableTrait
import scala.util.parsing.combinator.Parsers$Failure








/**
 * Implements default for Stylable trait that relies on CSS3 parser
 * that applies requests to swing components
 * @author rleys
 *
 */
trait SwingStylableTrait extends StylableTrait with SGNode[Component]{

  /*class CSS3Parser extends RegexParsers{

    def background_color: Parser[String] = "background-color:\\s*(.+);".r


  }*/

  class ColorDefinition {

  }

  object CSS3Parser extends RegexParsers {

    private def background_color: Parser[Unit] = """background-color: """ ~> color_definition

    private def color_definition : Parser[Unit] = """white""" ^^ {rt => println(s"Bg col matched: ${rt}") }

    private def parse(css:String)= {

      //println(CSS3Parser.parseAll(this.background_color,css.toCharArray()))



    }

  }

  override def style(css:String) = {

    println("Swing Styling a component: "+base)
    println(s"-- Style is: ${css}")



    //
    //println(CSS3Parser.parseAll(CSS3Parser.background_color,css.toCharArray()))

    /*CSS3Parser.parseAll(CSS3Parser.background_color,css.toCharArray()) match {

      //	case f : Success(lup,_) => println("Success")
      	/*case x  =>
      	  println(x.get
      	  println("->"+x.getClass())*/


      	case x if (x.get=="bg") => println("background-color matched")

      	case x : Throwable => println("Error "+x)

      	case x => println("Matched Something "+x)




      }*/


  }


}

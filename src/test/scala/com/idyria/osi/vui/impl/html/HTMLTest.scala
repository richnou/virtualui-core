package com.idyria.osi.vui.impl.html

import org.scalatest.FunSuite
import com.idyria.osi.vui.impl.html.components.Table
import com.idyria.osi.ooxoo.core.buffers.datatypes.XSDStringBuffer

class HTMLTest extends FunSuite with HtmlTreeBuilder {

  class TableObject (

    var name: XSDStringBuffer = null,

    var category: XSDStringBuffer = null
  ) {
    
  }

  test("Table Test") {

    // Prepare Data
    //-----------
	var datas = List(new TableObject("a","b"),new TableObject("a","b"),new TableObject("a","b"))
    
    // Output
    //---------------
    var d = div {

      var tb = table[TableObject]
      tb {
        t ⇒
          t.column("A") {
            c ⇒
              c.content {
                content ⇒ content.name
              }
          }

          t.column("B") {
            c ⇒
              c.content {
                content ⇒ content.category
              }
          }

      }
      
      datas.foreach(tb.add(_))
      
      /*  tb {
        t : Table[TableObject] => 
      }*/
    }
	
	// Result
	println("Result: "+d.toString)

  }

  test("Produce HTML") {

    var html = this.html {

      head {

        title("Page title")

        link {
          attribute("rel" -> "stylesheet")

        }

        this.genericElt {

        }
        this.genericElt("tt")

      }

      body {

        h1("Test Title")

      }

    }

    println("Result: " + html.toString())

  }

}
package com.idyria.osi.vui.impl.html


import org.scalatest.FunSuite

class HTMLTest extends FunSuite with HtmlTreeBuilder {
  
  test("Produce HTML") {
    
    var html = this.html {
      
      this.head {
        
      }
      
      this.body {
        
      }
      
    }
    
    println("Result: "+html.toString())
    
  }
  
}
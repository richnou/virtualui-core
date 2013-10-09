package com.idyria.osi.vui.impl.html


import org.scalatest.FunSuite

class HTMLTest extends FunSuite with HtmlTreeBuilder {
  
  test("Produce HTML") {
    
    var html = this.html {
      
      head {
        
         title("Page title")
        
        link {
          attribute ("rel" -> "stylesheet")
          
        }
        
       
        
        this.genericElt {
          
        }
        this.genericElt("tt")
        
      }
      
      body {
        
        h1("Test Title") 
        
        
        
        
      }
      
    }
    
    println("Result: "+html.toString())
    
  }
  
}
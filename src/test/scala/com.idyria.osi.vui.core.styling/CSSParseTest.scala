package com.idyria.osi.vui.core.styling

import org.scalatest.FunSuite



class CSSTest extends FunSuite {
  
  test("Simple class parse") {
    
    CSSStyle.readCSS("""
    
        .h2 {
    	
        }
    
    """)
    
    CSSStyle.readCSS("""
    
        .h1 {
	
	font-size: 16pt;
	font-weight: bold;
	
}
    
    """)
    
    // Assertions
    //-----------------
    assert(CSSStyle.classes.contains("h1"))
    assertResult(2)(CSSStyle.classes("h1").size)
    assertResult("font-size")(CSSStyle.classes("h1")(0)._1)
    
  }
  
}
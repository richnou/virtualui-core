package com.idyria.osi.vui.impl.html

import com.idyria.osi.vui.core.components.table.TableBuilderInterface
import com.idyria.osi.vui.impl.html.components.HTMLNode
import com.idyria.osi.vui.core.components.table.SGTable
import com.idyria.osi.vui.impl.html.components.Table

trait TableBuilder extends TableBuilderInterface[org.w3c.dom.html.HTMLElement] {
  
  
  def table[OT] : SGTable[OT,org.w3c.dom.html.HTMLElement] = {
    
    return new Table[OT] {
      
      
    }
    
  }
  
  
}
package com.idyria.osi.vui.impl.html

import com.idyria.osi.vui.core.components.table.TableBuilderInterface
import com.idyria.osi.vui.impl.html.components.HTMLNode
import com.idyria.osi.vui.core.components.table.SGTable
import com.idyria.osi.vui.impl.html.components.Table

trait TableBuilder extends TableBuilderInterface[Any] {
  
  
  def table[OT] : SGTable[OT,Any] = {
    
    return new Table[OT] {
      
    }
    
  }
  
  
}
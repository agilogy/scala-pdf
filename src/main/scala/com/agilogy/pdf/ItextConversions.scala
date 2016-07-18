package com.agilogy.pdf

private[pdf] object ItextConversions {
  def mask(valueToMask:Boolean, iTextValue:Int):Int = if(valueToMask) iTextValue else 0
}

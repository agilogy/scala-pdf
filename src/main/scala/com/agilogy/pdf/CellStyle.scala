package com.agilogy.pdf

import com.lowagie.text.Rectangle

case class Border(top: Boolean, bottom: Boolean, left: Boolean, right: Boolean) {
  private[pdf] def toIText: Int = {
    import ItextConversions.mask
    import Rectangle._
    mask(top, TOP) | mask(bottom, BOTTOM) | mask(left, LEFT) | mask(right, RIGHT)
  }
}

object Border {
  val Box = Border(top = true, bottom = true, left = true, right = true)
  val Sides = Border(top = false, bottom = false, left = true, right = true)
  val None = Border(top = false, bottom = false, left = false, right = false)
  val Last = Border(top = false, bottom = true, left = true, right = true)
}

case class CellStyle(border: Border = Border.None, padding: Padding = Padding.Default)

object CellStyle {
  val Default = CellStyle()
}

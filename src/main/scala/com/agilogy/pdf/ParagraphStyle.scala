package com.agilogy.pdf

import java.awt.Color

import com.agilogy.pdf.FontFamily.FontFamily
import com.lowagie.text.{Font => IFont}

object FontFamily extends Enumeration {
  type FontFamily = Value
  val Courier = Value(0, "Courier")
  val Helvetica = Value(1, "Helvetica")
  val TimesRoman = Value(2, "Times Roman")
}

case class FontStyle(fontFamily: FontFamily = FontFamily.Helvetica, fontSize: Float = 10f, color: Color = Color.BLACK,
                     bold: Boolean = false, italic: Boolean = false, underline: Boolean = false, strikethru: Boolean = false) {

  private[pdf] def toFont: IFont = {
    val iTextStyle: Int = {
      import IFont._
      import ItextConversions.mask
      mask(bold, BOLD) | mask(italic, ITALIC) | mask(underline, UNDERLINE) | mask(strikethru, STRIKETHRU)
    }
    new IFont(fontFamily.id, fontSize, iTextStyle, color)
  }
}
object FontStyle {
  val Default = FontStyle()
}


import com.agilogy.pdf.Alignment._
case class ParagraphStyle(fontStyle: FontStyle = FontStyle.Default,
                          alignment: Alignment = Left,
                          multipliedLeading: Float = 1.1f,
                          indentationLeft: Float = 0f,
                          indentationRight: Float = 0f,
                          firstLineIndentation: Float = 0f) {

}
object ParagraphStyle {
  val Default = ParagraphStyle()
}
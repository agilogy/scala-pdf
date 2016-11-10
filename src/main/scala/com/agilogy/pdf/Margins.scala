package com.agilogy.pdf

case class Margins(leftMargin: Float, rightMargin: Float, topMargin: Float, bottomMargin: Float,
                   leftRightMirroring: Boolean = false, topBottomMirroring: Boolean = false) {

}

object Margins {
  //lateral margins must be symmetric in order to align header, content and footer
  val Default = Margins(
    leftMargin = -25f,
    rightMargin = -25f,
    topMargin = 20f,
    bottomMargin = 20f
  )
}
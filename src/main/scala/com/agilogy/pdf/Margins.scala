package com.agilogy.pdf

case class Margins(leftMargin: Float, rightMargin: Float, topMargin: Float, bottomMargin: Float,
                   leftRightMirroring: Boolean = false, topBottomMirroring: Boolean = false) {

}

object Margins {
  val Default = Margins(
    leftMargin = -40f, // why they can be negative is way beyond me
    rightMargin = -40f,
    topMargin = 20f,
    bottomMargin = 20f
  )
}
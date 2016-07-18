package com.agilogy.pdf

import com.lowagie.text.{Element => IElement, Image => IImage}

case class Image(resourcePath:String) extends ITextableElement {
  override private[pdf] def toItext(currentPage: Int, totalPages: Int): IElement = {
    IImage.getInstance(getClass.getResource(resourcePath))
  }
}

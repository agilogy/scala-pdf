package com.agilogy.pdf

import com.lowagie.text.{Element => IElement, Phrase => IPhrase}

case class PageNumber(fontStyle: FontStyle = FontStyle.Default) extends ITextableElement {
  override private[pdf] def toItext(currentPage: () => Int, totalPages: Int): IElement = {
    val p = new IPhrase()
    p.setFont(fontStyle.toFont)
    p.add(currentPage().toString)
    p
  }
}

object PageNumber extends PageNumber(FontStyle.Default)
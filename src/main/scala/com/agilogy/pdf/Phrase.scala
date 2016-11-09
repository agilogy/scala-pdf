package com.agilogy.pdf

import com.lowagie.text.{Element => IElement, Phrase => IPhrase}

case class Phrase(text: String, fontStyle: FontStyle = FontStyle.Default) extends ITextableElement {
  override private[pdf] def toItext(currentPageFn: () => Int, totalPages: Int): IElement = {
    val p = new IPhrase()
    p.setFont(fontStyle.toFont)
    p.add(text)
    p
  }
}

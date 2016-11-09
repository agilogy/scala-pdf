package com.agilogy.pdf

import com.lowagie.text.pdf.PdfPTable
import com.lowagie.text.{Element => IElement}

case class Table(widths: Seq[Float], cells: Seq[Cell], header: Seq[Cell], headerRows: Option[Int] = None, spaceBefore: Float = 0f, spaceAfter: Float = 0f) extends ITextableElement {
  val numColumns = widths.size
  override private[pdf] def toItext(currentPageFn:() => Int, totalPages: Int): IElement = {
    val table = new PdfPTable(numColumns)
    table.setKeepTogether(true)
    table.setSpacingBefore(spaceBefore)
    table.setSpacingAfter(spaceAfter)
    table.setWidths(widths.toArray)
    (header ++ cells).map(_.toItext(currentPageFn, totalPages)).foreach(c => table.addCell(c))
    table.setHeaderRows(headerRows.getOrElse(header.size / numColumns))
    table
  }
  def withSpaceBefore(spaceBefore: Float) = this.copy(spaceBefore = spaceBefore)
  def withSpaceAfter(spaceAfter: Float) = this.copy(spaceAfter = spaceAfter)
  def withHeader(headers: Cell*) = this.copy(header = headers)
}

object Table {
  def apply(widths: Seq[Float], cells: Cell*) = new Table(widths, cells, Nil, None, 0f, 0f)
  def apply(cells: Cell*) = new Table(Nil, cells, Nil, None, 0f, 0f)
}

package com.agilogy.pdf

import java.io.OutputStream

import com.agilogy.pdf.util.NullOutputStream
import com.lowagie.text.pdf.{ColumnText => IColumnText, _}
import com.lowagie.text.{PageSize, Document => IDocument, Phrase => IPhrase, Chunk => IChunk}

case class Document(margins: Margins, pageHeader: Seq[ITextableElement], elements: Seq[Element],
                    pageFooter:Option[Seq[ITextableElement]] = None, leftSideVerticalContent:Option[Seq[Phrase]] = None) {

  def generatePdf(os: OutputStream): Unit = {
    val totalPages = generatePdf(new NullOutputStream, 0)
    generatePdf(os, totalPages)
  }

  private def generatePdf(os: OutputStream, totalPages: Int): Int = {
    val document = new IDocument(PageSize.A4)
    val writer = PdfWriter.getInstance(document, os)
    val currentPageFn = writer.getCurrentPageNumber _
    val header = oneCellTable(PageSize.A4.getWidth, pageHeader) _
    val footer = pageFooter.map(f => oneCellTable(PageSize.A4.getWidth, f) _)
    document.setMargins(margins.leftMargin, margins.rightMargin, header(currentPageFn, totalPages).getTotalHeight + margins.topMargin,
      footer.fold(margins.bottomMargin)(_(currentPageFn,totalPages).getTotalHeight + margins.bottomMargin))
    document.setMarginMirroring(margins.leftRightMirroring)
    document.setMarginMirroringTopBottom(margins.topBottomMirroring)

    var returnTotalPages = 0
    writer.setPageEvent(new PdfPageEventHelper {
      override def onEndPage(writer: PdfWriter, document: IDocument): Unit = {
        returnTotalPages += 1
        header(currentPageFn, totalPages).writeSelectedRows(0, 1, margins.leftMargin, writer.getPageSize.getTop - 25f, writer.getDirectContent)
        footer.foreach(f => f(currentPageFn,totalPages).writeSelectedRows(0, 1, margins.leftMargin, writer.getPageSize.getBottom + f(currentPageFn,totalPages).getTotalHeight + 25f, writer.getDirectContent))
        var newSidePhrasePosition = 15f
        leftSideVerticalContent.foreach (_.foreach { p =>
            val phraseWithMarginsSize = new IChunk(p.text,p.fontStyle.toFont).getWidthPoint
            IColumnText.showTextAligned(writer.getDirectContent, Alignment.Center.id, p.toItext(currentPageFn, totalPages).asInstanceOf[IPhrase], newSidePhrasePosition, (PageSize.A4.getHeight - phraseWithMarginsSize)/2, 90f)
            newSidePhrasePosition += 7f
          }
        )
      }
    })

    document.open()
    elements.foreach {
      case PageBreak => document.newPage()
      case e: ITextableElement => document.add(e.toItext(currentPageFn, totalPages))
    }
    document.close()
    returnTotalPages
  }

  def oneCellTable(width: Float, elements: Seq[ITextableElement])(currentPageFn: () => Int, totalPages: Int) = {
    val table = new PdfPTable(1)
    table.setTotalWidth(width)
    val cell = new PdfPCell()
    cell.setBorder(0)
    elements.foreach(h => cell.addElement(h.toItext(currentPageFn, totalPages)))
    table.addCell(cell)
    table
  }
}

object Document {
  def apply(elements: ITextableElement*) = new Document(Margins.Default, Nil, elements)
}
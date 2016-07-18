package com.agilogy.pdf.util

import java.io.OutputStream

private[pdf] class NullOutputStream extends OutputStream {
  override def write(i: Int): Unit = {
    //do nothing.
  }
}

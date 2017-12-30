package de.hackundsoehne.invoicegen.model

/** Specifies talKIT or Hack & Söhne as targets */
sealed trait Target
object TalKIT extends Target
object HackUndSoehne extends Target


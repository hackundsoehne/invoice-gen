package de.hackundsoehne.invoicegen.model

import java.util.Locale

/** Specifies structure of an invoice as it is received from the client.
  *
  * @param address Address of company
  * @param city City of company
  * @param company Name of company
  * @param country Country of residence of company
  * @param dealDate Date the agreement was made
  * @param invoiceDate Date of the invoice
  * @param locale Locale of the invoice (German, English, ect.)
  * @param orders Orders associated with invoice
  * @param person Name of contact at company
  * @param target Hack&Söhne or talKIT
  * @param transferDeadline Deadline for funds to be transferred
  * @param zip Zip code of company
  */
case class Invoice(address: String,
                   city: String,
                   company: String,
                   country: String,
                   dealDate: String,
                   invoiceDate: String,
                   locale: Locale,
                   orders: List[Order],
                   person: String,
                   target: Target,
                   transferDeadline: String,
                   zip: String)

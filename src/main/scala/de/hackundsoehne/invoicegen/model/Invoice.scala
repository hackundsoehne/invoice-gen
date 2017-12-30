package de.hackundsoehne.invoicegen.model

import java.util.Locale

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

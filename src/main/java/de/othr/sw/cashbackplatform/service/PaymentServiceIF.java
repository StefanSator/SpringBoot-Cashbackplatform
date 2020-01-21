package de.othr.sw.cashbackplatform.service;

/*
 * Dieser Service ist nur ein Dummy, da wir in unserer Gruppe
 * keine echte Schnittstelle zu einem Payment Provider geplant haben.
 * Anstelle eines Payment Providers nutze ich eines Statistik Provider.
 * Für weitere Infos zu Schnittstellen in meinem Projekt, siehe Komponenten
 * Diagramm.
 */

public interface PaymentServiceIF {
	public void transferMoney(String iban, double value) throws Exception;
}

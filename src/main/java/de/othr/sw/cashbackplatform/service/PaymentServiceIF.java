package de.othr.sw.cashbackplatform.service;

import de.othr.sw.cashbackplatform.entity.PrivateCustomer;

/*
 * Dieser Service ist nur ein Dummy, da wir in unserer Gruppe
 * keine echte Schnittstelle zu einem Payment Provider geplant haben.
 * Anstelle eines Payment Providers nutze ich eines Statistik Provider.
 * Für weitere Infos zu Schnittstellen in meinem Projekt, siehe Komponenten
 * Diagramm von Meilenstein 1.
 */

public interface PaymentServiceIF {
	public void transferMoney(String iban, double value) throws Exception;
}

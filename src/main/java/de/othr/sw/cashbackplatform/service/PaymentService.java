package de.othr.sw.cashbackplatform.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

/*
 * Dieser Service ist nur ein Dummy, da wir in unserer Gruppe
 * keine echte Schnittstelle zu einem Payment Provider geplant haben.
 * Anstelle eines Payment Providers nutze ich einen Statistik Provider.
 * Für weitere Infos zu Schnittstellen in meinem Projekt, siehe Komponenten-
 * Diagramm von Meilenstein 1.
 */

@Service
public class PaymentService implements PaymentServiceIF {

	@Override
	@Transactional
	public void transferMoney(String iban, double value) throws Exception {
		// Money gets transferred to customer
		// Unfortunately nothing happens here because this is only a dummy
		// For information see comment above.
	}

}

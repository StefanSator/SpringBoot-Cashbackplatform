package de.othr.sw.cashbackplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.entity.Kunde;
import de.othr.sw.cashbackplatform.repository.KundeRepository;

@Service
public class KundeService implements KundeServiceIF {
	@Autowired
	private KundeRepository kundeRepository;

	@Override
	public Kunde kundeAnlegen(Kunde kunde) {
		Kunde neu = kundeRepository.save(kunde);
		return neu;
	}

}

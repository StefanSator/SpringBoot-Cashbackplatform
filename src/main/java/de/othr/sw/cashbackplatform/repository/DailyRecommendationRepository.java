package de.othr.sw.cashbackplatform.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import de.othr.sw.cashbackplatform.entity.DailyRecommendation;

public interface DailyRecommendationRepository extends CrudRepository<DailyRecommendation, Date> {

}

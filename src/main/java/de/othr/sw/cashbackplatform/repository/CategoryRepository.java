package de.othr.sw.cashbackplatform.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Shop;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByCategoryAndOwner(String categoryname, Shop owner);
}

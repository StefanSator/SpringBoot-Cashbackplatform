package de.othr.sw.cashbackplatform.proxy;

import de.othr.sw.cashbackplatform.entity.statisticrestservice.BusinessObjectDTO;
import de.othr.sw.cashbackplatform.entity.statisticrestservice.StatisticPackageDTO;

public interface StatisticsProxyIF {
	public StatisticPackageDTO sendBusinessObjectsAndReceiveStatisticPackageDTO(BusinessObjectDTO dto) throws Exception;
}

package de.othr.sw.cashbackplatform.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.othr.sw.cashbackplatform.entity.statisticrestservice.BusinessObjectDTO;
import de.othr.sw.cashbackplatform.entity.statisticrestservice.StatisticPackageDTO;

@Component
@Qualifier("test")
public class StatisticsTestProxy implements StatisticsProxyIF {
	@Autowired
	private RestTemplate restServiceClient;
	
	@Override
	public StatisticPackageDTO sendBusinessObjectsAndReceiveStatisticPackageDTO(BusinessObjectDTO dto) throws Exception {
		StatisticPackageDTO statisticsPackage = restServiceClient
	    		.postForObject("http://localhost:8824/restapi/sendBusinessObjectsAndReceiveStatisticPackageDTO", 
							   dto,
							   StatisticPackageDTO.class);
		return statisticsPackage;
	}

}

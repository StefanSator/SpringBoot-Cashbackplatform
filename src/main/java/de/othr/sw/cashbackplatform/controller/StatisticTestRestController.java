package de.othr.sw.cashbackplatform.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.othr.sw.cashbackplatform.entity.statisticrestservice.BusinessObjectDTO;
import de.othr.sw.cashbackplatform.entity.statisticrestservice.StatisticPackageDTO;

/* 
 * Rest Controller for testing rest call in
 * StatisticsTestProxy.java
 */

@RestController
@RequestMapping("/restapi")
public class StatisticTestRestController {
	@Autowired
    ResourceLoader resourceLoader;
	
	@RequestMapping(value="/sendBusinessObjectsAndReceiveStatisticPackageDTO", method = RequestMethod.POST)
	public StatisticPackageDTO sendBusinessObjectsAndReceiveStatisticPackageDTO(@RequestBody BusinessObjectDTO businessObjectDTO) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:static/image/TestStatistic.png");
		InputStream input = resource.getInputStream();
		File file = resource.getFile();
		BufferedImage bufferedImage = ImageIO.read(file);
		ByteArrayOutputStream byteoutputstream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", byteoutputstream);
		byte[] statisticdata = byteoutputstream.toByteArray();
		StatisticPackageDTO statisticsPackage = new StatisticPackageDTO();
		List<Long> statisticStructureIDs = businessObjectDTO.getStatisticStructureIDList();
		statisticsPackage.addStatisticAsByteArray(statisticdata, statisticStructureIDs.get(0));
		return statisticsPackage;
	}
	
}

package de.othr.sw.cashbackplatform.entity.statisticrestservice;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class StatisticPackageDTO {

    // KEY: StatisticStructureID
    Map<Long, byte[]> statisticAsByteArraysMap = new HashMap<>();






    public void addStatisticAsByteArray(byte[] statisticImage, Long statisticStructureID) throws Exception
    {
        if(statisticImage!=null)
        {
            if(statisticAsByteArraysMap.containsKey(statisticStructureID) == false)
            {
                statisticAsByteArraysMap.put(statisticStructureID, statisticImage);
            }
            else
            {
                throw new Exception("The statistic with the ID: " + statisticStructureID + " does not exist");
            }
        }
        else
        {
            throw new Exception("The statistic Image as a Byte Array may not be null");
        }
    }







    public void removeStatistic(Long statisticStructureID) throws Exception
    {
        if(statisticAsByteArraysMap.containsKey(statisticStructureID))
        {
            statisticAsByteArraysMap.remove(statisticStructureID);
        }
        else
        {
            throw new Exception("The Statistic with the ID: " + statisticStructureID + " can not be deleted" +
                    " because it does not exist");
        }
    }






    public File getStatisticAsImageFile(Long statisticStructureID, String path) throws Exception
    {
        if(statisticAsByteArraysMap.containsKey(statisticStructureID))
        {
            byte[] data = statisticAsByteArraysMap.get(statisticStructureID);

            if(data!=null)
            {
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                BufferedImage bImage2 = ImageIO.read(bis);
                File image = new File(path);
                ImageIO.write(bImage2, "png",image );

                return image;
            }
            else
            {
                throw new Exception("The statistic with the ID: " + statisticStructureID + " is null!");
            }
        }
        else
        {
            throw new Exception("There is no statistic with the ID: " + statisticStructureID);
        }
    }













    public Map<Long, byte[]> getStatisticAsByteArraysMap() {
        return statisticAsByteArraysMap;
    }

    public void setStatisticAsByteArraysMap(Map<Long, byte[]> statisticAsByteArraysMap) {
        this.statisticAsByteArraysMap = statisticAsByteArraysMap;
    }
}

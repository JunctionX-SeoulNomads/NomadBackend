package com.example.servingwebcontent.shceduled;

import com.example.database.NomadDB;
import com.example.servingwebcontent.components.Hierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class UpdateClusterStats {
    private final Hierarchy hierarchy;

    @Autowired
    UpdateClusterStats(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60000)
    public void updateClusterMonthlyStats() {
        NomadDB nomadDB = new NomadDB();

        Map<Integer, Integer> clusterDailyStatById = nomadDB.getClusterDailyStat();
        nomadDB.updateClusterMonthlyStatus(clusterDailyStatById);
        nomadDB.resetClusterDailyCounter(new ArrayList<>(clusterDailyStatById.keySet()));
    }

    @Scheduled(fixedDelay = 10 * 60 * 60000)
    public void saveClusterDailyStat() {
        Map<Integer, Integer> dailyStat = hierarchy.getAndResetClustersDailyCounter();
        NomadDB nomadDB = new NomadDB();

        nomadDB.updateClusterDailyStat(dailyStat);

    }

}

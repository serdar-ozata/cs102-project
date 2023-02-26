package com.example.application.data;

import database.*;
import database.assets.FarmLand;
import database.assets.Government;
import database.assets.Info;
import database.assets.RealEstate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Allows interactions between views and database for lands
 */
@Service
public class LandService {
    DataSource dataSource = new DataSource();

    public ArrayList<Info> getAllLands(String mail) {
        ArrayList<FarmLand> farmLand = dataSource.getFarmLand(mail);
        ArrayList<RealEstate> realEstate = dataSource.getRealEstate(mail);
        ArrayList<Government> government = dataSource.getGovernment(mail);
        ArrayList<Info> output = new ArrayList<>();
        for (Info i : farmLand)
            output.add(i);
        for (Info i : realEstate)
            output.add(i);
        for (Info i : government)
            output.add(i);
        return output;
    }

    public void deleteLand(Info file, String mail) {
        String name = file.getName();
        if (file instanceof FarmLand) {
            dataSource.deleteFarmland(dataSource.getFarmlandID(name, mail));
        } else if (file instanceof RealEstate) {
            dataSource.deleteRealEstate(dataSource.getRealEstateID(name, mail));
        } else {
            dataSource.deleteGovernment(dataSource.getGovernmentID(name, mail));
        }
    }

    public void updateFarmLand(String mail, String name, String description, String groupName) {
        int fieldID = dataSource.getFarmlandID(name, mail);
        dataSource.updateFarmLandDescription(fieldID, description);
        dataSource.updateFarmLandGroupName(fieldID, groupName);
    }

    public void updateRealEstate(String mail, String name, String description, String groupName, String type, int aptNo, int floorNo) {
        int fieldID = dataSource.getRealEstateID(name, mail);
        dataSource.updateRealEstateDescription(fieldID, description);
        dataSource.updateRealEstateGroupName(fieldID, groupName);
        dataSource.updateRealEstateType(fieldID, type);
        dataSource.updateRealEstateAptNo(fieldID, aptNo);
        dataSource.updateRealEstateFloorNo(fieldID, floorNo);
    }

    public void updateGovernment(String mail, String name, String description, String groupName) {
        int fieldID = dataSource.getGovernmentID(name, mail);
        dataSource.updateGovernmentDescription(fieldID, description);
        dataSource.updateGovernmentGroupName(fieldID, groupName);
    }

    public void addCrop(FarmLand land, String mail, String name, double yield, double cost, double revenue, int year,
                        String description) {
        dataSource.addCrop(dataSource.getFarmlandID(land.getName(), mail), name, yield, cost, revenue, year, description);
    }

}

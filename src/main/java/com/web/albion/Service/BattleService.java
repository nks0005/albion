package com.web.albion.Service;

import com.web.albion.Mapper.BattleMapper;
import com.web.albion.Model.Battle;
import com.web.albion.Model.Gear;
import com.web.albion.Model.Team;
import com.web.albion.dto.*;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BattleService {

    @Autowired
    private BattleMapper battlemapper;

    @Autowired
    private GearsService gearsservice;

    @Autowired
    private GearSetService gearsetservice;

    @Autowired
    private UserAlbionService userabionservice;

    @Autowired
    private UserMatchService usermatchservice;

    private String extractWeaponName(String str) {
        String tmp = str;
        int index;

        if (str.charAt(0) == 'T') {
            index = str.indexOf('_');
            if (index != -1) {
                tmp = tmp.substring(index + 1);
            }
        }

        index = tmp.indexOf('@');
        if (index != -1) {
            tmp = tmp.substring(0, index);
        }

        return tmp;
    }

    private int getGearId(String name) {
        String tmp = name;
        if (tmp == null) tmp = "";

        GearsDto gear = new GearsDto();
        gear.setName(tmp);
        int gear_id = gearsservice.insertGearSet(gear);
        // System.out.println("gear : " + gear_id + " | name : " + tmp);

        return gear_id;
    }

    private int make_gearsetid(Gear gear) {
        GearsSetDto gearset = new GearsSetDto();

        String tmp;

        // Main
        int main_hand_id = getGearId(gear.getMain_hand());
        // Off
        int off_hand_id = getGearId(gear.getOff_hand());
        // Head
        int head_id = getGearId(gear.getHead());
        // Armor
        int armor_id = getGearId(gear.getArmor());
        // Shoes
        int shoes_id = getGearId(gear.getShoes());
        // Cape
        int cape_id = getGearId(gear.getCape());

        gearset.setMain_hand_id(main_hand_id);
        gearset.setOff_hand_id(off_hand_id);
        gearset.setHead_id(head_id);
        gearset.setArmor_id(armor_id);
        gearset.setShoes_id(shoes_id);
        gearset.setCape_id(cape_id);

        return gearsetservice.insertGearSet(gearset);
    }

    private int make_userid(Team team) {
        String user_name = team.getUser_name();
        String user_uid = team.getUser_uid();

        UserAlbionDto user = new UserAlbionDto();
        user.setUid(user_uid);
        user.setName(user_name);

        return userabionservice.insertUser(user);
    }

    private void make_user_match(Team team, String match_state, int battle_id) {
        // Gear Set 구축
        int gearset_id = make_gearsetid(team.getGear());

        // user set 구축
        int user_id = make_userid(team);

        //if(gearset_id || user_id == null) throw new TransactionSystemException("gearset_id, user_id is null");

        UserMatchDto usermatch = new UserMatchDto();

        usermatch.setIp((int)team.getIp());
        usermatch.setUser_id(user_id);
        usermatch.setMatch_state(match_state);
        usermatch.setGearset_id(gearset_id);
        usermatch.setBattle_id(battle_id);

        //System.out.println(usermatch.toString());

        usermatchservice.insertUserMatch(usermatch);
    }

    // TODO. 트랜젝션 throw TransactionSystemException 구현해야함
    @Transactional
    public int insertBattle(Battle battle) {
        if (battlemapper.checkBattleExists(battle.getBattle_id()) == 0) {
            // System.out.println("no have : " + battle.getBattle_id());


            int result = 0;

            try {
                BattlesDto battles = getBattles(battle);
                result = battlemapper.insertBattle(battles);


                // Winner
                for (Team team : battle.getWinners()) {
                    make_user_match(team, "win", battle.getBattle_id());
                }
                // Losser
                for (Team team : battle.getLossers()) {
                    make_user_match(team, "loss", battle.getBattle_id());
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


            return result;
        } else {
           // System.out.println("have : " + battle.getBattle_id());
        }
        return 0;
    }

    public int checkBattleExists(int battle_id) {
        return battlemapper.checkBattleExists(battle_id);
    }

    private static BattlesDto getBattles(Battle battle) throws ParseException {
        BattlesDto battles = new BattlesDto();
        battles.setBattle_id(battle.getBattle_id());

        String match_type = null;
        switch (battle.getMatch_type()) {
            case 2:
                match_type = "22";
                break;
            case 5:
                match_type = "55";
                break;
            case 10:
                match_type = "1010";
                break;
        }
        battles.setMatch_type(match_type);

        String battle_time = battle.getBattle_time();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = format.parse(battle_time);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());

        battles.setBattle_time(timestamp);
        return battles;
    }

    List<BattlesDto> getBattles(@Param("offset") int offset){
        return battlemapper.getBattles(offset);
    }
}

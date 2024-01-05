package com.web.albion.Thread;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.web.albion.Model.Battle;
import com.web.albion.Model.Gear;
import com.web.albion.Model.Team;
import com.web.albion.Service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.web.albion.util.CrawlerHelper;

/**
 * Battle Log와 Event Log 들로 부터
 * <p>
 * 매칭 타입 2v2, 5v5, 10v10 헬게이트 전투에 대한 battle_id를 추출한다.
 */
@Component
public class BattleLog {

    @Autowired
    private CrawlerHelper cralwerhelper;

    private List<Integer> list_cashe_battleid;

    @Autowired
    private BattleService battleservice;

    private String url_base_east_albion = "https://gameinfo-sgp.albiononline.com";
    private String url_baseBattleLogs;
    private String url_baseBattleLog;
    private String url_baseEventLogs;


    public BattleLog() {
        this.url_baseBattleLogs = url_base_east_albion + "/api/gameinfo/battles?offset=%d&limit=50&sort=recent";
        this.url_baseEventLogs = url_base_east_albion + "/api/gameinfo/events/battle/%d?offset=0&limit=%d";

        this.list_cashe_battleid = new ArrayList<Integer>();
    }

    private String getURLBattleLogs(int index) {
        return String.format(this.url_baseBattleLogs, index);
    }

    private String getURLEventLogs(int id, int match_type) {
        match_type = match_type * 2;
        return String.format(this.url_baseEventLogs, id, match_type);
    }

    private Gear getGear(String name, JsonNode jsonNode_equip) {
        Gear gear = new Gear();

        String[] equipmentTypes = {"MainHand", "OffHand", "Head", "Armor", "Shoes", "Cape"};

        // 장비 타입을 순회하면서 처리합니다.
        for (String type : equipmentTypes) {
            JsonNode equipment = jsonNode_equip.get(type);

            if (equipment != null && !equipment.isNull()) {
                String gearType = equipment.get("Type").asText();
                // 각 장비 타입에 맞는 처리를 수행합니다.
                switch (type) {
                    case "MainHand":
                        gear.setMain_hand(gearType);
                        break;
                    case "OffHand":
                        gear.setOff_hand(gearType);
                        break;
                    case "Head":
                        gear.setHead(gearType);
                        break;
                    case "Armor":
                        gear.setArmor(gearType);
                        break;
                    case "Shoes":
                        gear.setShoes(gearType);
                        break;
                    case "Cape":
                        gear.setCape(gearType);
                        break;
                }
            }
        }


        ////System.out.println("Gear : "+ gear.toString());

        return gear;
    }

    private Gear mergeGear(Gear gear_1, Gear gear_2) {

        if (gear_2.getMain_hand() != null) {
            gear_1.setMain_hand(gear_2.getMain_hand());
        }

        if (gear_2.getOff_hand() != null) {
            gear_1.setOff_hand(gear_2.getOff_hand());
        }

        if (gear_2.getHead() != null) {
            gear_1.setHead(gear_2.getHead());
        }

        if (gear_2.getArmor() != null) {
            gear_1.setArmor(gear_2.getArmor());
        }

        if (gear_2.getShoes() != null) {
            gear_1.setShoes(gear_2.getShoes());
        }

        if (gear_2.getCape() != null) {
            gear_1.setCape(gear_2.getCape());
        }

        return gear_1;
    }

    private Team getTeam(String name, JsonNode jsonNode) {
        Team team = new Team();

        double userip = 0.0;
        Gear result_gear = new Gear();

        for (JsonNode node : jsonNode) {
            // Killer
            if (node.get("Killer").get("Name").asText().equals(name)) {
                Gear killer_gear = this.getGear(name, node.get("Killer").get("Equipment"));
                result_gear = mergeGear(result_gear, killer_gear);

                double ip = node.get("Killer").get("AverageItemPower").asDouble();
                if(ip != 0.0)
                    userip = ip;
            }
            // Victim
            if (node.get("Victim").get("Name").asText().equals(name)) {
                // get uid guild ally
                team.setUser_name(name);
                team.setUser_uid(node.get("Victim").get("Id").asText());

                Gear victim_gear = this.getGear(name, node.get("Victim").get("Equipment"));
                result_gear = mergeGear(result_gear, victim_gear);

                double ip = node.get("Victim").get("AverageItemPower").asDouble();
                if(ip != 0.0)
                    userip = ip;
            }

            // GroupMembers
            for (JsonNode group : node.get("GroupMembers")) {
                if (group.get("Name").asText().equals(name)) {
                    // get uid guild ally
                    team.setUser_name(name);
                    team.setUser_uid(group.get("Id").asText());

                    Gear group_gear = this.getGear(name, group.get("Equipment"));
                    result_gear = mergeGear(result_gear, group_gear);


                    double ip = group.get("AverageItemPower").asDouble();
                    if(ip != 0.0)
                        userip = ip;
                }
            }


            // Participants
            for (JsonNode part : node.get("Participants")) {
                if (part.get("Name").asText().equals(name)) {
                    Gear part_gear = this.getGear(name, part.get("Equipment"));
                    result_gear = mergeGear(result_gear, part_gear);

                    double ip = part.get("AverageItemPower").asDouble();
                    if(ip != 0.0)
                        userip = ip;
                }
            }
        }

        //System.out.println("name : " + name + " / gear set : " + result_gear.toString());
        team.setGear(result_gear);
        team.setIp(userip);

        return team;
    }

    private List<Battle> preFindMatch(JsonNode j_node) {

        List<Battle> result = new ArrayList<Battle>();

        ////System.out.println(j_node.toString());
        ////System.out.println(j_node.isArray());

        if (j_node.isArray()) {
            for (JsonNode node : j_node) {
                // 각 배열 요소에 대한 작업 수행

                // 1차 검증. 유저 수가 2v2, 5v5, 10v10인지


                int battle_id = node.get("id").asInt();
                // 캐싱 확인
                // DB로 부터 갱신하지 않고 미리 확인함
                if (list_cashe_battleid.contains(battle_id)) continue;

                // DB확인
                if (battleservice.checkBattleExists(battle_id) > 0) continue;


                int players_count = node.get("players").size();
                int match_type;

                if (players_count == 4) match_type = 2;
                else if (players_count == 10) match_type = 5;
                else if (players_count == 20) match_type = 10;
                else continue;

                /*
                //System.out.println("===================================");
                //System.out.println("match_type : "+match_type);
                //System.out.println("battle_id : "+ node.get("id").asInt());
                //System.out.println("battle_time : " + node.get("startTime").asText());
                List<String> players = new ArrayList<String>();
                for (JsonNode player_node : node.get("players")) {
                    players.add(player_node.get("name").asText());
                }
                //System.out.println(players.toString());
                //System.out.println("===================================");
                */


                String battle_time = node.get("startTime").asText();

                List<String> players = new ArrayList<String>();
                for (JsonNode player_node : node.get("players")) {
                    players.add(player_node.get("name").asText());
                }


                Battle battle = new Battle();
                battle.setBattle_id(battle_id);
                battle.setMatch_type(match_type);
                battle.setBattle_time(battle_time);
                battle.setPlayers(players);

                result.add(battle);
            }
        }

        return result;
    }

    private boolean checkIp(JsonNode node) {

        double ip = node.get("AverageItemPower").asDouble();
        //System.out.println(ip);

        if (ip == 0.0) return true;
        else if (ip >= 1000.0 && ip <= 1450.0)
            return true;

        return false;
    }

    /**
     * 1차 처리로 만들어진 List<Battle>에서 IP, GROUP 등을 조사한다.
     *
     * @param list_battle
     * @return type list_battle
     */
    private List<Battle> FindMatch(List<Battle> list_battle) throws Exception {
        List<Battle> result = new ArrayList<Battle>();

        for (Battle battle : list_battle) {

            // battle_id 로 부터 event log 데이터를 받아와야함
            String url_eventlog = this.getURLEventLogs(battle.getBattle_id(), battle.getMatch_type());
            //System.out.println(url_eventlog);


            JsonNode jsonNode = cralwerhelper.getJsonFromUrl(url_eventlog);
            if (!jsonNode.isArray()) continue;

            /**
             * check list
             *
             * 각 노드마다
             *  groupMemberCount == match_type ( 파티 인원 수. 2v2면 2. 5v5면 5. 10v10면 10. 이여야함 )
             *
             * IP 체킹 해야됨
             *
             * Team을 구해야함.
             *  첫 GroupMembers 에서 Team A와 Team B를 구할 수 있음.
             *  이후 Victim 로그를 통해 Winner Team과 Losser Team을 구할 수 있음
             *
             * Gear를 구해야함
             *
             */

            // 1. 파티 인원수 체크
            boolean is_hellgate = true;
            for (JsonNode node : jsonNode) {
                int group_member_count = node.get("groupMemberCount").asInt();
                if (group_member_count != battle.getMatch_type()) is_hellgate = false;

                // IP 체킹


                // Killer
                if (!checkIp(node.get("Killer"))) is_hellgate = false;

                // Victim
                if (!checkIp(node.get("Victim"))) is_hellgate = false;

                // Group
                for (JsonNode group : node.get("GroupMembers")) {
                    if (!checkIp(group)) is_hellgate = false;
                }
            }
            if (!is_hellgate) continue;


            // 2. Team 구하기
            List<String> A_team = new ArrayList<String>();
            List<String> B_team = new ArrayList<String>();

            for (JsonNode node : jsonNode) {
                String killer = node.get("Killer").get("Name").asText();
                //if (!A_team.contains(killer))
                A_team.add(killer);

                for (JsonNode group : node.get("GroupMembers")) {
                    String Group = group.get("Name").asText();
                    //if (!A_team.contains(group))
                    if (!Group.equals(killer))
                        A_team.add(Group);
                }

                break;
            }

            // A_team이 제대로 완성되어있는지
            //System.out.println("A_team size : " + A_team.size());
            //System.out.println("A_team : " + A_team.toString());
            if (A_team.size() != battle.getMatch_type()) continue;

            // B_team은 전체 팀에서 A_team을 빼면 된다.
            for (String str : battle.getPlayers()) {
                B_team.add(str);
            }
            B_team.removeAll(A_team);

            ////System.out.println("A_team : " + A_team.toString());
            //System.out.println("B_team : " + B_team.toString());


            // 3. Winner Team, Losser Team 구하기
            List<String> Winner_Team;
            List<String> Losser_Team;

            int A_victims_count = 0;
            int B_victims_count = 0;

            for (JsonNode node : jsonNode) {

                Double Victim_ip = node.get("Victim").get("AverageItemPower").asDouble();
                //System.out.println("Victim_ip : " + Victim_ip);
                if (Victim_ip == 0.0) continue;

                String Victim = node.get("Victim").get("Name").asText();

                if (A_team.contains(Victim)) {
                    A_victims_count++;
                } else if (B_team.contains(Victim)) {
                    B_victims_count++;
                } else {
                    throw new Exception("Victim Error. Not A, B team");
                }
            }

            //System.out.println("A_victim_count : " + A_victims_count);
            //System.out.println("B_victim_count : " + B_victims_count);

            if (A_victims_count > B_victims_count) {
                Winner_Team = B_team;
                Losser_Team = A_team;
            } else if (A_victims_count < B_victims_count) {
                Winner_Team = A_team;
                Losser_Team = B_team;
            } else {
                continue;
            }

            //System.out.println("Winner Team : " + Winner_Team.toString());
            //System.out.println("Losser Team : " + Losser_Team.toString());

            // 4. Winner, Losser Team 들의 Gear 들을 구해야함

            List<Team> winners = new ArrayList<Team>();
            List<Team> lossers = new ArrayList<Team>();

            // Winner
            for (String str : Winner_Team) {
                Team team = getTeam(str, jsonNode);
                winners.add(team);
            }

            // Losser
            for (String str : Losser_Team) {
                Team team = getTeam(str, jsonNode);
                lossers.add(team);
            }

            // 5. result에 넣어야함
            battle.setWinners(winners);
            battle.setLossers(lossers);

            // 6. 캐싱
            list_cashe_battleid.add(battle.getBattle_id());

            result.add(battle);
        }

        //System.out.println("result : " + result.toString());
        return result;
    }

    /**
     * 전투 기록을 얻어올 때 50개 씩 얻어온다.
     * index가 2일 경우 100개를 얻어온다.
     *
     * @param index
     * @return
     */
    public List<Battle> getBattleIds(int index) {
        List<Battle> result = new ArrayList<Battle>();

        try {
            String str_url_recent_battle_log = this.getURLBattleLogs(index);
            //System.out.println(str_url_recent_battle_log);


            JsonNode jsonNode = cralwerhelper.getJsonFromUrl(str_url_recent_battle_log);
            ////System.out.println(jsonNode);

            result = preFindMatch(jsonNode);
            for (Battle battle : result) {
                //System.out.println(battle.toString());
            }
            result = FindMatch(result);


            /*
            for (Battle battle : result) {
                System.out.println("========================");
                System.out.println(battle.getBattle_id());
                System.out.println(battle.getMatch_type());
                System.out.println(battle.getBattle_time());
                System.out.println("| Winners");
                System.out.println(battle.getWinners().toString());
                System.out.println("| Lossers");
                System.out.println(battle.getLossers().toString());
                System.out.println("========================");
            }
             */


        } catch (Exception e) {
            e.printStackTrace(); // TODO. exception 설정
        }

        return result;
    }
}

package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("rest")
public class PlayerRestController {

    private final PlayerService playerService;
    private static Integer countPlayers;
//    private final Logger logger = LoggerFactory.getLogger(PlayerRestController.class);
    private final Logger logger = Logger.getLogger(PlayerRestController.class.getName());
    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players/count")
    public Integer getCountPlayers( @RequestParam(defaultValue = "ID") PlayerOrder order,
                                    @RequestParam(defaultValue = "3") Integer pageSize,
                                    @RequestParam(defaultValue = "0") Integer pageNumber,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String title,
                                    @RequestParam(required = false) Race race,
                                    @RequestParam(required = false) Profession profession,
                                    @RequestParam(required = false) Long after,
                                    @RequestParam(required = false) Long before,
                                    @RequestParam(required = false) Boolean banned,
                                    @RequestParam(required = false) Integer minExperience,
                                    @RequestParam(required = false) Integer maxExperience,
                                    @RequestParam(required = false) Integer minLevel,
                                    @RequestParam(required = false) Integer maxLevel) {
        getAllPlayersByOrder(order, pageSize, pageNumber, name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        return countPlayers;
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        logger.info(id.toString());
        if (id==0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = playerService.getPlayerById(id);
        if(player==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @GetMapping("/players")
    public List<Player> getAllPlayersByOrder(
            @RequestParam(defaultValue = "ID") PlayerOrder order,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel
    ) {
        //Формируем полный список игроков упорядоченный по заданным параметрам;
        logger.info(order + " "
                + pageSize + " "
                + pageNumber + " "
                + name + " "
                + title + " "
                + race + " "
                + profession + " "
                + after + " "
                + before + " "
                + banned + " "
                + minExperience + " "
                + maxExperience + " "
                + minLevel + " "
                + maxLevel
        );
        List<Player> players;
        switch (order.getFieldName()) {
            case ("id"): {
                players = playerService.getAllPlayersByOrderById();
                break;
            }
            case ("name"): {
                players = playerService.getAllPlayersByOrderByName();
                break;
            }
            case ("experience"): {
                players = playerService.getAllPlayersByOrderByExperience();
                break;
            }
            case ("birthday"): {
                players = playerService.getAllPlayersByOrderByBirthday();
                break;
            }
            default:
                players = playerService.getAllPlayersByOrderById();
        }

        //Фильтруем список по имени, если есть соответствующий параметр.
        if (name != null) {
            List<Player> playersWithName = playerService.getAllPlayersByNameContains(name);
            players.removeIf(p -> !playersWithName.contains(p));
        }
        //Фильтруем список по @title, если есть соответствующий параметр.
        if (title != null) {
            List<Player> playersWithTitle = playerService.getAllPlayersByTitleContains(title);
            players.removeIf(p -> !playersWithTitle.contains(p));
        }
        //Фильтруем список по бану, если есть соответствующий параметр.
        if (banned!=null) {
            List<Player> playersWithBanned;
            if(banned) {
                playersWithBanned = playerService.getAllPlayersWhoHaveBan();
            }else {
                playersWithBanned = playerService.getAllPlayersWhoHaveNotBan();
            }
            players.removeIf(p -> !playersWithBanned.contains(p));
        }
        //Фильтруем список по профессии, если есть соответствующий параметр.
        if (profession != null) {
            List<Player> playersWithProfession = playerService.getAllPlayersByProfession(profession);
            players.removeIf(p -> !playersWithProfession.contains(p));
        }
        //Фильтруем список по опыту, если есть соответствующий параметр.
        if (minExperience != null || maxExperience != null) {
            List<Player> playersWithExperience;
            if (minExperience != null && maxExperience != null) {
                playersWithExperience = playerService.getAllPlayersByExperienceBetween(minExperience, maxExperience);
            } else if(minExperience!=null){
                playersWithExperience = playerService.getAllPlayersByExperienceBetween(minExperience, Integer.MAX_VALUE);
            } else {
                playersWithExperience = playerService.getAllPlayersByExperienceBetween(0, maxExperience);
            }
            players.removeIf(p -> !playersWithExperience.contains(p));
        }
        //Фильтруем список по дате рождения, если есть соответствующий параметр.
        if (after != null || before != null) {
            Date afterDate;
            Date beforeDate;

            List<Player> playersWithBirthday;
            if (after != null && before != null) {
                afterDate = new Date(after);
                beforeDate = new Date(before);
                playersWithBirthday = playerService.getAllPlayersByBirthdayBetween(afterDate, beforeDate);
            } else if(after!=null){
                afterDate = new Date(after);
                playersWithBirthday = playerService.getAllPlayersByBirthdayBetween(afterDate, new Date());
            } else {
                beforeDate = new Date(before);
                playersWithBirthday = playerService.getAllPlayersByBirthdayBetween(new Date(0), beforeDate);
            }
            players.removeIf(p -> !playersWithBirthday.contains(p));
        }
        //Фильтруем список по уровню, если есть соответствующий параметр.
        if (minLevel != null || maxLevel != null) {
            List<Player> playersWithLevel;
            if (minLevel != null && maxLevel != null) {
                playersWithLevel = playerService.getAllPlayersByLevelBetween(minLevel, maxLevel);
            } else if(minLevel!=null){
                playersWithLevel = playerService.getAllPlayersByLevelBetween(minLevel, Integer.MAX_VALUE);
            } else {
                playersWithLevel = playerService.getAllPlayersByLevelBetween(0, maxLevel);
            }
            players.removeIf(p -> !playersWithLevel.contains(p));
        }
        //Фильтруем список по расе, если есть соответствующий параметр.
        if (race != null) {
            List<Player> playersWithRace = playerService.getAllPlayersByRace(race);
            players.removeIf(p -> !playersWithRace.contains(p));
        }

        //Формируем список для отбражения в зависимости от параметра order
        countPlayers = players.size();
        logger.info(countPlayers.toString());
        List<Player> listPlayersOnThePage = new ArrayList<>();
        int startIndex = pageNumber * pageSize;
        for (int i = startIndex; i < startIndex + pageSize && i < countPlayers; i++) {
            listPlayersOnThePage.add(players.get(i));
        }

        //Отправляем на view итоговый лист
        return listPlayersOnThePage;
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(
        @RequestBody Player player
    ){

        if(
        player.getName() == null || player.getName().length()>12 || player.getName().equals("") ||
        player.getExperience() == null || player.getTitle() == null || player.getTitle().length()>30 ||
        player.getBirthday().getTime() < 946674000000L || player.getBirthday().getTime()>32535118800000L ||
                player.getExperience() > 10000000){
            return new ResponseEntity<>(player, HttpStatus.BAD_REQUEST);
        }

        player.setId(0L);

        if(player.getBanned()==null) {
            player.setBanned(false);
        }

        int level = (int)((Math.sqrt(2500+200*player.getExperience())-50)/100);
        player.setLevel(level);
        int nextLevel = 50*(level+1)*(level+2) - player.getExperience();
        player.setUntilNextLevel(nextLevel);

        Player playerInBase = playerService.createOrUpdatePlayer(player);
        return new ResponseEntity<>(playerInBase, HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(
            @PathVariable Long id,
            @RequestBody Player player
    ){
        ResponseEntity<Player> tempEntity = getPlayerById(id);
        if(tempEntity.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(tempEntity.getStatusCode());
        }
        Player tempPlayer = tempEntity.getBody();
        if(tempPlayer==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(player.getName()!=null){
            tempPlayer.setName(player.getName());
        }
        if(player.getTitle()!=null){
            tempPlayer.setTitle(player.getTitle());
        }
        if(player.getRace()!=null){
            tempPlayer.setRace(player.getRace());
        }
        if(player.getProfession()!=null){
            tempPlayer.setProfession(player.getProfession());
        }
        if(player.getBirthday()!=null){
            if(player.getBirthday().getTime() < 946674000000L || player.getBirthday().getTime()>32535118800000L){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            tempPlayer.setBirthday(player.getBirthday());
        }
        if(player.getBanned()!=null){
            tempPlayer.setBanned(player.getBanned());
        }
        if(player.getExperience() != null) {
            if( player.getExperience()<0 || player.getExperience()>10000000){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                int exp = player.getExperience();
                tempPlayer.setExperience(exp);
                int level = (int)((Math.sqrt(2500+200*exp)-50)/100);
                tempPlayer.setLevel(level);
                int nextLevel = 50*(level+1)*(level+2) - exp;
                tempPlayer.setUntilNextLevel(nextLevel);
            }
        }
        Player updatingPlayer = playerService.createOrUpdatePlayer(tempPlayer);
        logger.info(updatingPlayer.toString());
        return new ResponseEntity<>(updatingPlayer, HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable long id){
        ResponseEntity<Player> entity = getPlayerById(id);
        logger.info(entity.toString());
        if(entity.getStatusCode()!=HttpStatus.OK){
            return new ResponseEntity<>(entity.getStatusCode());
        }
        playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

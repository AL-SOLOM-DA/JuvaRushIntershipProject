package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.List;

public interface PlayerService {

    //GET
    List<Player> getAllPlayers();
    Player getPlayerById(Long id);
    List<Player> getAllPlayersByOrderByName();
    List<Player> getAllPlayersByOrderById();
    List<Player> getAllPlayersByOrderByExperience();
    List<Player> getAllPlayersByOrderByBirthday();
    List<Player> getAllPlayersWhoHaveBan();
    List<Player> getAllPlayersWhoHaveNotBan();
    List<Player> getAllPlayersByNameContains(String name);
    List<Player> getAllPlayersByTitleContains(String title);
    List<Player> getAllPlayersByRace(Race race);
    List<Player> getAllPlayersByProfession(Profession profession);
    List<Player> getAllPlayersByExperienceBetween(Integer minExperience, Integer maxExperience);
    List<Player> getAllPlayersByLevelBetween(Integer minLevel, Integer maxLevel);
    List<Player> getAllPlayersByBirthdayBetween(Date after, Date before);

    //POST/UPDATE
    Player createOrUpdatePlayer(Player player);

    //DELETE
    void deletePlayer(long id);
}

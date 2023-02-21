package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayerById(Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);;
        return optionalPlayer.orElse(null);
    }

    @Override
    public List<Player> getAllPlayersByOrderByName() {
        return playerRepository.findAllByOrderByName();
    }

    @Override
    public List<Player> getAllPlayersByOrderById() {
        return playerRepository.findAllByOrderById();
    }

    @Override
    public List<Player> getAllPlayersByOrderByExperience() {
        return playerRepository.findAllByOrderByExperience();
    }

    @Override
    public List<Player> getAllPlayersByOrderByBirthday() {
        return playerRepository.findAllByOrderByBirthday();
    }

    @Override
    public List<Player> getAllPlayersWhoHaveBan() {
        return playerRepository.findAllByBannedIsTrue();
    }

    @Override
    public List<Player> getAllPlayersWhoHaveNotBan() {
        return playerRepository.findAllByBannedIsFalse();
    }

    @Override
    public List<Player> getAllPlayersByNameContains(String name) {
        return playerRepository.findAllByNameContains(name);
    }

    @Override
    public List<Player> getAllPlayersByTitleContains(String title) {
        return playerRepository.findAllByTitleContains(title);
    }

    @Override
    public List<Player> getAllPlayersByRace(Race race) {
        return playerRepository.findAllByRace(race);
    }

    @Override
    public List<Player> getAllPlayersByProfession(Profession profession) {
        return playerRepository.findAllByProfession(profession);
    }

    @Override
    public List<Player> getAllPlayersByExperienceBetween(Integer minExperience, Integer maxExperience) {
        return playerRepository.findAllByExperienceBetween(minExperience, maxExperience);
    }

    @Override
    public List<Player> getAllPlayersByLevelBetween(Integer minLevel, Integer maxLevel) {
        return playerRepository.findAllByLevelBetween(minLevel, maxLevel);
    }

    @Override
    public List<Player> getAllPlayersByBirthdayBetween(Date after, Date before) {
        return playerRepository.findAllByBirthdayBetween(after,before);
    }

    //POST/UPDATE

    @Override
    public Player createOrUpdatePlayer(Player player) {
        return playerRepository.save(player);
    }


    //DELETE

    @Override
    public void deletePlayer(long id) {
        playerRepository.deleteById(id);
    }
}

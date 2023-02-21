package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, Long> {

   //GET
   List<Player> findAllByOrderByName();
   List<Player> findAllByOrderById();
   List<Player> findAllByOrderByExperience();
   List<Player> findAllByOrderByBirthday();
   List<Player> findAllByBannedIsTrue();
   List<Player> findAllByBannedIsFalse();
   List<Player> findAllByNameContains(String name);
   List<Player> findAllByTitleContains(String title);
   List<Player> findAllByRace(Race race);
   List<Player> findAllByProfession(Profession profession);
   List<Player> findAllByExperienceBetween(Integer minExperience, Integer maxExperience);
   List<Player> findAllByLevelBetween(Integer minLevel, Integer maxLevel);
   List<Player> findAllByBirthdayBetween(Date after, Date before);

   //POST

}

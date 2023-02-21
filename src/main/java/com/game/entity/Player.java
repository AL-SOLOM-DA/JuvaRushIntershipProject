package com.game.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;

@Validated
@Entity
@Table(name = "player")
@JsonIgnoreProperties
public class Player {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column (name = "title")
    private String title;

    @NonNull
    @Column (name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;

    @NonNull
    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @NonNull
    @Column(name = "experience")
    private Integer experience;

    @Column(name = "level")
    private Integer level;

    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;

    @NonNull
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "banned", columnDefinition = "BIT")
    private Boolean banned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (getId() != null ? !getId().equals(player.getId()) : player.getId() != null) return false;
        if (getName() != null ? !getName().equals(player.getName()) : player.getName() != null) return false;
        if (getTitle() != null ? !getTitle().equals(player.getTitle()) : player.getTitle() != null) return false;
        if (getRace() != player.getRace()) return false;
        if (getProfession() != player.getProfession()) return false;
        if (getExperience() != null ? !getExperience().equals(player.getExperience()) : player.getExperience() != null)
            return false;
        if (getLevel() != null ? !getLevel().equals(player.getLevel()) : player.getLevel() != null) return false;
        if (getUntilNextLevel() != null ? !getUntilNextLevel().equals(player.getUntilNextLevel()) : player.getUntilNextLevel() != null)
            return false;
        if (getBirthday() != null ? !getBirthday().equals(player.getBirthday()) : player.getBirthday() != null)
            return false;
        return getBanned() != null ? getBanned().equals(player.getBanned()) : player.getBanned() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getRace() != null ? getRace().hashCode() : 0);
        result = 31 * result + (getProfession() != null ? getProfession().hashCode() : 0);
        result = 31 * result + (getExperience() != null ? getExperience().hashCode() : 0);
        result = 31 * result + (getLevel() != null ? getLevel().hashCode() : 0);
        result = 31 * result + (getUntilNextLevel() != null ? getUntilNextLevel().hashCode() : 0);
        result = 31 * result + (getBirthday() != null ? getBirthday().hashCode() : 0);
        result = 31 * result + (getBanned() != null ? getBanned().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }
}

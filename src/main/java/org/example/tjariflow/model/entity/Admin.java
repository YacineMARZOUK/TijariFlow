package org.example.tjariflow.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
@Entity
@SuperBuilder
@AllArgsConstructor
@Data
public class Admin extends User{

}

package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Movements
import com.tcc.money.database.entities.MovementsEntity
import org.mapstruct.Mapper

@Mapper
interface MovementsMapper {
    fun toMovements(movementsEntity: MovementsEntity): Movements
    fun toMovementsEntity(movements: Movements): MovementsEntity
    fun toMovementsEntityList(movements: List<Movements>): List<MovementsEntity>
    fun toMovementsList(movementsEntity: List<MovementsEntity>): List<Movements>

}
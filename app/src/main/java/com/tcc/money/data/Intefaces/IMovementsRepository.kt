package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Movements
import java.util.UUID

interface IMovementsRepository {
     fun save(movements: Movements): Movements
     fun findByUUID(uuid: UUID): Movements
     fun findAll(): List<Movements>
}
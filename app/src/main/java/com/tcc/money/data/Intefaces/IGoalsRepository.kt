package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Goals
import com.tcc.money.data.models.Movements
import java.util.UUID

interface IGoalsRepository {
     fun save(goals: Goals): Goals
     fun findByUUID(uuid: UUID): Goals
     fun findAll(): List<Goals>
}

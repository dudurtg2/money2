package com.tcc.money.data.applications.save

import android.util.Log
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class SaveMovementsUseCase @Inject constructor(
    private val repository: MovementsRepository,
    private val dao: MovementsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: MovementsMapper
) {
    companion object {
        private const val TAG = "SaveMovementsUseCase"
    }

    suspend fun execute(input: Movements): Movements = withContext(Dispatchers.IO) {
        Log.d(TAG, "Execute iniciado com input: $input")

        // Gera um novo UUID para o movimento
        val newUuid = UUID.randomUUID()
        val movWithId = input.copy(uuid = newUuid)
        Log.d(TAG, "UUID gerado: $newUuid")

        // Verifica se a conta é premium
        val isPremium = try {
            checkPremium.execute().also {
                Log.d(TAG, "Resultado do checkPremium: $it")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao verificar conta premium", e)
            false
        }

        if (isPremium) {
            // Se for premium, salva primeiro no repositório remoto
            Log.d(TAG, "Usuário premium: salvando remotamente...")
            val savedRemote = try {
                repository.save(movWithId).also {
                    Log.d(TAG, "Salvo no remoto: $it")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Falha ao salvar remotamente. Lançando exceção.", e)
                throw e
            }

            // Mapeia para entidade local e marca sync = true
            val entity = mapper.toMovementsEntity(savedRemote).apply {
                sync = true
            }
            try {
                dao.save(entity)
                Log.d(TAG, "Salvo localmente (sync=true): $entity")
            } catch (e: Exception) {
                Log.e(TAG, "Falha ao salvar localmente, mesmo após salvar no remoto", e)
            }

            Log.d(TAG, "Retornando objeto remoto salvo: $savedRemote")
            return@withContext savedRemote
        } else {
            // Se não for premium, salva apenas localmente (sync = false)
            Log.d(TAG, "Usuário não premium: salvando localmente somente (sync=false)...")
            val entity = mapper.toMovementsEntity(movWithId).apply {
                sync = false
            }

            try {
                dao.save(entity)
                Log.d(TAG, "Salvo localmente (sync=false): $entity")
            } catch (e: Exception) {
                Log.e(TAG, "Falha ao salvar localmente (usuário não premium)", e)
            }

            val fetched = try {
                dao.findByUUID(entity.uuid).also {
                    Log.d(TAG, "Recuperado após salvar local: $it")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Falha ao buscar localmente após salvar", e)
                null
            }

            val result = if (fetched != null) mapper.toMovements(fetched) else {
                Log.w(TAG, "findByUUID retornou null, retornando input original")
                input
            }

            Log.d(TAG, "Retornando objeto local convertido: $result")
            return@withContext result
        }
    }
}

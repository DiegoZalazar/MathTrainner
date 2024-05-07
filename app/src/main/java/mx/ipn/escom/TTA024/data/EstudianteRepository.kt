package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.EstudianteModel
import mx.ipn.escom.TTA024.data.network.EstudianteService
import mx.ipn.escom.TTA024.domain.model.Estudiante
import mx.ipn.escom.TTA024.domain.model.toDomain

class EstudianteRepository  {
    private val api: EstudianteService = EstudianteService()
    suspend fun getAllEstudiantesFromAPI(): List<Estudiante> {
        val response: List<EstudianteModel> = api.getEstudiantes()
        return response.map { it.toDomain() }
    }

    suspend fun insertModuloFromAPI(estudianteModel: EstudianteModel): String {
        val response: String = api.insertEstudiante(estudianteModel)
        return response
    }

    suspend fun deleteModuloFromApi(id_estudiante: Int): String {
        val response: String = api.deleteEstudiante(id_estudiante)
        return response
    }
}
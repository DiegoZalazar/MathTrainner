package mx.ipn.escom.TTA024.domain.model

import mx.ipn.escom.TTA024.data.models.EstudianteModel
import mx.ipn.escom.TTA024.data.models.ModuloModel

data class Estudiante(val idEstudiante: Int,
                           var nombreEstudiante: String,
                           var nombreUsuario: String,
                           var correoEstudiante: String,
                           var estatusEstudiante: String,
                           var contrasenaEstudiante: String
)
fun EstudianteModel.toDomain() = Estudiante(idEstudiante, nombreEstudiante,nombreUsuario,correoEstudiante,estatusEstudiante,contrasenaEstudiante)


import os
import json
from examenDAO import ExamenDAO
import ArbolEvaluacion


def Get(event):
    global status_code, response_body, examen_dao
    try:

        # Create new instance DAO
        examen_dao = ExamenDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = examen_dao.CreateExam()
        status_code = 200
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        examen_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Post(event):
    # todo agregar el avnce al la bd del estudiante y poner logica para que no pase mas de un nivel
    global status_code, response_body
    data = json.loads(event['body'])
    avance = 0
    nivel = "basico"
    resultados = []
    estudianteAvance={}
    try:
        examen_dao = ExamenDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])

        estudianteAvance["idEstudiante"] = event['requestContext']['authorizer']['claims']['sub']

        for respuesta in data["respuestas"]:
            variables = [int(respuesta["correcta"]), int(respuesta["tipo"]), int(respuesta["tiempo"]),
                         int(respuesta["intentos"])]

            estudianteAvance["avance"] = ArbolEvaluacion.calcular_avanceModulo(variables)
            estudianteAvance["idModulo"] = respuesta["modulo"]
            rows = examen_dao.SetNivel(estudianteAvance)

            nivel = ArbolEvaluacion.calcular_nivel(variables)
            resultado = f'Modulo {respuesta["modulo"]} nivel asignado {nivel} '
            resultados.append(resultado)

        status_code = 200
        response_body = {'resultados': resultados}

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    finally:

        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


import os
import json
from sesionDAO import sesionDAO
import ArbolEvaluacion


def GetSesionEjercicio(event):
    global status_code, response_body, session_dao
    try:
        idModulo = event['pathParameters']['idModulo']

        # Create new instance DAO
        session_dao = sesionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])

        idEstudiante = event['requestContext']['authorizer']['claims']['sub']
        avanceModulo = session_dao.FindAvance(idEstudiante, idModulo)

        # avanceModulo = .40
        # todo agregar funcio completado
        nivelEjercicio = 1
        avanceModulo = float(avanceModulo)
        if 0.0 <= avanceModulo <= .33:
            nivelEjercicio = 1
        elif .34 <= avanceModulo <= .66:
            nivelEjercicio = 2
        elif .67 >= avanceModulo:
            nivelEjercicio = 3

        response_body = session_dao.CreateSesionEjercicio(idModulo, nivelEjercicio)
        status_code = 200
    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        session_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def PostSesionEjercicio(event):
    # todo agregar el avnce al la bd del estudiante y poner logica para que no pase mas de un nivel
    global status_code, response_body, avanceTotal
    data = json.loads(event['body'])
    avance = 0
    try:
        session_dao = sesionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        idEstudiante = event['requestContext']['authorizer']['claims']['sub']
        idModulo = event['pathParameters']['idModulo']
        for respuesta in data["respuestas"]:
            variables = [int(respuesta["correcta"]), int(respuesta["tipo"]), int(respuesta["tiempo"]),
                         int(respuesta["intentos"])]
            avance += ArbolEvaluacion.calcular_valor(variables)

        avanceActual = float(session_dao.FindAvance(idEstudiante, idModulo))
        avanceTotal = 0.0
        if avanceActual < .34:
            avanceTotal = avanceActual + avance
            if avanceTotal > .34:
                avanceTotal = .34

        elif avanceActual >= .34 and avanceActual < .67:
            avanceTotal = avanceActual + avance
            if avanceTotal > .67:
                avanceTotal = .67
        elif avanceActual >= .67:
            avanceTotal = avanceActual + avance
            if avanceTotal > 1.0:
                avanceTotal = 1.0

        estudianteModulo = {
            "avance": avanceTotal,
            "idModulo": idModulo,
            "idEstudiante": idEstudiante

        }

        rows = session_dao.updateAvance(estudianteModulo)

        status_code = 200
        response_body = {'Avance del modulo': avanceTotal}

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:

        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def GetSesionLeccion(event):
    global status_code, response_body, session_dao
    try:
        idModulo = event['pathParameters']['idModulo']
        # todo cambiar cuando este el id del estudiante en bd

        # Create new instance DAO
        session_dao = sesionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        # todo acabar el avance de modulo
        # avanceModulo = session_dao.FindAvance(idEstudiante, idModulo)
        # avanceModulo = .40
        # todo ver que regresa y hacer cast

        nivelLeccion = 0

        # if 0 <= avanceModulo <= .33:
        #    nivelLeccion = 0
        # elif .34 <= avanceModulo <= .66:
        #    nivelLeccion = 1
        # elif .67 >= avanceModulo:
        #    nivelLeccion = 2

        response_body = session_dao.CreateSesionLeccion(idModulo, nivelLeccion)
        status_code = 200
    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        session_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

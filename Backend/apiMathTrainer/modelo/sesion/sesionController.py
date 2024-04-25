import os
import json
from sesionDAO import sesionDAO


def GetSesionEjercicio(event):
    global status_code, response_body, session_dao
    try:
        idModulo = event['pathParameters']['idModulo']
        # todo cambiar cuando este el id del estudiante en bd
        idEstudiante = 1

        # Create new instance DAO
        session_dao = sesionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])

        avanceModulo = session_dao.FindAvance(idEstudiante, idModulo)
        # todo ver que regresa y hacer cast

        nivelEjercicio = 0

        if 0 <= avanceModulo <= .33:
            nivelEjercicio = 0
        elif .34 <= avanceModulo <= .66:
            nivelEjercicio = 1
        elif .67 >= avanceModulo:
            nivelEjercicio = 2

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
    response = {
        'statusCode': status_code,
        'body': 'Funcionalidad arbol de decicion para calificar (arbol de decicion)',
    }
    return response

def GetSesionLeccion(event):
    global status_code, response_body, session_dao
    try:
        idModulo = event['pathParameters']['idModulo']
        # todo cambiar cuando este el id del estudiante en bd
        idEstudiante = 1

        # Create new instance DAO
        session_dao = sesionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])

        avanceModulo = session_dao.FindAvance(idEstudiante, idModulo)
        # todo ver que regresa y hacer cast

        nivelLeccion = 0

        if 0 <= avanceModulo <= .33:
            nivelLeccion = 0
        elif .34 <= avanceModulo <= .66:
            nivelLeccion = 1
        elif .67 >= avanceModulo:
            nivelLeccion = 2

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




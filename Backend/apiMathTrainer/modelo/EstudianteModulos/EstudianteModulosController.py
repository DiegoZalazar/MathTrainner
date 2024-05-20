import os
import json
from EstudianteModulosDAO import EstudianteMdoulosDAO


def GetModulosAvance(event):
    global status_code, response_body, estudianteModuloDAO
    try:

        estudianteModuloDAO = EstudianteMdoulosDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'],
                                                   os.environ['DB'])
        idEstudiante = event['requestContext']['authorizer']['claims']['sub']
        response_body = estudianteModuloDAO.GetModulosEstudiante(idEstudiante)
        status_code = 200
    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        estudianteModuloDAO.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def ResetModulosAvance(event):
    global status_code, response_body, estudianteModuloDAO
    try:

        estudianteModuloDAO = EstudianteMdoulosDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'],
                                                   os.environ['DB'])
        idEstudiante = event['requestContext']['authorizer']['claims']['sub']
        response_body = estudianteModuloDAO.ResetModulosAvance(idEstudiante)
        status_code = 200
    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        estudianteModuloDAO.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response
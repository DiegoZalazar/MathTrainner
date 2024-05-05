import os
import json
from examenDAO import ExamenDAO


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
    response = {
        'statusCode': status_code,
        'body': 'Funcionalidad arbol de decicion para calificar examen',
    }
    return response

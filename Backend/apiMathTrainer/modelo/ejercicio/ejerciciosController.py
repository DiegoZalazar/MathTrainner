import os
import json
from ejercicioDAO import EjercicioDAO


def GetAll(event):
    global status_code, response_body, ejercicio_dao
    try:
        idModulo = event['pathParameters']['idModulo']
        # Create new instance DAO
        ejercicio_dao = EjercicioDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = ejercicio_dao.FindAll(idModulo)
        status_code = 200
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        ejercicio_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Post(event):
    global status_code, response_body, ejercicio_dao
    try:
        # Extract Data from event
        ejercicio = json.loads(event['body'])
        ejercicio["idModulo"] = event['pathParameters']['idModulo']


        # Create new instance DAO
        ejercicio_dao = EjercicioDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        ejercicioId = ejercicio_dao.Save(ejercicio)

        response_body = f"Data insert success with: ID {ejercicioId}"
        status_code = 200
    except KeyError as e:
        response_body = {'message': f'Body mising arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        ejercicio_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def GetByID(event):
    idEjercicio = event['pathParameters']['idEjercicio']
    global status_code, response_body, ejercicio_dao
    try:

        # Create new instance DAO
        ejercicio_dao = EjercicioDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = ejercicio_dao.FindById(idEjercicio)
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        ejercicio_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def Put(event):
    global status_code, response_body, ejercicio_dao
    try:
        # Extract Data from event
        ejercicio = json.loads(event['body'])
        ejercicio['idModulo'] = event['pathParameters']['idModulo']

        # Create new instance DAO
        ejercicio_dao = EjercicioDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = ejercicio_dao.Update(ejercicio)
        response_body = f'Rows updated: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        ejercicio_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Delete(event):
    idEjercicio = event['pathParameters']['idEjercicio']
    global status_code, response_body, ejercicio_dao
    try:
        # Create new instance DAO
        ejercicio_dao = EjercicioDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = ejercicio_dao.Delete(idEjercicio)
        response_body = f'Rows deleted: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        ejercicio_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response
